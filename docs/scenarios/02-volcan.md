# 02 — Volcans et jetons Feu

> Source : `decouverte/05_volcano.feature`
>
> Émojis : 🏰 Château · 🌿 Steppe · 🏞️ Lac · 🌳 Jungle · ⛰️ Carrière · 🏜️ Désert · 🌋 Volcan · 🔥 Icône feu · (🔥) Jeton feu

---

### 🔢 01 — Volcan 1 cratère donne un jeton feu

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé 🌿 en (2,1)
🔵 When poser 🏞️-🌋 en (2,0) & (1,0)
🟣 Then ✅ reçu (🔥) valeur **1**

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 | 🌋 |   |   |   |   |
| 2 | 🏞️ | 🌿 | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

💡 Volcan à 1 cratère → jeton (🔥) de valeur 1 créé

---

### 🔢 02 — Volcan 2 cratères donne un jeton feu

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé 🌿 en (2,1)
🔵 When poser 🌳-🌋 en (3,2) & (3,1)
🟣 Then ✅ reçu (🔥) valeur **2**

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 |   | 🌿 | 🏰 |   |   |
| 3 |   | 🌋 | 🌳 |   |   |
| 4 |   |   |   |   |   |

💡 Volcan à 2 cratères → jeton (🔥) de valeur 2 créé

---

### 🔢 03 — Volcan 3 cratères donne un jeton feu

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé 🌿 en (2,1)
🔵 When poser 🏜️-🌋 en (2,3) & (2,4)
🟣 Then ✅ reçu (🔥) valeur **3**

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 |   | 🌿 | 🏰 | 🏜️ | 🌋 |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

💡 Volcan à 3 cratères → jeton (🔥) de valeur 3 créé

---

### 🔢 04 — Domino sans volcan ne donne pas de jeton

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé 🌿 en (2,1)
🔵 When poser 🌿-🌿 en (2,0) & (1,0)
🟣 Then ✅ aucun jeton reçu

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 | 🌿 |   |   |   |   |
| 2 | 🌿 | 🌿 | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

💡 Aucun 🌋 sur le domino → pas de (🔥)

---

### 🔢 05 — Projection d'un jeton feu sur la case départ

📋 **Steps :**
🟢 Given 🌋 en (2,3) avec (🔥) 2
🔵 When placer (🔥) 2 depuis (2,3) vers (2,2)
🟣 Then ✅ (🔥) placé en (2,2)

📐 **État avant projection :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 |   |   | 🏰 | 🌋 |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

📐 **État après projection :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 |   |   | 🏰(🔥) | 🌋 |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

💡 Le jeton (🔥) se projette du 🌋 (2,3) vers 🏰 (2,2) dans la limite de sa portée.

---

### 🔢 06 — Portée d'un jeton feu selon sa valeur

📋 **Steps :**
🟢 Given 🌋 en (2,3) avec (🔥) 1
🟣 Then ✅ (🔥) 1 a une portée de **3**

📐 **Grille :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 |   |   | 🏰 | 🌋 |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

💡 Portée = valeur du jeton + 2. (🔥) 1 → portée 3 cases. Cibles atteignables depuis (2,3) : (2,0)-(2,1)-(2,2)-(1,2)-(1,3)-(0,3)-(2,4) dans un rayon de 3.

---

### 🔢 07 — Placement d'un jeton feu hors de portée

📋 **Steps :**
🟢 Given 🌋 en (2,3) avec (🔥) 1
🔵 When tenter placer (🔥) 1 depuis (2,3) vers (0,0)
🟣 Then ❌ impossible : hors de portée

📐 **Grille :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 | ❌ |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 |   |   | 🏰 | 🌋 |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

💡 Distance (2,3)→(0,0) = |2-0| + |3-0| = **5**. Portée max = **3**. 5 > 3 → ❌.

---

### 🔢 08 — Pas de projection sur une case volcan

📋 **Steps :**
🟢 Given 🌋 en (2,3) avec (🔥) 3
🔵 When tenter placer (🔥) 3 depuis (2,3) vers (2,3)
🟣 Then ❌ impossible : case source = case cible

📐 **Grille :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 |   |   | 🏰 | 🌋❌ |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

💡 Impossible de projeter (🔥) sur la case 🌋 elle-même.

---

### 🔢 09 — Pas de projection sur une case avec icône feu

📋 **Steps :**
🟢 Given 🌋 en (2,3) avec (🔥) 3
🟢 And 🌳 en (1,2) avec 🔥 3
🔵 When tenter placer (🔥) 3 depuis (2,3) vers (1,2)
🟣 Then ❌ impossible : case déjà 🔥

📐 **Grille :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   | 🌳🔥 |   |   |
| 2 |   |   | 🏰 | 🌋 |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

❌ Tentative : (🔥) 3 depuis 🌋 (2,3) vers 🌳🔥 (1,2) — case déjà occupée par une 🔥 imprimée.

---

### 🔢 10 — Pas de deux jetons feu sur la même case

📋 **Steps :**
🟢 Given 🌋 en (2,3) avec (🔥) 2
🔵 When placer (🔥) 2 depuis (2,3) vers (2,2) ✅
🔵 When tenter placer (🔥) 2 depuis (2,3) vers (2,2)
🟣 Then ❌ impossible : (🔥) déjà présent

📐 **Grille après premier placement :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 |   |   | 🏰(🔥) | 🌋 |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

❌ Tentative seconde projection sur (2,2) : (🔥) déjà présent → impossible.

💡 Deux jetons (🔥) ne peuvent pas occuper la même case.

---

### 🔢 11 — Défausse d'un jeton feu

📋 **Steps :**
🟢 Given 🌋 en (2,3) avec (🔥) 3
🟢 And (🔥) 3 disponible
🟢 And (🔥) 3 déjà placé en (2,2)
🔵 When tenter placer le (🔥) depuis (2,3)
🟣 Then ✅ (🔥) défaussé (aucune case valide disponible)

📐 **Grille :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 |   |   | 🏰(🔥) | 🌋 |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

💡 (🔥) 3 déjà en (2,2). Portée depuis (2,3) : cases (2,0)-(2,1)-(2,2)-(1,1)-(1,2)-(1,3)-(0,2)-(0,3)-(2,4)-(3,2)-(3,3)-(4,2). Toutes les cases dans le rayon sont vides ou déjà occupées par (🔥) → (🔥) défaussé.
