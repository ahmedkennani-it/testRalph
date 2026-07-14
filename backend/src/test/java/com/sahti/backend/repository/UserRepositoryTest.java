package com.sahti.backend.repository;

import com.sahti.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void savesUserAndGeneratesIdAndDateCreation() {
        User saved = userRepository.save(new User("test@example.com", "hashed-password"));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getDateCreation()).isNotNull();
    }

    @Test
    void findsUserByEmail() {
        userRepository.save(new User("find-me@example.com", "hashed-password"));

        assertThat(userRepository.findByEmail("find-me@example.com")).isPresent();
        assertThat(userRepository.findByEmail("missing@example.com")).isEmpty();
    }

    @Test
    void existsByEmailReflectsPresence() {
        userRepository.save(new User("exists@example.com", "hashed-password"));

        assertThat(userRepository.existsByEmail("exists@example.com")).isTrue();
        assertThat(userRepository.existsByEmail("missing@example.com")).isFalse();
    }
}
