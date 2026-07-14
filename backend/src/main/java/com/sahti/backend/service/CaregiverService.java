package com.sahti.backend.service;

import com.sahti.backend.dto.CaregiverRequest;
import com.sahti.backend.entity.Caregiver;
import com.sahti.backend.repository.CaregiverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaregiverService {

    private final CaregiverRepository caregiverRepository;
    private final FamilyMemberService familyMemberService;

    public CaregiverService(CaregiverRepository caregiverRepository, FamilyMemberService familyMemberService) {
        this.caregiverRepository = caregiverRepository;
        this.familyMemberService = familyMemberService;
    }

    public List<Caregiver> list(Long userId, Long familyMemberId) {
        familyMemberService.getOwnedOrThrow(userId, familyMemberId);
        return caregiverRepository.findByFamilyMemberId(familyMemberId);
    }

    public Caregiver invite(Long userId, Long familyMemberId, CaregiverRequest request) {
        familyMemberService.getOwnedOrThrow(userId, familyMemberId);
        Caregiver caregiver = new Caregiver(familyMemberId, request.getNom(), request.getEmail());
        return caregiverRepository.save(caregiver);
    }
}
