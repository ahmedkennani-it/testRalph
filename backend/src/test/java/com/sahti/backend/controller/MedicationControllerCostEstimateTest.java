package com.sahti.backend.controller;

import com.sahti.backend.entity.User;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class MedicationControllerCostEstimateTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private String authToken() {
        User user = userRepository.save(new User("cost-estimate-user@example.com", "hashed-password"));
        return jwtService.generateToken(user.getId(), user.getEmail());
    }

    @Test
    void computesTotalReimbursementAndRemainderForAmo() throws Exception {
        // id 1 = Doliprane Fictif (12.50), id 2 = Advilex Fictif (18.00) -> total 30.50, AMO taux 0.70
        mockMvc.perform(post("/api/medications/cost-estimate")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"medicationIds\":[1,2],\"regime\":\"AMO\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPpv").value(30.50))
                .andExpect(jsonPath("$.montantRembourse").value(21.35))
                .andExpect(jsonPath("$.resteACharge").value(9.15));
    }

    @Test
    void computesDifferentReimbursementForCnops() throws Exception {
        mockMvc.perform(post("/api/medications/cost-estimate")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"medicationIds\":[1,2],\"regime\":\"CNOPS\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPpv").value(30.50))
                .andExpect(jsonPath("$.montantRembourse").value(24.40))
                .andExpect(jsonPath("$.resteACharge").value(6.10));
    }

    @Test
    void rejectsUnknownMedicationId() throws Exception {
        mockMvc.perform(post("/api/medications/cost-estimate")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"medicationIds\":[999999],\"regime\":\"AMO\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsEmptyMedicationIdsList() throws Exception {
        mockMvc.perform(post("/api/medications/cost-estimate")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"medicationIds\":[],\"regime\":\"AMO\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsRequestWithoutToken() throws Exception {
        mockMvc.perform(post("/api/medications/cost-estimate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"medicationIds\":[1],\"regime\":\"AMO\"}"))
                .andExpect(status().isUnauthorized());
    }
}
