# 01 — Placement des dominos

> Source : `decouverte/02_placement.feature`
>
> Émojis : 🏰 Château · 🌿 Steppe · 🏞️ Lac · 🌳 Jungle · ⛰️ Carrière · 🏜️ Désert · 🌋 Volcan · 🔥 Icône feu · (🔥) Jeton feu

---

### 🔢 01 — Poser le premier domino adjacent à la tuile départ

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🔵 When poser 🌿-🏞️ en (2,1) & (2,0)
🟣 Then ✅ accepté · territoire = 3 cases

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 | 🏞️ | 🌿 | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

---

### 🔢 02 — Premier domino jungle sur le château

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🔵 When poser 🌳-⛰️ en (3,2) & (4,2)
🟣 Then ✅ accepté

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 |   |   | 🏰 |   |   |
| 3 |   |   | 🌳 |   |   |
| 4 |   |   | ⛰️ |   |   |

---

### 🔢 03 — Premier domino carrière sur le château

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🔵 When poser ⛰️-🏞️ en (1,2) & (0,2)
🟣 Then ✅ accepté

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   | 🏞️ |   |   |
| 1 |   |   | ⛰️ |   |   |
| 2 |   |   | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

---

### 🔢 04 — Premier domino lac sur le château en horizontal

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🔵 When poser 🏞️-🌿 en (2,3) & (2,4)
🟣 Then ✅ accepté

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 |   |   | 🏰 | 🏞️ | 🌿 |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

---

### 🔢 05 — Premier domino désert sur le château en horizontal

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🔵 When poser 🏜️-🌳 en (2,1) & (2,0)
🟣 Then ✅ accepté

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 | 🌳 | 🏜️ | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

---

### 🔢 06 — Premier domino volcan sur le château

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🔵 When poser 🌋-🌿 en (3,2) & (4,2)
🟣 Then ✅ accepté

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 |   |   | 🏰 |   |   |
| 3 |   |   | 🌋 |   |   |
| 4 |   |   | 🌿 |   |   |

---

### 🔢 07 — Connecter un domino à un terrain identique (steppe)

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé 🌿-🏜️ en (2,1) & (2,0)
🔵 When poser 🌿-🌿 en (1,1) & (1,0)
🟣 Then ✅ accepté (🌿 en (1,1) ↔ 🌿 en (2,1))

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 | 🌿 | 🌿 |   |   |   |
| 2 | 🏜️ | 🌿 | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

---

### 🔢 08 — Connecter un domino jungle à un terrain jungle

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé 🌳-🌿 en (3,2) & (4,2)
🔵 When poser 🌳-🏞️ en (3,1) & (3,0)
🟣 Then ✅ accepté (🌳 en (3,1) ↔ 🌳 en (3,2))

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 |   |   | 🏰 |   |   |
| 3 | 🏞️ | 🌳 | 🌳 |   |   |
| 4 |   |   | 🌿 |   |   |

---

### 🔢 09 — Connecter un domino carrière à un terrain carrière

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé ⛰️-🌳 en (1,2) & (0,2)
🔵 When poser ⛰️-🏞️ en (1,1) & (1,0)
🟣 Then ✅ accepté (⛰️ en (1,1) ↔ ⛰️ en (1,2))

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   | 🌳 |   |   |
| 1 | 🏞️ | ⛰️ | ⛰️ |   |   |
| 2 |   |   | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

---

### 🔢 10 — Connecter un domino lac à un terrain lac

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé 🏞️-🌳 en (2,3) & (2,4)
🔵 When poser 🏞️-🌿 en (1,3) & (1,4)
🟣 Then ✅ accepté (🏞️ en (1,3) ↔ 🏞️ en (2,3))

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   | 🏞️ | 🌿 |
| 2 |   |   | 🏰 | 🏞️ | 🌳 |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

---

