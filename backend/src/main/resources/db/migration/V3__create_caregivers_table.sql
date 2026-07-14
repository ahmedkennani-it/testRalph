-- Aidants (proches invités) associés à un profil famille : simple liste de noms/emails,
-- sans gestion de comptes multi-utilisateurs pour cette version.
CREATE TABLE caregivers (
    id                 BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    family_member_id   BIGINT NOT NULL REFERENCES family_members(id),
    nom                VARCHAR(255) NOT NULL,
    email              VARCHAR(255) NOT NULL
);

CREATE INDEX idx_caregivers_family_member_id ON caregivers(family_member_id);
