package com.sahti.backend.repository;

import com.sahti.backend.entity.FamilyMember;
import com.sahti.backend.entity.Prescription;
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
class PrescriptionRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Test
    void savesAndFindsPrescriptionsByFamilyMemberId() {
        User user = userRepository.save(new User("prescription-owner@example.com", "hashed-password"));
        FamilyMember member = familyMemberRepository.save(
                new FamilyMember(user.getId(), "Ahmed", LocalDate.of(1990, 1, 1), "O+", null, null));

        prescriptionRepository.save(new Prescription(member.getId(), 1L, null, "500mg", "3 fois par jour",
                "08:00,14:00,20:00", 30, LocalDate.now().plusDays(10)));
        prescriptionRepository.save(new Prescription(member.getId(), null, "Sirop maison", "5ml", "2 fois par jour",
                "09:00,21:00", 10, null));

        assertThat(prescriptionRepository.findByFamilyMemberId(member.getId())).hasSize(2);
    }
}
