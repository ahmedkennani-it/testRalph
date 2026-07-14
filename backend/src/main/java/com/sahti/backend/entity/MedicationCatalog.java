package com.sahti.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "medication_catalog")
public class MedicationCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String dosage;

    @Column(name = "prix_ppv", nullable = false)
    private BigDecimal prixPpv;

    @Column(name = "notice_simplifiee")
    private String noticeSimplifiee;

    @Column(name = "est_generique", nullable = false)
    private boolean estGenerique;

    @Column(name = "medicament_ref_id")
    private Long medicamentRefId;

    protected MedicationCatalog() {
    }

    public MedicationCatalog(String nom, String dosage, BigDecimal prixPpv, String noticeSimplifiee,
                              boolean estGenerique, Long medicamentRefId) {
        this.nom = nom;
        this.dosage = dosage;
        this.prixPpv = prixPpv;
        this.noticeSimplifiee = noticeSimplifiee;
        this.estGenerique = estGenerique;
        this.medicamentRefId = medicamentRefId;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDosage() {
        return dosage;
    }

    public BigDecimal getPrixPpv() {
        return prixPpv;
    }

    public String getNoticeSimplifiee() {
        return noticeSimplifiee;
    }

    public boolean isEstGenerique() {
        return estGenerique;
    }

    public Long getMedicamentRefId() {
        return medicamentRefId;
    }
}
