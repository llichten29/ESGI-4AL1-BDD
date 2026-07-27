# Kingdomino Origins — BDD (ESGI 4AL1)

Application de simulation du jeu **Kingdomino Origins** (Modes Découverte et Totem) spécifiée par scénarios Gherkin (Cucumber Java).

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

**Modes implémentés :**
- **Découverte** — [docs/PERIMETRE_DECOUVERTE.md](docs/PERIMETRE_DECOUVERTE.md) (placement, volcans, scoring)
- **Totem** — [docs/PERIMETRE_TOTEM.md](docs/PERIMETRE_TOTEM.md) (ressources, totems, scoring Totem)
- **Bonus optionnels** — Empire du Feu (+10) et Homo Habilis (+5), applicables aux deux modes

**Hors périmètre :** Mode Tribu, variante 2 joueurs
