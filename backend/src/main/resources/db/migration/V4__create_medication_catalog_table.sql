-- Catalogue de médicaments : DONNÉES FICTIVES DE DÉMONSTRATION UNIQUEMENT.
-- Aucune donnée officielle marocaine (base médicaments réelle, prix AMO réels) n'est utilisée ici ;
-- les noms, dosages et prix ci-dessous sont inventés à des fins de démonstration/test.
CREATE TABLE medication_catalog (
    id                    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nom                   VARCHAR(255) NOT NULL,
    dosage                VARCHAR(100),
    prix_ppv              NUMERIC(10,2) NOT NULL,
    notice_simplifiee     VARCHAR(2000),
    est_generique         BOOLEAN NOT NULL DEFAULT FALSE,
    medicament_ref_id     BIGINT REFERENCES medication_catalog(id)
);

CREATE INDEX idx_medication_catalog_nom ON medication_catalog(nom);
CREATE INDEX idx_medication_catalog_ref_id ON medication_catalog(medicament_ref_id);

-- 10 médicaments de référence fictifs (ids 1 à 10, marques inventées).
INSERT INTO medication_catalog (nom, dosage, prix_ppv, notice_simplifiee, est_generique, medicament_ref_id) VALUES
('Doliprane Fictif', '500mg', 12.50, 'Antidouleur et antipyrétique (donnée fictive de démonstration).', FALSE, NULL),
('Advilex Fictif', '400mg', 18.00, 'Anti-inflammatoire non stéroïdien (donnée fictive de démonstration).', FALSE, NULL),
('Amoxiline Fictif', '500mg', 25.00, 'Antibiotique de la famille des pénicillines (donnée fictive de démonstration).', FALSE, NULL),
('Ventolair Fictif', '100mcg', 35.00, 'Bronchodilatateur inhalé (donnée fictive de démonstration).', FALSE, NULL),
('Glucalex Fictif', '850mg', 22.00, 'Antidiabétique oral (donnée fictive de démonstration).', FALSE, NULL),
('Cardiolol Fictif', '50mg', 28.00, 'Bêta-bloquant (donnée fictive de démonstration).', FALSE, NULL),
('Gastrozol Fictif', '20mg', 30.00, 'Inhibiteur de la pompe à protons (donnée fictive de démonstration).', FALSE, NULL),
('Allergyl Fictif', '10mg', 15.00, 'Antihistaminique (donnée fictive de démonstration).', FALSE, NULL),
('Tensiofix Fictif', '5mg', 20.00, 'Antihypertenseur (donnée fictive de démonstration).', FALSE, NULL),
('Somniprax Fictif', '10mg', 17.00, 'Aide au sommeil (donnée fictive de démonstration).', FALSE, NULL);

-- 10 génériques fictifs (ids 11 à 20), chacun lié à son référent ci-dessus par ordre d'insertion.
INSERT INTO medication_catalog (nom, dosage, prix_ppv, notice_simplifiee, est_generique, medicament_ref_id) VALUES
('Paracétamol Génériq', '500mg', 6.00, 'Générique fictif du médicament de référence (donnée de démonstration).', TRUE, 1),
('Ibuprofène Génériq', '400mg', 9.00, 'Générique fictif du médicament de référence (donnée de démonstration).', TRUE, 2),
('Amoxicilline Génériq', '500mg', 12.00, 'Générique fictif du médicament de référence (donnée de démonstration).', TRUE, 3),
('Salbutamol Génériq', '100mcg', 18.00, 'Générique fictif du médicament de référence (donnée de démonstration).', TRUE, 4),
('Metformine Génériq', '850mg', 10.00, 'Générique fictif du médicament de référence (donnée de démonstration).', TRUE, 5),
('Atenolol Génériq', '50mg', 14.00, 'Générique fictif du médicament de référence (donnée de démonstration).', TRUE, 6),
('Oméprazole Génériq', '20mg', 15.00, 'Générique fictif du médicament de référence (donnée de démonstration).', TRUE, 7),
('Cétirizine Génériq', '10mg', 7.00, 'Générique fictif du médicament de référence (donnée de démonstration).', TRUE, 8),
('Amlodipine Génériq', '5mg', 9.00, 'Générique fictif du médicament de référence (donnée de démonstration).', TRUE, 9),
('Zolpidem Génériq', '10mg', 8.00, 'Générique fictif du médicament de référence (donnée de démonstration).', TRUE, 10);
