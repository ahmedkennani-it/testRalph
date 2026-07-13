# Instructions du projet — React Todo App

## Stack
- React 18 + TypeScript + Vite
- Tests : Vitest + React Testing Library
- Pas de librairie de state management externe (useState/useReducer suffisent)
- Pas de framework CSS, CSS simple dans des fichiers `.css` co-localisés

## Conventions de code
- Composants fonctionnels uniquement, avec hooks
- Un composant par fichier, nommé `NomDuComposant.tsx`
- Chaque composant a son fichier de test `NomDuComposant.test.tsx` à côté
- Types explicites, pas de `any`
- Noms de variables et commentaires en français, noms de fonctions/composants en anglais (convention du code)

## Règles de travail (boucle Ralph)
- Lire `prd.json` au début de chaque itération
- Choisir la **première tâche non terminée** (`done: false`), dans l'ordre des phases
- Implémenter la tâche **complètement** : pas de TODO, pas de stub, pas de `// à faire plus tard`
- Après implémentation :
  1. Lancer `npm run test` — tous les tests doivent passer
  2. Lancer `npm run build` — pas d'erreur TypeScript
  3. Si échec : corriger avant de continuer, ne pas passer à la tâche suivante
- Marquer la tâche comme `"done": true` dans `prd.json`
- Committer avec un message clair : `git commit -m "feat(X.Y): description courte"`
- **Pousser immédiatement après chaque commit** : `git push`
  - Si le push échoue (conflit, branche en retard), faire `git pull --rebase` puis réessayer `git push` avant de continuer
  - Si le push échoue pour une raison d'authentification, ne pas insister en boucle : documenter l'erreur dans `progress.md` et s'arrêter pour signaler le blocage
- Ne jamais casser une fonctionnalité déjà terminée (vérifier avec les tests existants)

## Critère de fin
Quand **toutes** les tâches de `prd.json` sont à `done: true`, ET que `npm run test` et `npm run build` passent sans erreur, écrire exactement :

<promise>TODO_APP_COMPLETE</promise>
