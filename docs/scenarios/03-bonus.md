# 03 — Bonus optionnels (Empire du Feu & Homo Habilis)

> Source : `decouverte/07_bonus.feature`
>
> Émojis : 🏰 Château · 🌿 Steppe · 🏞️ Lac · 🌳 Jungle · ⛰️ Carrière · 🏜️ Désert · 🌋 Volcan · 🔥 Icône feu · (🔥) Jeton feu

---

### 🔢 01 — Hutte entourée rapporte le bonus Empire du Feu

📋 **Steps :**
🟢 Given 🏰 en (2,2)
🟢 And 4 dominos posés autour du 🏰 (🌿 aux 4 adjacentes)
🟢 Domino steppe-desert en (2,1) & (2,0)
🟢 Domino steppe-lac en (2,3) & (2,4)
🟢 Domino steppe-jungle en (1,2) & (0,2)
🟢 Domino steppe-carriere en (3,2) & (4,2)
🔵 When calculer score + bonus
🟣 Then ✅ score = 10

📐 **Grille :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   | 🌳 |   |   |
| 1 |   |   | 🌿 |   |   |
| 2 | 🏜️ | 🌿 | 🏰 | 🌿 | 🏞️ |
| 3 |   |   | 🌿 |   |   |
| 4 |   |   | ⛰️ |   |   |

💡 Les 4 cellules adjacentes au 🏰 sont toutes 🌿 → bonus Empire du Feu = +10 (en plus du score des régions qui est 0 ici car aucune région n'a 🔥)

---

### 🔢 02 — Un côté manquant annule le bonus Empire du Feu

📋 **Steps :**
🟢 Given 🏰 en (2,2)
🟢 And 3 dominos posés (côté carrière manquant)
🟢 Domino steppe-desert en (2,1) & (2,0)
🟢 Domino steppe-lac en (2,3) & (2,4)
🟢 Domino steppe-jungle en (1,2) & (0,2)
🔵 When calculer score + bonus
🟣 Then ✅ score = 0

📐 **Grille :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   | 🌳 |   |   |
| 1 |   |   | 🌿 |   |   |
| 2 | 🏜️ | 🌿 | 🏰 | 🌿 | 🏞️ |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

💡 Case (3,2) vide → pas de 🌿 en bas → bonus Empire du Feu non déclenché

---

### 🔢 03 — Aucune cellule adjacente ne rapporte aucun bonus

📋 **Steps :**
🟢 Given 🏰 en (2,2) uniquement
🔵 When calculer score + bonus
🟣 Then ✅ score = 0

📐 **Grille :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 |   |   | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

💡 Aucune cellule adjacente → aucun bonus.

---

### 🔢 04 — Territoire complet cumule les deux bonus

📋 **Steps :**
🟢 Given territoire de 25 cases
🔵 When calculer score + bonus
🟣 Then ✅ score = 15

📐 **Grille (exemple de royaume 5×5 valide) :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 | 🌿 | 🌳 | 🌳 | 🌳 | 🏞️ |
| 1 | 🏜️ | 🌿 | 🌿 | 🌿 | 🏞️ |
| 2 | 🏜️ | 🌿 | 🏰 | 🌿 | 🏞️ |
| 3 | 🏜️ | 🌿 | 🌿 | 🌿 | 🏞️ |
| 4 | 🏜️ | ⛰️ | ⛰️ | 🌿 | 🏞️ |

💡 25 cases = royaume complet → bonus Homo Habilis (+5). Les 4 cellules adjacentes au 🏰 sont 🌿 → Empire du Feu (+10). Total = 15.

---

### 🔢 05 — Petit territoire incomplet ne rapporte aucun bonus

📋 **Steps :**
🟢 Given territoire de 3 cases
🔵 When calculer score + bonus
🟣 Then ✅ score = 0

📐 **Grille :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   |   |   |   |
| 2 | 🏞️ | 🌿 | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

💡 3 cases < 25 → ni Homo Habilis (+5) ni Empire du Feu (+10) déclenchés. Score = 0.

---

### 🔢 06 — Empire du Feu ajoute 10 points au score des régions

📋 **Steps :**
🟢 Given 🏰 en (2,2)
🟢 And domino steppe-lac en (2,1) & (2,0)
🟢 And (🔥) 1 placé en (2,1) → steppe 🔥 1
🟢 And 3 autres dominos autour (steppe-desert, steppe-jungle, steppe-carriere)
🔵 When calculer score + bonus
🟣 Then ✅ score = 11

📐 **Grille :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   | 🌳 |   |   |
| 1 |   |   | 🌿 |   |   |
| 2 | 🏞️ | 🌿(🔥) | 🏰 | 🌿 | 🏜️ |
| 3 |   |   | 🌿 |   |   |
| 4 |   |   | ⛰️ |   |   |

💡 Score = région steppe 5 cases × 0 🔥 + (🔥) 1 = 1 + bonus Empire du Feu 10 = 11

---

### 🔢 07 — Homo Habilis ajoute 5 points au score des régions

📋 **Steps :**
🟢 Given territoire de 25 cases
🟢 And (🔥) 1 placé en (0,0)
🔵 When calculer score + bonus
🟣 Then ✅ score = 39

📐 **Grille :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 | 🌿(🔥) | 🌳 | 🌳 | 🌳 | 🏞️ |
| 1 | 🏜️ | 🌿 | 🌿 | 🌿 | 🏞️ |
| 2 | 🏜️ | 🌿 | 🏰 | 🌿 | 🏞️ |
| 3 | 🏜️ | 🌿 | 🌿 | 🌿 | 🏞️ |
| 4 | 🏜️ | ⛰️ | ⛰️ | 🌿 | 🏞️ |

💡 Score = régions (34) + Homo Habilis (5) = 39

---

### 🔢 08 — Les deux bonus cumulés ajoutent 15 points au score des régions

📋 **Steps :**
🟢 Given territoire de 25 cases
🟢 And (🔥) 2 placé en (0,0)
🔵 When calculer score + bonus
🟣 Then ✅ score = 63

📐 **Grille :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 | 🌿(🔥🔥) | 🌳 | 🌳 | 🌳 | 🏞️ |
| 1 | 🏜️ | 🌿 | 🌿 | 🌿 | 🏞️ |
| 2 | 🏜️ | 🌿 | 🏰 | 🌿 | 🏞️ |
| 3 | 🏜️ | 🌿 | 🌿 | 🌿 | 🏞️ |
| 4 | 🏜️ | ⛰️ | ⛰️ | 🌿 | 🏞️ |

💡 Score = régions (48) + Homo Habilis (5) + Empire du Feu (10) = 63
