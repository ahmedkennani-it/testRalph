-- Profils "famille" (le patient principal ou ses proches) rattachés à un utilisateur.
CREATE TABLE family_members (
    id                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id           BIGINT NOT NULL REFERENCES users(id),
    nom               VARCHAR(255) NOT NULL,
    date_naissance    DATE,
    groupe_sanguin    VARCHAR(10),
    allergies         VARCHAR(1000),
    antecedents       VARCHAR(1000)
);

CREATE INDEX idx_family_members_user_id ON family_members(user_id);
