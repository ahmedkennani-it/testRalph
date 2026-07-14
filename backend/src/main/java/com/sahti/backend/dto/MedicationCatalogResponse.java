package com.sahti.backend.dto;

import com.sahti.backend.entity.MedicationCatalog;

import java.math.BigDecimal;

public class MedicationCatalogResponse {

    private final Long id;
    private final String nom;
    private final String dosage;
    private final BigDecimal prixPpv;
    private final String noticeSimplifiee;
    private final boolean estGenerique;
    private final Long medicamentRefId;

    public MedicationCatalogResponse(Long id, String nom, String dosage, BigDecimal prixPpv,
                                      String noticeSimplifiee, boolean estGenerique, Long medicamentRefId) {
        this.id = id;
        this.nom = nom;
        this.dosage = dosage;
        this.prixPpv = prixPpv;
        this.noticeSimplifiee = noticeSimplifiee;
        this.estGenerique = estGenerique;
        this.medicamentRefId = medicamentRefId;
    }

    public static MedicationCatalogResponse from(MedicationCatalog medication) {
        return new MedicationCatalogResponse(medication.getId(), medication.getNom(), medication.getDosage(),
                medication.getPrixPpv(), medication.getNoticeSimplifiee(), medication.isEstGenerique(),
                medication.getMedicamentRefId());
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
