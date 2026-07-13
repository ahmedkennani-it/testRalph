# Instructions du projet — Sahti Backend (Spring Boot)

## Stack
- Java 21, Spring Boot 3 (Web, Data JPA, Security, Validation)
- PostgreSQL en dev (via docker-compose), H2 en tests
- Flyway pour les migrations
- Maven
- Tests : JUnit 5 + Mockito + Spring Boot Test

## Garde-fous obligatoires
- **Aucune vraie clé de paiement, aucun vrai secret externe** ne doit être codé en dur ou halluciné. L'abonnement Pro est un modèle de données complet avec un endpoint webhook interne prêt à recevoir un vrai fournisseur plus tard — ne jamais essayer d'appeler une vraie API Stripe/CMI.
- Contenu médical mocké (interactions, contre-indications, dosage, chat, explications) : règles statiques uniquement, chaque endpoint concerné renvoie un champ `disclaimer` explicite dans sa réponse JSON.
- Aucune donnée réelle officielle marocaine (base médicaments, taux AMO réels) : `MedicationCatalog` est un jeu de données fictif, documenté comme tel dans le commentaire de la migration Flyway qui le seed.
- Mots de passe : toujours hashés (BCrypt), jamais stockés ou loggés en clair.
- Secrets (JWT_SECRET, DB_PASSWORD, clé du webhook interne) : uniquement via variables d'environnement.

## Conventions de code
- Architecture en couches : `controller` / `service` / `repository` / `entity` / `dto`
- Un test d'intégration (`@SpringBootTest` + `MockMvc` ou `WebTestClient`) par endpoint, un test unitaire par règle métier non triviale dans les services
- DTOs distincts des entités JPA pour toutes les requêtes/réponses HTTP
- Toute route sous `/api/**` (hors `/api/auth/register` et `/api/auth/login`) exige un JWT valide
- Toute route qui manipule des données liées à un `familyMemberId` doit vérifier que ce family member appartient bien à l'utilisateur authentifié (pas seulement que l'id existe)

## Règles de travail (boucle Ralph)
- Lire `sahti-backend-prd.json` au début de chaque itération
- Traiter les tâches dans l'ordre des phases, une seule tâche par itération
- Implémenter la tâche **complètement**, avec ses tests
- Après implémentation :
  1. `mvn test` — tous les tests doivent passer
  2. `mvn package` — pas d'erreur de compilation
  3. Si échec : corriger avant de continuer
- Marquer la tâche `"done": true` dans `sahti-backend-prd.json`
- `git commit -m "feat(X.Y): description courte"`
- **Pousser immédiatement après chaque commit** : `git push`
  - Si le push échoue (conflit/retard) : `git pull --rebase` puis réessayer
  - Si le push échoue pour une raison d'authentification : documenter dans `progress.md` et s'arrêter pour signaler le blocage
- Ne jamais casser une fonctionnalité déjà terminée ni une route déjà testée

## Critère de fin
Quand toutes les tâches de `sahti-backend-prd.json` sont `done: true`, que `mvn test` et `mvn package` passent, et que chaque route Pro vérifie bien `SubscriptionService.isProActive()`, écrire exactement :

<promise>SAHTI_BACKEND_COMPLETE</promise>
