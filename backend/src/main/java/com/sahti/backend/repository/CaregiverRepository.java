package com.sahti.backend.repository;

import com.sahti.backend.entity.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {

    List<Caregiver> findByFamilyMemberId(Long familyMemberId);
}
