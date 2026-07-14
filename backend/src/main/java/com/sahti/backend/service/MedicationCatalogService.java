package com.sahti.backend.service;

import com.sahti.backend.entity.MedicationCatalog;
import com.sahti.backend.repository.MedicationCatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationCatalogService {

    private final MedicationCatalogRepository medicationCatalogRepository;

    public MedicationCatalogService(MedicationCatalogRepository medicationCatalogRepository) {
        this.medicationCatalogRepository = medicationCatalogRepository;
    }

    public List<MedicationCatalog> search(String query) {
        return medicationCatalogRepository.findByNomContainingIgnoreCase(query);
    }
}
