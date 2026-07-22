Feature: Bonus optionnels (Empire du Feu et Homo Habilis)
    En tant que joueur
    Je veux obtenir des points bonus en fin de partie
    Afin de recompenser la maitrise de mon territoire

    Background:
        Given un joueur "Alice (rose)" avec la tuile depart en position (2,2)

    # --- Empire du Feu ---

    Scenario: Hutte entouree rapporte le bonus Empire du Feu
        Given le joueur a deja pose un domino steppe-desert en (2,1) et (2,0)
        And le joueur a deja pose un domino steppe-lac en (2,3) et (2,4)
        And le joueur a deja pose un domino steppe-jungle en (1,2) et (0,2)
        And le joueur a deja pose un domino steppe-carriere en (3,2) et (4,2)
        When le joueur calcule son score
        And les bonus optionnels sont calcules
        Then le score total est de 10

    Scenario: Un cote manquant annule le bonus Empire du Feu
        Given le joueur a deja pose un domino steppe-desert en (2,1) et (2,0)
        And le joueur a deja pose un domino steppe-lac en (2,3) et (2,4)
        And le joueur a deja pose un domino steppe-jungle en (1,2) et (0,2)
        When le joueur calcule son score
        And les bonus optionnels sont calcules
        Then le score total est de 0

    Scenario: Aucune cellule adjacente ne rapporte aucun bonus
        When le joueur calcule son score
        And les bonus optionnels sont calcules
        Then le score total est de 0

    # --- Homo Habilis ---

    Scenario: Territoire complet cumule les deux bonus
        Given un territoire de 25 cases
        When le joueur calcule son score
        And les bonus optionnels sont calcules
        Then le score total est de 15

    Scenario: Petit territoire incomplet ne rapporte aucun bonus
        Given un territoire de 3 cases
        When le joueur calcule son score
        And les bonus optionnels sont calcules
        Then le score total est de 0

    # --- Bonus ajoutes a un score existant ---

    Scenario: Empire du Feu ajoute 10 points au score des regions
        Given le joueur a deja pose un domino steppe-lac en (2,1) et (2,0)
        And un jeton feu de valeur 1 place en (2,1)
        And le joueur a deja pose un domino steppe-desert en (2,3) et (2,4)
        And le joueur a deja pose un domino steppe-jungle en (1,2) et (0,2)
        And le joueur a deja pose un domino steppe-carriere en (3,2) et (4,2)
        When le joueur calcule son score
        And les bonus optionnels sont calcules
        Then le score total est de 11

    Scenario: Homo Habilis ajoute 5 points au score des regions
        Given un territoire de 25 cases
        And un jeton feu de valeur 1 place en (0,0)
        When le joueur calcule son score
        And les bonus optionnels sont calcules
        Then le score total est de 39

    Scenario: Les deux bonus cumules ajoutent 15 points au score des regions
        Given un territoire de 25 cases
        And un jeton feu de valeur 2 place en (0,0)
        When le joueur calcule son score
        And les bonus optionnels sont calcules
        Then le score total est de 63
