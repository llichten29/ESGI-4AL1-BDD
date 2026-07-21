Feature: Boucle de jeu multi-tours
    En tant que joueur
    Je veux enchainer les tours de jeu
    Afin de jouer une partie complete

    Scenario: Nouvelle draft apres un tour complet
        Given une partie de 4 joueurs avec seed 42
        When tous les joueurs choisissent un domino
        And le tour suivant commence
        Then une nouvelle draft de 4 dominos est disponible
        And le numero du tour est 2

    Scenario: La pioche diminue a chaque tour
        Given une partie de 4 joueurs avec seed 42
        Then la pioche contient 44 dominos
        When tous les joueurs choisissent un domino
        And le tour suivant commence
        Then la pioche contient 40 dominos
        When tous les joueurs choisissent un domino
        And le tour suivant commence
        Then la pioche contient 36 dominos

    Scenario: 3 joueurs avec domino non choisi defausse
        Given une partie de 3 joueurs avec seed 42
        When tous les joueurs choisissent un domino
        Then 1 domino reste sans chef et sera defausse
        When le tour suivant commence
        Then le domino non choisi a ete defausse
        And une nouvelle draft de 4 dominos est disponible

    Scenario: Derniere ligne formee fin de partie
        Given une partie de 4 joueurs avec seed 42
        When on joue jusqu'a la derniere ligne
        Then la derniere ligne de dominos est formee
        And aucun nouveau tour ne peut commencer
        And le numero du tour est 12
