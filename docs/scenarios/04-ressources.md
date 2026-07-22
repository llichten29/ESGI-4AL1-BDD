# 04 — Ressources en Mode Totem

> Source : `totem/01_resources.feature`
>
> Émojis : 🏰 Château · 🌿 Steppe · 🏞️ Lac · 🌳 Jungle · ⛰️ Carrière · 🏜️ Désert · 🌋 Volcan · 🔥 Icône feu · (🔥) Jeton feu
>
> Ressources : 🦣 Mammouth · 🐟 Poisson · 🍄 Champignon · 🪨 Silex

---

### 🔢 01 — Steppe reçoit une ressource Mammouth

📋 **Steps :**
🟢 Given 🌿 en (2,1) sans 🔥
🔵 When placer ressource sur (2,1)
🟣 Then ✅ (2,1) contient 🦣

💡 Steppe → 🦣

---

### 🔢 02 — Lac reçoit une ressource Poisson

📋 **Steps :**
🟢 Given 🏞️ en (2,1) sans 🔥
🔵 When placer ressource sur (2,1)
🟣 Then ✅ (2,1) contient 🐟

💡 Lac → 🐟

---

### 🔢 03 — Jungle reçoit une ressource Champignon

📋 **Steps :**
🟢 Given 🌳 en (2,1) sans 🔥
🔵 When placer ressource sur (2,1)
🟣 Then ✅ (2,1) contient 🍄

💡 Jungle → 🍄

---

### 🔢 04 — Carrière reçoit une ressource Silex

📋 **Steps :**
🟢 Given ⛰️ en (2,1) sans 🔥
🔵 When placer ressource sur (2,1)
🟣 Then ✅ (2,1) contient 🪨

💡 Carrière → 🪨

---

### 🔢 05 — Désert ne reçoit aucune ressource

📋 **Steps :**
🟢 Given 🏜️ en (2,1) sans 🔥
🔵 When placer ressource sur (2,1)
🟣 Then ✅ (2,1) ne contient rien

💡 Désert → aucune ressource

---

### 🔢 06 — Volcan ne reçoit aucune ressource

📋 **Steps :**
🟢 Given 🌋 en (2,1) sans 🔥
🔵 When placer ressource sur (2,1)
🟣 Then ✅ (2,1) ne contient rien

💡 Volcan → aucune ressource

---

### 🔢 07 — Case avec icône feu ne reçoit pas de ressource

📋 **Steps :**
🟢 Given 🌿 en (2,1) avec 🔥 1
🔵 When placer ressource sur (2,1)
🟣 Then ✅ (2,1) ne contient rien

💡 🔥 bloque la ressource

---

### 🔢 08 — Joueur collecte une ressource en posant un domino

📋 **Steps :**
🟢 Given 🏰 en (2,2)
🟢 And 🌿 posée en (1,2)
🟢 And recevoir domino 🌿-🏞️ avec 🦣
🔵 When poser le domino en (2,1) & (2,0)
🟣 Then ✅ Alice possède 1 🦣

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   | 🌿 |   |   |
| 2 | 🏞️ | 🌿 🦣 | 🏰 |   |   |
| 3 |   |   |   |   |   |
| 4 |   |   |   |   |   |

💡 Le domino apporte 🦣 sur la case 🌿 en (2,1) → Alice collecte 1 🦣

---

### 🔢 09 — Joueur collecte deux ressources sur un même domino

📋 **Steps :**
🟢 Given 🏰 en (2,2)
🟢 And 🌿 posée en (1,2)
🟢 And recevoir domino 🌿-🌿 avec 2 🦣
🔵 When poser le domino en (3,2) & (3,1)
🟣 Then ✅ Alice possède 2 🦣

📐 **Grille finale :**

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 |   |   |   |   |   |
| 1 |   |   | 🌿 |   |   |
| 2 |   |   | 🏰 |   |   |
| 3 |   | 🌿 🦣 | 🌿 🦣 |   |   |
| 4 |   |   |   |   |   |

💡 Domino 🌿-🌿 avec 2 🦣 → Alice collecte 2 🦣 en posant

---

### 🔢 10 — Feu détruit la ressource sur la case ciblée

📋 **Steps :**
🟢 Given 🌿 en (2,1) contenant 🦣
🟢 And 🌋 en (1,2) avec (🔥) 1
🔵 When projeter (🔥) 1 depuis (1,2) sur (2,1)
🟣 Then ✅ (2,1) ne contient plus rien
🟣 And ✅ Alice possède 0 🦣

💡 Le (🔥) détruit la ressource sur la case touchée

---

### 🔢 11 — Projeter le feu sur une case sans ressource ne change rien

📋 **Steps :**
🟢 Given 🌿 en (2,1) sans 🔥
🟢 And 🌋 en (1,2) avec (🔥) 1
🔵 When projeter (🔥) 1 depuis (1,2) sur (2,1)
🟣 Then ✅ (2,1) ne contient rien

💡 Pas de ressource à détruire → état inchangé
