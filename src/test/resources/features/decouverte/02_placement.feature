Feature: Placement des dominos
    En tant que joueur
    Je veux poser mes dominos dans mon territoire
    Afin de developper mon royaume

    Background:
        Given un joueur "Alice (rose)" avec la tuile depart en position (2,2)

    Scenario: Poser le premier domino adjacent a la tuile depart
        When le joueur pose un domino steppe-lac en (2,1) et (2,0)
        Then le placement est accepte
        And le territoire contient 3 cases

    # --- Premier domino : tous les terrains se connectent au chateau ---

    Scenario Outline: Premier domino <t1> sur le chateau (joker)
        When le joueur pose un domino <t1>-<t2> en (<x1>,<y1>) et (<x2>,<y2>)
        Then le placement est accepte

        Examples:
            | t1       | t2       | x1 | y1 | x2 | y2 |
            | jungle   | carriere | 3  | 2  | 4  | 2  |
            | carriere | lac      | 1  | 2  | 0  | 2  |
            | lac      | steppe   | 2  | 3  | 2  | 4  |
            | desert   | jungle   | 2  | 1  | 2  | 0  |
            | volcan   | steppe   | 3  | 2  | 4  | 2  |

    # --- Connexion a un terrain identique : chaque biome ---

    Scenario Outline: Connecter un domino <biome> a un terrain <biome> identique
        Given le joueur a deja pose un domino <pre1>-<pre2> en (<px1>,<py1>) et (<px2>,<py2>)
        When le joueur pose un domino <biome>-<t2> en (<x1>,<y1>) et (<x2>,<y2>)
        Then le placement est accepte

        Examples:
            | biome    | pre1     | pre2     | px1 | py1 | px2 | py2 | t2     | x1 | y1 | x2 | y2 |
            | steppe   | steppe   | desert   | 2   | 1   | 2   | 0   | steppe | 1  | 1  | 1  | 0  |
            | jungle   | jungle   | steppe   | 3   | 2   | 4   | 2   | lac    | 3  | 1  | 3  | 0  |
            | carriere | carriere | jungle   | 1   | 2   | 0   | 2   | lac    | 1  | 1  | 1  | 0  |
            | lac      | lac      | jungle   | 2   | 3   | 2   | 4   | steppe | 1  | 3  | 1  | 4  |
            | desert   | desert   | carriere | 2   | 1   | 2   | 0   | steppe | 1  | 1  | 1  | 0  |
            | volcan   | volcan   | steppe   | 2   | 3   | 2   | 4   | jungle | 1  | 3  | 1  | 4  |

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