### 🔢 11 — Connecter un domino désert à un terrain désert

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé 🏜️-⛰️ en (2,1) & (2,0)
🔵 When poser 🏜️-🌿 en (1,1) & (1,0)
🟣 Then ✅ accepté (🏜️ en (1,1) ↔ 🏜️ en (2,1))

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 | 🌿 | 🏜️ |   |   |   |
| 2 | ⛰️ | 🏜️ | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

---

### 🔢 12 — Connecter un domino volcan à un terrain volcan

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé 🌋-🌿 en (2,3) & (2,4)
🔵 When poser 🌋-🌳 en (1,3) & (1,4)
🟣 Then ✅ accepté (🌋 en (1,3) ↔ 🌋 en (2,3))

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   | 🌋 | 🌳 |
| 2 |   |   | 🏰 | 🌋 | 🌿 |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

---

### 🔢 13 — Choisir entre plusieurs positions valides

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé 🌿-🏜️ en (2,1) & (2,0)
🔵 When chercher positions valides pour 🌿-🏞️
🟣 Then ✅ plusieurs positions proposées

📐 **Grille (état initial) :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 | 🏜️ | 🌿 | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

💡 Positions valides possibles : (2,3)-(2,4) · (1,1)-(1,0) · (3,1)-(3,0) · (1,2)-(0,2) · (3,2)-(4,2)

---

### 🔢 14 — Défausse quand aucun placement possible

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And 4 dominos posés autour du 🏰
🔵 When recevoir 🏞️-🏞️
🟣 Then ❌ impossible · domino défaussé · 0 point

📐 **Grille (toutes les cases adjacentes au 🏰 occupées) :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   | 🌳 |   |   |
| 1 |   |   | 🌿 |   |   |
| 2 | 🏜️ | 🌿 | 🏰 | 🌿 | 🏜️ |
| 3 |   |   | 🌿 |   |   |
| 4 |   |   | ⛰️ |   |   |

💡 Aucune case 🌿 ou 🏞️ adjacente — le 🏞️-🏞️ ne peut être connecté nulle part.

---

### 🔢 15 — Obligation de poser si possible

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé 🌿-🏜️ en (2,1) & (2,0)
🟢 And recevoir 🌿-🌿
🟣 Then ✅ obligé de poser sur une position valide

📐 **Grille (état initial) :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 | 🏜️ | 🌿 | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

💡 Positions valides pour 🌿-🌿 : (1,1)-(1,0) · (3,1)-(3,0) · (1,2)-(0,2) · (3,2)-(4,2)

---

### 🔢 16 — Placement sur une case déjà occupée

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé 🌿-🏜️ en (2,1) & (2,0)
🔵 When tenter poser en (2,2) & (2,1)
🟣 Then ❌ refusé : "case déjà occupée"

📐 **Grille (état initial) :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 | 🏜️ | 🌿 | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

❌ Tentative : (2,2)=🏰 déjà occupé · (2,1)=🌿 déjà occupé

---

### 🔢 17 — Aucun terrain adjacent compatible

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé 🏜️-🌿 en (2,1) & (2,0)
🔵 When tenter poser 🌿-🏞️ en (0,0) & (0,1)
🟣 Then ❌ refusé : "aucun terrain adjacent compatible"

📐 **Grille (état initial) :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 | 🌿 | 🏜️ | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

❌ Tentative : (0,0)-(0,1) — aucune des deux cases n'est adjacente au royaume existant

---

### 🔢 18 — Domino dépassant la grille 5×5

📋 **Steps :**
🟢 Given 🏰 placé en (2,2)
🟢 And posé 🌿-🏜️ en (2,1) & (2,0)
🔵 When tenter poser en (2,4) & (2,5)
🟣 Then ❌ refusé : "hors de la grille 5×5"

📐 **Grille (état initial) :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 | 🏜️ | 🌿 | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

❌ Tentative : (2,5) est hors grille (colonnes 0-4 uniquement)
