package com.sahti.backend.controller;

import com.sahti.backend.dto.MedicationCatalogResponse;
import com.sahti.backend.service.MedicationCatalogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    private final MedicationCatalogService medicationCatalogService;

    public MedicationController(MedicationCatalogService medicationCatalogService) {
        this.medicationCatalogService = medicationCatalogService;
    }

    @GetMapping("/search")
    public List<MedicationCatalogResponse> search(@RequestParam("q") String query) {
        return medicationCatalogService.search(query).stream()
                .map(MedicationCatalogResponse::from)
                .toList();
    }
}
