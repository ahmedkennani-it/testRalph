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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class FamilyMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Autowired
    private JwtService jwtService;

    private String tokenFor(User user) {
        return jwtService.generateToken(user.getId(), user.getEmail());
    }

    @Test
    void createsListsUpdatesAndDeletesFamilyMember() throws Exception {
        User user = userRepository.save(new User("owner@example.com", "hashed-password"));
        String token = tokenFor(user);

        String createBody = "{\"nom\":\"Ahmed\",\"dateNaissance\":\"1990-01-01\",\"groupeSanguin\":\"O+\","
                + "\"allergies\":\"Pollen\",\"antecedents\":\"Aucun\"}";

        mockMvc.perform(post("/api/family-members")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom").value("Ahmed"));

        Long createdId = familyMemberRepository.findByUserId(user.getId()).get(0).getId();

        mockMvc.perform(get("/api/family-members")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        String updateBody = "{\"nom\":\"Ahmed K.\",\"dateNaissance\":\"1990-01-01\",\"groupeSanguin\":\"O+\","
                + "\"allergies\":\"Pollen, Arachides\",\"antecedents\":\"Aucun\"}";

        mockMvc.perform(put("/api/family-members/" + createdId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Ahmed K."))
                .andExpect(jsonPath("$.allergies").value("Pollen, Arachides"));

        mockMvc.perform(delete("/api/family-members/" + createdId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent());

        assertThat(familyMemberRepository.findByUserId(user.getId())).isEmpty();
    }

    @Test
    void listOnlyReturnsFamilyMembersOfAuthenticatedUser() throws Exception {
        User owner = userRepository.save(new User("scoped-owner@example.com", "hashed-password"));
        User other = userRepository.save(new User("scoped-other@example.com", "hashed-password"));
        familyMemberRepository.save(new FamilyMember(owner.getId(), "Owned", LocalDate.of(2000, 1, 1), "A+", null, null));
        familyMemberRepository.save(new FamilyMember(other.getId(), "NotOwned", LocalDate.of(2000, 1, 1), "B+", null, null));

        mockMvc.perform(get("/api/family-members")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenFor(owner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nom").value("Owned"));
    }

    @Test
    void cannotUpdateOrDeleteFamilyMemberOwnedByAnotherUser() throws Exception {
        User owner = userRepository.save(new User("real-owner@example.com", "hashed-password"));
        User attacker = userRepository.save(new User("attacker@example.com", "hashed-password"));
        FamilyMember member = familyMemberRepository.save(
                new FamilyMember(owner.getId(), "Protected", LocalDate.of(2000, 1, 1), "A+", null, null));

        String updateBody = "{\"nom\":\"Hacked\"}";

        mockMvc.perform(put("/api/family-members/" + member.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenFor(attacker))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/api/family-members/" + member.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenFor(attacker)))
                .andExpect(status().isNotFound());
    }

    @Test
    void rejectsRequestsWithoutToken() throws Exception {
        mockMvc.perform(get("/api/family-members"))
                .andExpect(status().isUnauthorized());
    }
}
