package com.sahti.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "caregivers")
public class Caregiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "family_member_id", nullable = false)
    private Long familyMemberId;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String email;

    protected Caregiver() {
    }

    public Caregiver(Long familyMemberId, String nom, String email) {
        this.familyMemberId = familyMemberId;
        this.nom = nom;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public Long getFamilyMemberId() {
        return familyMemberId;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }
}
