package com.sahti.backend.dto;

import java.math.BigDecimal;

/**
 * Taux de remboursement simplifiés par régime marocain (AMO/CNSS/CNOPS). Ce sont des valeurs
 * illustratives à des fins de démonstration, pas des taux officiels réels.
 */
public enum RegimeRemboursement {

    AMO(new BigDecimal("0.70")),
    CNSS(new BigDecimal("0.70")),
    CNOPS(new BigDecimal("0.80"));

    private final BigDecimal taux;

    RegimeRemboursement(BigDecimal taux) {
        this.taux = taux;
    }

    public BigDecimal getTaux() {
        return taux;
    }
}
