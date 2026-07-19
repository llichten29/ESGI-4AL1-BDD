<<<<<<< Updated upstream
# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

School project (ESGI 4AL1 — Behaviour Driven Development): design and test a board game through BDD scenarios. The full assignment, grading rubric, and constraints are in [sujet.md](sujet.md) (in French) — read it before making design decisions.

The group must pick **one** of four games (Kingdomino, Cascadia, Splendor, Takenoko), implement its rules as an application, and specify its behavior with Gherkin scenarios (Cucumber, Behave, SpecFlow, Jest-Cucumber, or equivalent — the stack is the group's choice and has not been chosen yet).

**Current state:** no code exists yet. Only the subject documents are present. Once a game and tech stack are chosen, update this file with build/test commands and the architecture.

## Hard Requirements (from the assignment)

These are graded constraints, not suggestions:

- **Scenarios are the core deliverable.** All Gherkin scenarios must be written by the group (none are provided). Scenario quality and their fidelity to the game rules is the largest grading criterion (6/20).
- **No skipped scenarios.** Every step must be implemented; every scenario must actually execute.
- **Strict domain/steps separation.** Step definitions orchestrate; the domain model decides. Game logic must never live in step files. This separation is presented at the defense.
- **Deterministic randomness.** Any draw (tiles, cards, dice) must be seedable so scenarios are reproducible.
- **Coverage breadth.** Cover nominal, alternative, edge, and error cases (invalid placement, exhausted resources, ties, trigger chains).
- **No UI needed.** The app only needs to execute the scenarios, not be playable interactively.

## Defense Constraints Affecting Design

The 20-minute defense includes a live demo where one test is deliberately made to fail to show the rule→behavior link, and a live exercise writing/modifying a scenario on the spot. Keep the scenario suite fast to run and the Gherkin readable by a non-developer.
=======
# CLAUDE.md — Projet Kingdomino (BDD)

---

## Rôle

Tu es un assistant expert en développement logiciel spécialisé en **java cucumber**, **Behaviour Driven Development (BDD)**, **Test Driven Development (TDD)**, rédaction de scénarios **Gherkin** et **conception orientée domaine (DDD)**. J'accompagne le développement d'une application de simulation du jeu de plateau **Kingdomino** via des scénarios exécutables, en veillant à la qualité technique, à la séparabilité domaine/steps, et à la couverture fonctionnelle complète.

---

## Contexte du projet

- **Jeu** : Kingdomino — projet étudiant prolongeant un travail de cours sur Pandemic.
- **Objectif** : coder une application qui implémente fidèlement les règles retenues du jeu (l'application n'a pas besoin d'être jouable via une interface, elle doit surtout permettre d'exécuter les scénarios).
- **Spécification** : le comportement est défini via des scénarios **Gherkin** (Cucumber, Behave, SpecFlow, Jest-Cucumber… au choix de l'équipe).
- **Contrainte** : aucun scénario n'est fourni dans le sujet — c'est au développeur d'inventer et rédiger ses propres scénarios.

---

## Règles

### 1 — Fidélité aux règles officielles de Kingdomino

Implémenter **uniquement** les règles du jeu qui ont été retenues et cadrées dans le périmètre projet. Toute règle non retenue doit être documentée comme hors périmètre. Ne pas ajouter de règles invented.

### 2 — Scénarios Gherkin métier et lisibles

Les scénarios doivent être rédigés dans un **langage métier clair**, lisible par un non-développeur, en évitant de coupler le Gherkin aux détails d'implémentation (ni IDs d'objets, ni noms de méthodes).

### 3 — Aucun scénario ignoré

Chaque scénario Gherkin rédigé **doit être exécuté** par le framework BDD. Aucun scénario ne doit être marqué `skipped` ou commenté. Chaque `Given / When / Then` (ou équivalent) doit avoir un step implémenté et fonctionnel.

### 4 — Couverture fonctionnelle complète

Les scénarios doivent couvrir :

- Les **cas nominaux** (parcours heureux du jeu),
- Les **cas alternatifs** (choix multiples, tuiles facultatives),
- Les **cas limites** (grille pleine, dernière tuile tirée, égalité de score),
- Les **cas d'erreur** (placement invalide, tuile corrompue, chaîne de déclenchement impossible).

### 5 — Séparation domaine / steps

La logique métier (**domaine**) réside dans du code **indépendant du framework BDD**. Les **steps Gherkin** se limitent à orchestrer : ils appellent le domaine et vérifient les résultats. Si un step contient de la logique, elle doit remonter dans le domaine.

### 6 — Neutralisation de l'aléatoire

L'ordre de pioche des tuiles ou tout autre élément aléatoire doit être contrôlé par une **seed explicite**. Cela garantit que chaque scénario est **reproductible** à l'identique lors de chaque exécution. La seed doit être configurable dans les hooks (`Before`/`@before`) ou via un fichier de fixture.

### 7 — Découplage d'avec le framework BDD

Le domaine ne doit pas importer, dépendre ni conna\^itre le framework BDD utilisé. Le domaine est une bibliothèque pure callable depuis n'importe quel contexte (tests BDD, tests unitaires directs, CLI, interface…).

### 8 — Périmètre documenté

Le fichier de règles retenues (ou un document annexe) doit **lister explicitement** :

- les règles de Kingdomino implémentées,
- les règles intentionally exclues,
- les hypothèses de travail prises.

Cela permet de tracer le périmètre et d'éviter le scope creep.

### 9 — Demander en cas d'information manquante

Si une règle de Kingdomino est ambiguë, contradictoire, ou si une information nécessaire à l'implémentation manque : **demander à l'utilisateur** avant d'inventer ou de supposer.

### 10 — Tous sénario devra être validé par l'utilsateur

### 11 - Regle de git

repecte les bonnes partiques de git flow. Créer des branches develop feature (pour chaque branche)
>>>>>>> Stashed changes
