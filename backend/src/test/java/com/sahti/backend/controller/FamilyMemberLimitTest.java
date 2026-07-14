package com.sahti.backend.controller;

import com.sahti.backend.entity.User;
import com.sahti.backend.repository.UserRepository;
import com.sahti.backend.service.JwtService;
import com.sahti.backend.service.SubscriptionStatusProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class FamilyMemberLimitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @MockitoBean
    private SubscriptionStatusProvider subscriptionStatusProvider;

    private String createBody(String nom) {
        return "{\"nom\":\"" + nom + "\"}";
    }

    @Test
    void rejectsSecondFamilyMemberOnFreeTier() throws Exception {
        when(subscriptionStatusProvider.isProActive(any())).thenReturn(false);
        User user = userRepository.save(new User("free-tier@example.com", "hashed-password"));
        String token = jwtService.generateToken(user.getId(), user.getEmail());

        mockMvc.perform(post("/api/family-members")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody("First")))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/family-members")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody("Second")))
                .andExpect(status().isForbidden());
    }

    @Test
    void allowsMultipleFamilyMembersForProUser() throws Exception {
        when(subscriptionStatusProvider.isProActive(any())).thenReturn(true);
        User user = userRepository.save(new User("pro-tier@example.com", "hashed-password"));
        String token = jwtService.generateToken(user.getId(), user.getEmail());

        mockMvc.perform(post("/api/family-members")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody("First")))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/family-members")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody("Second")))
                .andExpect(status().isCreated());
    }
}
