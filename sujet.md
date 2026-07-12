**Projet Behaviour Driven Development**

Concevoir et tester un jeu de plateau par scénarios BDD

*Sujet, modalités et barème de soutenance*

Niveau : école d\'ingénieur / master

Format : en groupe --- soutenance de 20 minutes par groupe

1\. Contexte et objectifs

Ce projet prolonge le travail mené en cours autour du jeu Pandemic.
L\'objectif est d\'appliquer le Behaviour Driven Development (BDD) à un
domaine riche en règles : un jeu de plateau. Un bon jeu offre un cadre
idéal pour le BDD, car il combine des règles déterministes, des états
qui évoluent (plateau, ressources, main de cartes), des conditions de
victoire et de défaite claires, et de nombreux cas limites à spécifier.

Chaque groupe choisit l\'un des quatre jeux proposés, code une
application qui en applique les règles, puis en spécifie le comportement
à l\'aide de scénarios BDD écrits en Gherkin (Cucumber, Behave,
SpecFlow, Jest-Cucumber ou équivalent, au choix du groupe).
L\'application n\'a pas besoin d\'être jouable via une interface : elle
doit avant tout permettre d\'exécuter les scénarios. Les scénarios sont
entièrement conçus et rédigés par les étudiants : aucun scénario n\'est
fourni. Le cœur de l\'évaluation porte sur la qualité des scénarios
inventés et sur la fidélité avec laquelle ils décrivent les règles.

**Compétences visées :** traduire des règles métier en comportements
observables, écrire des scénarios lisibles par un non-développeur,
séparer la logique métier des étapes de test, et couvrir les cas
nominaux comme les cas limites.

2\. Modalités de la soutenance

La soutenance dure 20 minutes par groupe et se déroule en trois temps :

-   **Présentation (≈8 min) :** le jeu choisi, le périmètre implémenté,
    les règles retenues, et l\'architecture (séparation domaine / étapes
    Gherkin).

-   **Démonstration (≈5 min) :** exécution en direct de la suite de
    scénarios, avec au moins un test qui passe et un test que l\'on fait
    échouer volontairement pour montrer le lien règle vers comportement.

-   **Questions (≈7 min) :** échange sur les choix de conception et
    exercice « à la volée » (écrire ou modifier un scénario en direct).

**Point d\'attention --- déterminisme :** pour les jeux comportant un
tirage (pioche, dés), l\'aléatoire doit être neutralisé par une graine
(seed) fixe afin que les scénarios soient reproductibles.

3\. Consignes pour les étudiants

-   Choisir l\'un des quatre jeux proposés (section 4).

-   Coder une application qui implémente fidèlement les règles retenues
    (périmètre à cadrer et à assumer). L\'application n\'a pas besoin
    d\'être jouable via une interface : elle doit surtout permettre
    d\'exécuter les scénarios.

-   Inventer et rédiger vos propres scénarios en Gherkin : aucun
    scénario n\'est fourni, c\'est le cœur du travail.

-   Rendre les scénarios effectivement exécutés : chaque étape (step)
    doit être implémentée, aucun scénario ignoré (skipped).

-   Couvrir les cas nominaux, alternatifs, limites et d\'erreur
    (placement invalide, ressource épuisée, égalité, chaînes de
    déclenchement).

-   Séparer clairement la logique métier des étapes Gherkin : les steps
    orchestrent, le domaine décide.

-   Neutraliser l\'aléatoire par une seed pour garantir la
    reproductibilité des scénarios.

4\. Choisir son jeu

Chaque groupe choisit l\'un des quatre jeux suivants :
| Jeu            | Mécanique principale                                          |
| -------------- | ------------------------------------------------------------- |
| **Kingdomino** | Pose de dominos sur une grille 5×5, score surface × couronnes |
| **Cascadia**   | Pose de tuiles + animaux, objectifs de faune variables        |
| **Splendor**   | Économie / engine building, achat de cartes à réductions      |
| **Takenoko**   | Croissance de bambou, mouvement panda/jardinier, objectifs    |

**Note :** le travail attendu --- inventer les scénarios BDD --- est
identique quel que soit le jeu retenu.

5\. Barème de soutenance (/20)

Le barème privilégie le cœur de l\'exercice --- la qualité des scénarios
BDD conçus par les étudiants et leur lien avec le code --- plutôt que la
seule bonne marche de l\'application.

| Critère                      | Points |
| ---------------------------- | ------ |
| Qualité des scénarios BDD    | /6     |
| Lien scénarios vers code     | /4     |
| Qualité du code & conception | /3     |
| Couverture & robustesse      | /2     |
| Présentation orale           | /2     |
| Réponses aux questions       | /3     |
