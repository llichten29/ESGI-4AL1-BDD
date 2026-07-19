Feature: Mise en place d'une partie
  En tant qu'organisateur du jeu
  Je veux initialiser une partie selon les regles
  Afin de commencer a jouer

  Scenario: Partie a 4 joueurs avec seed fixe
    Given une partie avec 4 joueurs et seed 42
    Then la pioche contient 48 dominos
    And la premiere ligne de draft expose 4 dominos classes par numero croissant
    And chaque joueur possede une tuile de depart avec sa hutte
    And chaque joueur a place son chef de tribu sur un domino de la ligne

  Scenario: Partie a 3 joueurs
    Given une partie avec 3 joueurs et seed 42
    Then la pioche contient 48 dominos
    And la premiere ligne de draft expose 4 dominos dont 1 est sans chef
    And ce domino sans chef sera defausse a la fin du tour

  Scenario: Meme seed produit le meme ordre de pioche
    Given une partie avec 4 joueurs et seed 123
    And une autre partie avec 4 joueurs et seed 123
    Then les deux pioches ont le meme ordre de dominos

  Scenario: Nombre de joueurs insuffisant
    Given une partie avec 1 joueur et seed 42
    Then la creation de la partie echoue car "une partie necessite 3 ou 4 joueurs"

  Scenario: Trop de joueurs
    Given une partie avec 5 joueurs et seed 42
    Then la creation de la partie echoue car "une partie necessite 3 ou 4 joueurs"
