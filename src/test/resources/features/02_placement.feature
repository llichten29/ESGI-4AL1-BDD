Feature: Placement des dominos
    En tant que joueur
    Je veux poser mes dominos dans mon territoire
    Afin de developper mon royaume

    Background:
        Given un joueur "rose" avec la tuile depart en position (2,2)

    Scenario: Poser le premier domino adjacent a la tuile depart
        When le joueur pose un domino steppe-lac en (2,1) et (2,0)
        Then le placement est accepte
        And le territoire contient 3 cases

    Scenario: Connecter un domino a un terrain identique
        Given le joueur a deja pose une steppe en (2,1)
        When le joueur pose un domino steppe-steppe en (2,0) et (1,0)
        Then le placement est accepte

    Scenario: Choisir entre plusieurs positions valides
        Given le joueur a deja pose une steppe en (2,1)
        When le joueur cherche les positions valides pour un domino steppe-lac
        Then plusieurs positions sont proposees

    Scenario: Defausse quand aucun placement possible
        Given le joueur a deja pose une steppe en (2,1)
        And le joueur a deja pose une steppe en (2,3)
        And le joueur a deja pose une steppe en (1,2)
        And le joueur a deja pose une steppe en (3,2)
        When le joueur recoit un domino lac-lac
        Then le domino ne peut pas etre place
        And le domino est defausse et ne rapporte aucun point

    Scenario: Obligation de poser si possible
        Given le joueur a deja pose une steppe en (2,1)
        And le joueur recoit un domino steppe-steppe
        Then le joueur est oblige de le poser sur une position valide

    Scenario: Placement sur une case deja occupee
        Given le joueur a deja pose une steppe en (2,1)
        When le joueur tente de poser un domino en (2,2) et (2,1)
        Then le placement est refuse car "case deja occupee"

    Scenario: Aucun terrain adjacent compatible
        Given le joueur a deja pose une desert en (2,1)
        When le joueur tente de poser un domino steppe-lac en (0,0) et (0,1)
        Then le placement est refuse car "aucun terrain adjacent compatible"

    Scenario: Domino depassant la grille 5x5
        Given le joueur a deja pose une steppe en (2,1)
        When le joueur tente de poser un domino en (2,4) et (2,5)
        Then le placement est refuse car "hors de la grille 5x5"
