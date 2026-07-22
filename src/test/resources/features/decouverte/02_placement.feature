Feature: Placement des dominos
    En tant que joueur
    Je veux poser mes dominos dans mon territoire
    Afin de developper mon royaume

    Background:
        Given un joueur "Alice" avec la tuile depart en position (2,2)

    Scenario: Poser le premier domino adjacent a la tuile depart
        When le joueur pose un domino steppe-lac en (2,1) et (2,0)
        Then le placement est accepte
        And le territoire contient 3 cases

    # --- Premier domino : tous les terrains se connectent au chateau ---

    Scenario: Premier domino jungle sur le chateau
        When le joueur pose un domino jungle-carriere en (3,2) et (4,2)
        Then le placement est accepte

    Scenario: Premier domino carriere sur le chateau
        When le joueur pose un domino carriere-lac en (1,2) et (0,2)
        Then le placement est accepte

    Scenario: Premier domino lac sur le chateau en horizontal
        When le joueur pose un domino lac-steppe en (2,3) et (2,4)
        Then le placement est accepte

    Scenario: Premier domino desert sur le chateau en horizontal
        When le joueur pose un domino desert-jungle en (2,1) et (2,0)
        Then le placement est accepte

    Scenario: Premier domino volcan sur le chateau
        When le joueur pose un domino volcan-steppe en (3,2) et (4,2)
        Then le placement est accepte

    # --- Connexion a un terrain identique : chaque biome ---

    Scenario: Connecter un domino a un terrain identique
        Given le joueur a deja pose un domino steppe-desert en (2,1) et (2,0)
        When le joueur pose un domino steppe-steppe en (1,1) et (1,0)
        Then le placement est accepte

    Scenario: Connecter un domino jungle a un terrain jungle
        Given le joueur a deja pose un domino jungle-steppe en (3,2) et (4,2)
        When le joueur pose un domino jungle-lac en (3,1) et (3,0)
        Then le placement est accepte

    Scenario: Connecter un domino carriere a un terrain carriere
        Given le joueur a deja pose un domino carriere-jungle en (1,2) et (0,2)
        When le joueur pose un domino carriere-lac en (1,1) et (1,0)
        Then le placement est accepte

    Scenario: Connecter un domino lac a un terrain lac
        Given le joueur a deja pose un domino lac-jungle en (2,3) et (2,4)
        When le joueur pose un domino lac-steppe en (1,3) et (1,4)
        Then le placement est accepte

    Scenario: Connecter un domino desert a un terrain desert
        Given le joueur a deja pose un domino desert-carriere en (2,1) et (2,0)
        When le joueur pose un domino desert-steppe en (1,1) et (1,0)
        Then le placement est accepte

    Scenario: Connecter un domino volcan a un terrain volcan
        Given le joueur a deja pose un domino volcan-steppe en (2,3) et (2,4)
        When le joueur pose un domino volcan-jungle en (1,3) et (1,4)
        Then le placement est accepte

    Scenario: Choisir entre plusieurs positions valides
        Given le joueur a deja pose un domino steppe-desert en (2,1) et (2,0)
        When le joueur cherche les positions valides pour un domino steppe-lac
        Then plusieurs positions sont proposees

    Scenario: Defausse quand aucun placement possible
        Given le joueur a deja pose un domino steppe-desert en (2,1) et (2,0)
        And le joueur a deja pose un domino steppe-desert en (2,3) et (2,4)
        And le joueur a deja pose un domino steppe-jungle en (1,2) et (0,2)
        And le joueur a deja pose un domino steppe-carriere en (3,2) et (4,2)
        When le joueur recoit un domino lac-lac
        Then le domino ne peut pas etre place
        And le domino est defausse et ne rapporte aucun point

    Scenario: Obligation de poser si possible
        Given le joueur a deja pose un domino steppe-desert en (2,1) et (2,0)
        And le joueur recoit un domino steppe-steppe
        Then le joueur est oblige de le poser sur une position valide

    Scenario: Placement sur une case deja occupee
        Given le joueur a deja pose un domino steppe-desert en (2,1) et (2,0)
        When le joueur tente de poser un domino en (2,2) et (2,1)
        Then le placement est refuse car "case deja occupee"

    Scenario: Aucun terrain adjacent compatible
        Given le joueur a deja pose un domino desert-steppe en (2,1) et (2,0)
        When le joueur tente de poser un domino steppe-lac en (0,0) et (0,1)
        Then le placement est refuse car "aucun terrain adjacent compatible"

    Scenario: Domino depassant la grille 5x5
        Given le joueur a deja pose un domino steppe-desert en (2,1) et (2,0)
        When le joueur tente de poser un domino en (2,4) et (2,5)
        Then le placement est refuse car "hors de la grille 5x5"
