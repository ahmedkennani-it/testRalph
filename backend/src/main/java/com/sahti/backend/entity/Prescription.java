package com.sahti.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "family_member_id", nullable = false)
    private Long familyMemberId;

    @Column(name = "medication_catalog_id")
    private Long medicationCatalogId;

    @Column(name = "nom_libre")
    private String nomLibre;

    @Column(nullable = false)
    private String dose;

    @Column(nullable = false)
    private String frequence;

    /** Horaires de prise, stockés sous forme de liste "HH:mm" séparés par des virgules. */
    private String horaires;

    @Column(name = "quantite_restante", nullable = false)
    private Integer quantiteRestante;

    @Column(name = "date_fin_estimee")
    private LocalDate dateFinEstimee;

    protected Prescription() {
    }

    public Prescription(Long familyMemberId, Long medicationCatalogId, String nomLibre, String dose,
                         String frequence, String horaires, Integer quantiteRestante, LocalDate dateFinEstimee) {
        this.familyMemberId = familyMemberId;
        this.medicationCatalogId = medicationCatalogId;
        this.nomLibre = nomLibre;
        this.dose = dose;
        this.frequence = frequence;
        this.horaires = horaires;
        this.quantiteRestante = quantiteRestante;
        this.dateFinEstimee = dateFinEstimee;
    }

    public Long getId() {
        return id;
    }

    public Long getFamilyMemberId() {
        return familyMemberId;
    }

    public Long getMedicationCatalogId() {
        return medicationCatalogId;
    }

    public void setMedicationCatalogId(Long medicationCatalogId) {
        this.medicationCatalogId = medicationCatalogId;
    }

    public String getNomLibre() {
        return nomLibre;
    }

    public void setNomLibre(String nomLibre) {
        this.nomLibre = nomLibre;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getFrequence() {
        return frequence;
    }

    public void setFrequence(String frequence) {
        this.frequence = frequence;
    }

    public String getHoraires() {
        return horaires;
    }

    public void setHoraires(String horaires) {
        this.horaires = horaires;
    }

    public Integer getQuantiteRestante() {
        return quantiteRestante;
    }

    public void setQuantiteRestante(Integer quantiteRestante) {
        this.quantiteRestante = quantiteRestante;
    }

    public LocalDate getDateFinEstimee() {
        return dateFinEstimee;
    }

    public void setDateFinEstimee(LocalDate dateFinEstimee) {
        this.dateFinEstimee = dateFinEstimee;
    }
}
