package com.sahti.backend.repository;

import com.sahti.backend.entity.FamilyMember;
import com.sahti.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FamilyMemberRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Test
    void savesAndFindsFamilyMembersByUserId() {
        User user = userRepository.save(new User("family-owner@example.com", "hashed-password"));

        familyMemberRepository.save(new FamilyMember(user.getId(), "Ahmed", LocalDate.of(1990, 1, 1),
                "O+", "Pollen", "Aucun"));
        familyMemberRepository.save(new FamilyMember(user.getId(), "Sara", LocalDate.of(2015, 5, 20),
                "A-", null, null));

        assertThat(familyMemberRepository.findByUserId(user.getId())).hasSize(2);
        assertThat(familyMemberRepository.countByUserId(user.getId())).isEqualTo(2);
    }

    @Test
    void countIsZeroWhenUserHasNoFamilyMembers() {
        User user = userRepository.save(new User("no-family@example.com", "hashed-password"));

        assertThat(familyMemberRepository.countByUserId(user.getId())).isZero();
        assertThat(familyMemberRepository.findByUserId(user.getId())).isEmpty();
    }
}
