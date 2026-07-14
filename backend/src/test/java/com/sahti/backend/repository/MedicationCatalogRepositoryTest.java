package com.sahti.backend.repository;

import com.sahti.backend.entity.MedicationCatalog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class MedicationCatalogRepositoryTest {

    @Autowired
    private MedicationCatalogRepository medicationCatalogRepository;

    @Test
    void seedsAtLeastTwentyFictionalEntries() {
        assertThat(medicationCatalogRepository.findAll()).hasSizeGreaterThanOrEqualTo(20);
    }

    @Test
    void searchesByNameCaseInsensitively() {
        List<MedicationCatalog> results = medicationCatalogRepository.findByNomContainingIgnoreCase("doliprane");

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getNom()).containsIgnoringCase("Doliprane");
    }

    @Test
    void genericsReferenceAnExistingBrandMedication() {
        List<MedicationCatalog> generics = medicationCatalogRepository.findAll().stream()
                .filter(MedicationCatalog::isEstGenerique)
                .toList();

        assertThat(generics).isNotEmpty();
        for (MedicationCatalog generic : generics) {
            assertThat(generic.getMedicamentRefId()).isNotNull();
            Optional<MedicationCatalog> reference = medicationCatalogRepository.findById(generic.getMedicamentRefId());
            assertThat(reference).isPresent();
            assertThat(reference.get().isEstGenerique()).isFalse();
        }
    }
}
