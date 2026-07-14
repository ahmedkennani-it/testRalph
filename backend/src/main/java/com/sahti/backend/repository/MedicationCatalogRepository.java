package com.sahti.backend.repository;

import com.sahti.backend.entity.MedicationCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationCatalogRepository extends JpaRepository<MedicationCatalog, Long> {

    List<MedicationCatalog> findByNomContainingIgnoreCase(String nom);

    List<MedicationCatalog> findByMedicamentRefIdOrderByPrixPpvAsc(Long medicamentRefId);
}
