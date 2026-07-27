# Périmètre du projet — Kingdomino Origins (Mode Découverte)

## Règles implémentées

- **Grille 5×5** : chaque joueur développe un territoire de 25 cases avec une tuile de départ (joker) au centre
- **Dominos** : chaque domino a 2 cases, un terrain et un nombre d'icônes Feu par case
- **Pioche et draft** : 48 dominos mélangés (seed reproductible), lignes de 4 classées par ordre croissant
- **Chefs de tribu** : ordre de tour déterminé par la position du chef sur la ligne de draft
- **Placement** : connexion à la tuile de départ (joker) ou à un terrain identique ; le domino doit être posé si possible, défaussé sinon (zéro point)
- **Volcans** : 3 types (1, 2, 3 cratères) → récupération du jeton Feu correspondant
- **Projection de feu** : le jeton Feu est placé sur une case libre (pas de jeton/icône Feu, pas de volcan) dans n'importe quelle direction ; défaussé si aucune case valide
- **Scoring** : chaque région (groupe orthogonal même terrain) rapporte `taille × nombre d'icônes Feu` ; région sans feu = 0 pt ; volcan = 0 pt
- **Égalités** : 1. plus grande région, 2. plus d'icônes Feu, 3. victoire partagée
- **12 tours** à 3 ou 4 joueurs
- **Dernier tour** : pose uniquement (pas de sélection)
- **3 joueurs** : un domino est défaussé par draft
- **4 joueurs** : dernier joueur prend le domino restant (choix forcé)
- **Bonus optionnels** :
  - *Empire du Feu* : +10 points si la hutte (case de départ) est entourée sur ses 4 côtés
  - *Homo Habilis* : +5 points si le territoire est complet (25 cases, aucun domino défaussé)

## Règles exclues (hors périmètre)

- Mode Tribu (Cro-magnons, plateau Grotte)
- Variante 2 joueurs (Néolithique, grille 7×7, 48 dominos)
- Extensions non officielles

## Hypothèses de travail

- La tuile de départ occupe toujours la position centrale (2,2) de la grille 5×5
- Les dominos sont mélangés avec `java.util.Random(seed)` au début de la partie
- La seed est configurable dans les hooks Cucumber (`@Before`)
- Un tour = pose + sélection (sauf dernier tour : pose uniquement)
- Les jetons Feu sont placés immédiatement après la pose d'un volcan, avant la sélection du prochain domino
