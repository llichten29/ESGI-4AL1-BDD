# Kingdomino Origins — BDD (ESGI 4AL1)

Application de simulation du jeu **Kingdomino Origins** (Mode Découverte) spécifiée par scénarios Gherkin (Cucumber Java).

**Stack :** Java 17, Maven, Cucumber 7, JUnit 5

## Commandes

```bash
mvn test          # Exécuter tous les scénarios Cucumber
mvn clean test    # Rebuild + exécution
```

## Architecture

```
src/
├── main/java/com/cts/
│   ├── domain/          ← Cœur métier (découplé du framework BDD)
│   │   ├── model/       ← Tile, Kingdom, Terrain, Player...
│   │   ├── service/     ← PlacementService, ScoringService...
│   │   └── exception/   ← InvalidPlacementException...
│   └── framework/       ← Config & helpers
├── test/java/com/cts/
│   ├── stepdefinitions/ ← Steps Cucumber (orchestration uniquement)
│   ├── runners/         ← TestRunner JUnit
│   └── features/        ← Fichiers .feature Gherkin
```

## Périmètre

Mode Découverte uniquement — voir [docs/PERIMETRE.md](docs/PERIMETRE.md).
