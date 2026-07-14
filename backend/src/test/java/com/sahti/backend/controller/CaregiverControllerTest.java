package com.sahti.backend.controller;

import com.sahti.backend.entity.FamilyMember;
import com.sahti.backend.entity.User;
import com.sahti.backend.repository.FamilyMemberRepository;
import com.sahti.backend.repository.UserRepository;
import com.sahti.backend.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CaregiverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Autowired
    private JwtService jwtService;

    @Test
    void invitesAndListsCaregiversForOwnedFamilyMember() throws Exception {
        User user = userRepository.save(new User("caregiver-owner@example.com", "hashed-password"));
        String token = jwtService.generateToken(user.getId(), user.getEmail());
        FamilyMember member = familyMemberRepository.save(
                new FamilyMember(user.getId(), "Ahmed", LocalDate.of(1990, 1, 1), "O+", null, null));

        mockMvc.perform(post("/api/family-members/" + member.getId() + "/caregivers")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Fatima\",\"email\":\"fatima@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom").value("Fatima"));

        mockMvc.perform(get("/api/family-members/" + member.getId() + "/caregivers")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].email").value("fatima@example.com"));
    }

    @Test
    void cannotListOrInviteCaregiversForFamilyMemberOwnedByAnotherUser() throws Exception {
        User owner = userRepository.save(new User("real-owner-2@example.com", "hashed-password"));
        User attacker = userRepository.save(new User("attacker-2@example.com", "hashed-password"));
        FamilyMember member = familyMemberRepository.save(
                new FamilyMember(owner.getId(), "Protected", LocalDate.of(1990, 1, 1), "O+", null, null));
        String attackerToken = jwtService.generateToken(attacker.getId(), attacker.getEmail());

        mockMvc.perform(get("/api/family-members/" + member.getId() + "/caregivers")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + attackerToken))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/api/family-members/" + member.getId() + "/caregivers")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + attackerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Intrus\",\"email\":\"intrus@example.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void rejectsRequestsWithoutToken() throws Exception {
        mockMvc.perform(get("/api/family-members/1/caregivers"))
                .andExpect(status().isUnauthorized());
    }
}
