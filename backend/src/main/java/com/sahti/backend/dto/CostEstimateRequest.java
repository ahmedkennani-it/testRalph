package com.sahti.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CostEstimateRequest {

    @NotEmpty
    private List<Long> medicationIds;

    @NotNull
    private RegimeRemboursement regime;

    public List<Long> getMedicationIds() {
        return medicationIds;
    }

    public void setMedicationIds(List<Long> medicationIds) {
        this.medicationIds = medicationIds;
    }

    public RegimeRemboursement getRegime() {
        return regime;
    }

    public void setRegime(RegimeRemboursement regime) {
        this.regime = regime;
    }
}
