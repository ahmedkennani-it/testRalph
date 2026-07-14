package com.sahti.backend.dto;

import java.math.BigDecimal;

public class CostEstimateResponse {

    private final BigDecimal totalPpv;
    private final BigDecimal montantRembourse;
    private final BigDecimal resteACharge;
    private final RegimeRemboursement regime;

    public CostEstimateResponse(BigDecimal totalPpv, BigDecimal montantRembourse, BigDecimal resteACharge,
                                 RegimeRemboursement regime) {
        this.totalPpv = totalPpv;
        this.montantRembourse = montantRembourse;
        this.resteACharge = resteACharge;
        this.regime = regime;
    }

    public BigDecimal getTotalPpv() {
        return totalPpv;
    }

    public BigDecimal getMontantRembourse() {
        return montantRembourse;
    }

    public BigDecimal getResteACharge() {
        return resteACharge;
    }

    public RegimeRemboursement getRegime() {
        return regime;
    }
}
