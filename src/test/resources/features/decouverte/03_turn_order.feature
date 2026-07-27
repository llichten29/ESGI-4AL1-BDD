Feature: Ordre du tour

    Rule: L'ordre du tour suit la position des chefs sur la ligne (numero croissant)

        Scenario: Ordre de jeu base sur la position des chefs dans la draft
            Given une partie de 4 joueurs avec seed 42
            When le joueur "Alice (rose)" choisit le domino numero 2
            And le joueur "Bastien (noir)" choisit le domino numero 21
            And le joueur "Camille (vert)" choisit le domino numero 28
            And le joueur "David (bleu)" choisit le domino numero 47
            Then l'ordre de jeu est "Alice (rose)", "Bastien (noir)", "Camille (vert)", "David (bleu)"

        Scenario: Le domino le plus petit donne le premier joueur
            Given une partie de 4 joueurs avec seed 42
            When le joueur "Camille (vert)" choisit le domino numero 21
            And le joueur "Alice (rose)" choisit le domino numero 2
            And le joueur "Bastien (noir)" choisit le domino numero 28
            And le joueur "David (bleu)" choisit le domino numero 47
            Then le premier joueur est "Alice (rose)"

    Rule: A 3 joueurs, un domino reste sans chef et sera defausse

        Scenario: 3 joueurs avec un domino non choisi
            Given une partie de 3 joueurs avec seed 42
            When le joueur "Alice (rose)" choisit le domino numero 2
            And le joueur "Bastien (noir)" choisit le domino numero 21
            And le joueur "Camille (vert)" choisit le domino numero 28
            Then 1 domino reste sans chef et sera defausse

    Rule: Un domino deja choisi ne peut plus etre selectionne

        Scenario: Un domino deja choisi ne peut pas etre reselectionne
            Given une partie de 4 joueurs avec seed 42
            When le joueur "Alice (rose)" choisit le domino numero 2
            Then le joueur "Bastien (noir)" ne peut pas choisir le domino numero 2
