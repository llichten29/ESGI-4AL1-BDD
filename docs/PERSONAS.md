# Personas — Kingdomino Origins (BDD)

Ces quatre personas donnent un visage aux joueurs des scénarios Gherkin. Ils rendent les
scénarios plus concrets et mémorables, sans rien changer aux règles : ce sont les **mêmes**
quatre joueurs déjà câblés dans le domaine.

Le mapping nom → couleur est la source de vérité côté code :
[`PlayerColor.NAME_MAP`](../src/main/java/com/cts/domain/model/player/PlayerColor.java) — et ces
noms sont aussi les noms par défaut d'une partie (`GameService`).

## La troupe

| Persona | Couleur | Rôle / trait | Affinité terrain → ressource |
|-----------|---------|-----------------------------|------------------------------|
| **Alice**   | rose  | l'éleveuse de mammouths     | steppe → **Mammouth**   |
| **Bastien** | noir  | le pêcheur                  | lac → **Poisson**       |
| **Camille** | vert  | la cueilleuse de champignons| jungle → **Champignon** |
| **David**   | bleu  | le tailleur de silex        | carrière → **Silex**    |

Ces affinités correspondent aux ressources du Mode Totem : chaque persona « vise » naturellement
sa ressource de prédilection, ce qui éclaire les scénarios de majorité (ex. Alice vs Bastien sur
le totem Mammouth).

## Convention d'usage dans les scénarios

- **Features mono-territoire** (règles de placement, volcans, scoring, bonus, ressources) : elles
  suivent **Alice** comme protagoniste par défaut. Le `Background` la nomme (`"Alice (rose)"`), et
  les étapes parlent du « joueur » — il s'agit d'Alice.
- **Features multi-joueurs** (mise en place, ordre du tour, boucle de jeu, totems, scoring Totem) :
  elles mettent en scène **la troupe complète** et leurs rivalités d'affinité.

## Référence dans les steps

Une référence de joueur s'écrit `"<Nom> (<couleur>)"`, par ex. `"Alice (rose)"`.
Le nom seul suffit aussi (`"Alice"`) : la couleur est déduite via `PlayerColor.forPlayerName`.
Voir `WorldContext.parsePlayerColor` / `WorldContext.extractPlayerName`.
