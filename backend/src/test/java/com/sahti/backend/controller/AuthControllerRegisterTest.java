package com.sahti.backend.controller;

import com.sahti.backend.entity.User;
import com.sahti.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AuthControllerRegisterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void registersNewUserSuccessfully() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"new-user@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("new-user@example.com"))
                .andExpect(jsonPath("$.id").exists());

        assertThat(userRepository.existsByEmail("new-user@example.com")).isTrue();
    }

    @Test
    void rejectsRegistrationWhenEmailAlreadyTaken() throws Exception {
        User existing = new User("taken@example.com", passwordEncoder.encode("password123"));
        userRepository.save(existing);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"taken@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    void rejectsRegistrationWithTooShortPassword() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"short-password@example.com\",\"password\":\"123\"}"))
                .andExpect(status().isBadRequest());

        assertThat(userRepository.existsByEmail("short-password@example.com")).isFalse();
    }

    @Test
    void rejectsRegistrationWithInvalidEmail() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"not-an-email\",\"password\":\"password123\"}"))
                .andExpect(status().isBadRequest());
    }
}
