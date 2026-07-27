# Périmètre du projet — Kingdomino Origins (Mode Totem)

## Règles implémentées (à compléter)

- Tout le Mode Découverte (voir PERIMETRE_DECOUVERTE.md)
- **Ressources** : placées sur les cases sans icône Feu à chaque nouvelle ligne
  - Steppe → Mammouth, Lac → Poisson, Jungle → Champignon, Carrière → Silex
  - Déserts et Volcans ne reçoivent aucune ressource
- **Projection de feu** : détruit la ressource sur la case ciblée
- **Tuiles Totem** : 4 tuiles (Mammouth, Poisson, Champignon, Silex)
  - Règle de majorité stricte : le joueur avec le plus de ressources d'un type récupère le totem correspondant
- **Scoring** : calcul Découverte + 1 point par ressource + points des tuiles Totem

> Note : les bonus optionnels *Empire du Feu* et *Homo Habilis* (voir PERIMETRE_DECOUVERTE.md)
> restent disponibles en Mode Totem.

## Règles non implémentées (hors périmètre actuel)

- Mode Tribu (Cro-magnons, plateau Grotte)
- Variante 2 joueurs

## Hypothèses de travail

- Les ressources sont placées au moment de la révélation de chaque ligne de 4 dominos
- Les totems sont réévalués à chaque pose de domino
- En cas d'égalité de majorité, le totem reste chez son propriétaire actuel
