-- Traitements (prescriptions) rattachés à un profil famille, soit liés au catalogue de
-- médicaments, soit saisis en nom libre (médicament hors catalogue).
CREATE TABLE prescriptions (
    id                      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    family_member_id        BIGINT NOT NULL REFERENCES family_members(id),
    medication_catalog_id   BIGINT REFERENCES medication_catalog(id),
    nom_libre               VARCHAR(255),
    dose                    VARCHAR(100) NOT NULL,
    frequence               VARCHAR(255) NOT NULL,
    horaires                VARCHAR(255),
    quantite_restante       INTEGER NOT NULL DEFAULT 0,
    date_fin_estimee        DATE,
    CONSTRAINT chk_prescription_medicament CHECK (medication_catalog_id IS NOT NULL OR nom_libre IS NOT NULL)
);

CREATE INDEX idx_prescriptions_family_member_id ON prescriptions(family_member_id);
