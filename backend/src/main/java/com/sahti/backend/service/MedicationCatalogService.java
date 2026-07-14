package com.sahti.backend.service;

import com.sahti.backend.dto.CostEstimateRequest;
import com.sahti.backend.dto.CostEstimateResponse;
import com.sahti.backend.entity.MedicationCatalog;
import com.sahti.backend.repository.MedicationCatalogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MedicationCatalogService {

    private final MedicationCatalogRepository medicationCatalogRepository;

    public MedicationCatalogService(MedicationCatalogRepository medicationCatalogRepository) {
        this.medicationCatalogRepository = medicationCatalogRepository;
    }

    public List<MedicationCatalog> search(String query) {
        return medicationCatalogRepository.findByNomContainingIgnoreCase(query);
    }

    public List<MedicationCatalog> getGenerics(Long medicationId) {
        if (!medicationCatalogRepository.existsById(medicationId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Médicament introuvable");
        }
        return medicationCatalogRepository.findByMedicamentRefIdOrderByPrixPpvAsc(medicationId);
    }

    public CostEstimateResponse estimateCost(CostEstimateRequest request) {
        Map<Long, MedicationCatalog> byId = medicationCatalogRepository.findAllById(request.getMedicationIds()).stream()
                .collect(Collectors.toMap(MedicationCatalog::getId, medication -> medication));

        BigDecimal totalPpv = BigDecimal.ZERO;
        for (Long id : request.getMedicationIds()) {
            MedicationCatalog medication = byId.get(id);
            if (medication == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Médicament introuvable : id " + id);
            }
            totalPpv = totalPpv.add(medication.getPrixPpv());
        }

        BigDecimal montantRembourse = totalPpv.multiply(request.getRegime().getTaux()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal resteACharge = totalPpv.subtract(montantRembourse).setScale(2, RoundingMode.HALF_UP);

        return new CostEstimateResponse(totalPpv.setScale(2, RoundingMode.HALF_UP), montantRembourse, resteACharge,
                request.getRegime());
    }
}
