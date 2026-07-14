package com.sahti.backend.dto;

import com.sahti.backend.entity.Caregiver;

public class CaregiverResponse {

    private final Long id;
    private final String nom;
    private final String email;

    public CaregiverResponse(Long id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    public static CaregiverResponse from(Caregiver caregiver) {
        return new CaregiverResponse(caregiver.getId(), caregiver.getNom(), caregiver.getEmail());
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }
}
