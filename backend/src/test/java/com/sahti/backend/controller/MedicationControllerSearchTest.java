package com.sahti.backend.controller;

import com.sahti.backend.entity.User;
import com.sahti.backend.repository.UserRepository;
import com.sahti.backend.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class MedicationControllerSearchTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private String authToken() {
        User user = userRepository.save(new User("medication-search-user@example.com", "hashed-password"));
        return jwtService.generateToken(user.getId(), user.getEmail());
    }

    @Test
    void findsMedicationByCaseInsensitivePartialName() throws Exception {
        mockMvc.perform(get("/api/medications/search")
                        .param("q", "doliprane")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nom").value("Doliprane Fictif"));
    }

    @Test
    void returnsEmptyListWhenNoMatch() throws Exception {
        mockMvc.perform(get("/api/medications/search")
                        .param("q", "totally-unknown-medication-xyz")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void rejectsRequestWithoutToken() throws Exception {
        mockMvc.perform(get("/api/medications/search").param("q", "doliprane"))
                .andExpect(status().isUnauthorized());
    }
}
