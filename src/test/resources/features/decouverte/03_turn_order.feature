Feature: Ordre du tour

    Scenario: Ordre de jeu base sur la position des chefs dans la draft
        Given une partie de 4 joueurs avec seed 42
        When le joueur "Alice" choisit le domino numero 2
        And le joueur "Bastien" choisit le domino numero 21
        And le joueur "Camille" choisit le domino numero 28
        And le joueur "David" choisit le domino numero 47
        Then l'ordre de jeu est "Alice", "Bastien", "Camille", "David"

    Scenario: Le domino le plus petit donne le premier joueur
        Given une partie de 4 joueurs avec seed 42
        When le joueur "Camille" choisit le domino numero 21
        And le joueur "Alice" choisit le domino numero 2
        And le joueur "Bastien" choisit le domino numero 28
        And le joueur "David" choisit le domino numero 47
        Then le premier joueur est "Alice"

    Scenario: 3 joueurs avec un domino non choisi
        Given une partie de 3 joueurs avec seed 42
        When le joueur "Alice" choisit le domino numero 2
        And le joueur "Bastien" choisit le domino numero 21
        And le joueur "Camille" choisit le domino numero 28
        Then 1 domino reste sans chef et sera defausse

    Scenario: Un domino deja choisi ne peut pas etre reselectionne
        Given une partie de 4 joueurs avec seed 42
        When le joueur "Alice" choisit le domino numero 2
        Then le joueur "Bastien" ne peut pas choisir le domino numero 2
