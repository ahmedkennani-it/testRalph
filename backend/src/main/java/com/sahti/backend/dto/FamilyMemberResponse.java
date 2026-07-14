package com.sahti.backend.dto;

import com.sahti.backend.entity.FamilyMember;

import java.time.LocalDate;

public class FamilyMemberResponse {

    private final Long id;
    private final String nom;
    private final LocalDate dateNaissance;
    private final String groupeSanguin;
    private final String allergies;
    private final String antecedents;

    public FamilyMemberResponse(Long id, String nom, LocalDate dateNaissance, String groupeSanguin,
                                 String allergies, String antecedents) {
        this.id = id;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.groupeSanguin = groupeSanguin;
        this.allergies = allergies;
        this.antecedents = antecedents;
    }

    public static FamilyMemberResponse from(FamilyMember member) {
        return new FamilyMemberResponse(member.getId(), member.getNom(), member.getDateNaissance(),
                member.getGroupeSanguin(), member.getAllergies(), member.getAntecedents());
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public String getGroupeSanguin() {
        return groupeSanguin;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getAntecedents() {
        return antecedents;
    }
}
