package com.sahti.backend.service;

import com.sahti.backend.dto.FamilyMemberRequest;
import com.sahti.backend.entity.FamilyMember;
import com.sahti.backend.repository.FamilyMemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FamilyMemberService {

    private static final int FREE_TIER_MAX_FAMILY_MEMBERS = 1;

    private final FamilyMemberRepository familyMemberRepository;
    private final SubscriptionStatusProvider subscriptionStatusProvider;

    public FamilyMemberService(FamilyMemberRepository familyMemberRepository,
                                SubscriptionStatusProvider subscriptionStatusProvider) {
        this.familyMemberRepository = familyMemberRepository;
        this.subscriptionStatusProvider = subscriptionStatusProvider;
    }

    public List<FamilyMember> list(Long userId) {
        return familyMemberRepository.findByUserId(userId);
    }

    public FamilyMember create(Long userId, FamilyMemberRequest request) {
        if (!subscriptionStatusProvider.isProActive(userId)
                && familyMemberRepository.countByUserId(userId) >= FREE_TIER_MAX_FAMILY_MEMBERS) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Limite atteinte : passez à l'abonnement Pro pour ajouter plus d'un profil famille");
        }
        FamilyMember member = new FamilyMember(userId, request.getNom(), request.getDateNaissance(),
                request.getGroupeSanguin(), request.getAllergies(), request.getAntecedents());
        return familyMemberRepository.save(member);
    }

    public FamilyMember update(Long userId, Long id, FamilyMemberRequest request) {
        FamilyMember member = getOwnedOrThrow(userId, id);
        member.setNom(request.getNom());
        member.setDateNaissance(request.getDateNaissance());
        member.setGroupeSanguin(request.getGroupeSanguin());
        member.setAllergies(request.getAllergies());
        member.setAntecedents(request.getAntecedents());
        return familyMemberRepository.save(member);
    }

    public void delete(Long userId, Long id) {
        FamilyMember member = getOwnedOrThrow(userId, id);
        familyMemberRepository.delete(member);
    }

    /**
     * Looks up a family member and verifies it belongs to the given user, for reuse by any
     * other service scoping data to a familyMemberId (prescriptions, health constants, etc.).
     */
    public FamilyMember getOwnedOrThrow(Long userId, Long id) {
        FamilyMember member = familyMemberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profil famille introuvable"));
        if (!member.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profil famille introuvable");
        }
        return member;
    }
}
