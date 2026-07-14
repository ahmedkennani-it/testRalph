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
class MedicationControllerGenericsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private String authToken() {
        User user = userRepository.save(new User("generics-user@example.com", "hashed-password"));
        return jwtService.generateToken(user.getId(), user.getEmail());
    }

    @Test
    void returnsGenericsSortedByAscendingPrice() throws Exception {
        // id 1 = "Doliprane Fictif" seeded in V4, with two generics seeded in V4/V5
        // priced 6.00 and 4.50 respectively.
        mockMvc.perform(get("/api/medications/1/generics")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nom").value("Paracétamol Génériq Économique"))
                .andExpect(jsonPath("$[0].prixPpv").value(4.50))
                .andExpect(jsonPath("$[1].prixPpv").value(6.00));
    }

    @Test
    void returnsNotFoundForUnknownMedicationId() throws Exception {
        mockMvc.perform(get("/api/medications/999999/generics")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    void rejectsRequestWithoutToken() throws Exception {
        mockMvc.perform(get("/api/medications/1/generics"))
                .andExpect(status().isUnauthorized());
    }
}
