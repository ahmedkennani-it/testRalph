# Instructions du projet — Sahti Frontend (branché sur backend Spring)

## Stack
- React 18 + TypeScript + Vite
- Routing : react-router-dom
- Appels API : axios (instance unique avec intercepteur JWT)
- Tests : Vitest + React Testing Library (mocker les appels API avec `vi.mock` ou `msw`)
- Backend : Spring Boot, dépôt séparé — voir `sahti-backend-prd.json` de ce dépôt-là pour la liste des endpoints disponibles

## Garde-fous obligatoires
- Ne jamais réintroduire de persistance locale (localStorage/reducer) pour des données métier qui existent maintenant côté backend (profils, prescriptions, constantes, etc.) — seul le JWT est stocké côté client.
- Le statut Pro vient uniquement de `GET /api/subscription/status`. Ne jamais recréer un toggle Pro local.
- Afficher tel quel le champ `disclaimer` renvoyé par les endpoints médicaux mockés (interactions, contre-indications, dosage, chat, explication) — ne pas l'omettre, ne pas le reformuler.
- Ne jamais coder en dur une URL de backend : toujours passer par `VITE_API_BASE_URL`.

## Conventions de code
- Composants fonctionnels + hooks uniquement
- Un composant par fichier `NomDuComposant.tsx`, test associé `NomDuComposant.test.tsx`
- Un dossier par module fonctionnel sous `src/features/`
- Un hook dédié par ressource API sous `src/api/` (ex: `usePrescriptions.ts`, `useFamilyMembers.ts`) plutôt que des appels axios éparpillés dans les composants
- Types explicites, pas de `any` — les types des DTOs vivent dans `src/types/`

## Règles de travail (boucle Ralph)
- Lire `sahti-frontend-prd-v2.json` au début de chaque itération (remplace l'ancien `sahti-prd.json`)
- Traiter les tâches dans l'ordre des phases, une seule tâche par itération
- Implémenter la tâche **complètement**, avec ses tests (mocker les appels réseau, ne pas dépendre d'un vrai backend démarré pour que les tests passent en CI)
- Après implémentation :
  1. `npm run test` — tous les tests doivent passer
  2. `npm run build` — pas d'erreur TypeScript
  3. Si échec : corriger avant de continuer
- Marquer la tâche `"done": true` dans `sahti-frontend-prd-v2.json`
- `git commit -m "feat(X.Y): description courte"`
- **Pousser immédiatement après chaque commit** : `git push`
  - Si le push échoue (conflit/retard) : `git pull --rebase` puis réessayer
  - Si le push échoue pour une raison d'authentification : documenter dans `progress.md` et s'arrêter pour signaler le blocage
- Ne jamais casser une fonctionnalité déjà terminée

## Critère de fin
Quand toutes les tâches de `sahti-frontend-prd-v2.json` sont `done: true`, que `npm run test` et `npm run build` passent, écrire exactement :

<promise>SAHTI_FRONTEND_COMPLETE</promise>
