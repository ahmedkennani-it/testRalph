package com.sahti.backend.repository;

import com.sahti.backend.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByFamilyMemberId(Long familyMemberId);
}
