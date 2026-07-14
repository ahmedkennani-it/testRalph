-- Ajout d'un second générique fictif pour "Doliprane Fictif" (id 1), moins cher que le premier,
-- afin de pouvoir tester le tri par prix croissant de /api/medications/{id}/generics.
-- DONNÉE FICTIVE DE DÉMONSTRATION.
INSERT INTO medication_catalog (nom, dosage, prix_ppv, notice_simplifiee, est_generique, medicament_ref_id) VALUES
('Paracétamol Génériq Économique', '500mg', 4.50, 'Générique fictif du médicament de référence (donnée de démonstration).', TRUE, 1);
