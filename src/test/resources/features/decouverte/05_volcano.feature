Feature: Volcans et jetons Feu
    En tant qu'Alice, l'eleveuse de mammouths
    Je veux recevoir des jetons Feu en posant des dominos volcan
    Et les placer sur mon territoire pour marquer plus de points

    Background:
        Given un joueur "Alice (rose)" avec la tuile depart en position (2,2)

    Rule: Un domino volcan fournit le jeton Feu correspondant a ses crateres

        Scenario Outline: Volcan a <crateres> crateres donne un jeton feu de valeur <valeur>
            Given le joueur a deja pose une steppe en (2,1)
            When le joueur pose un domino <t1>-volcan en (<x1>,<y1>) et (<x2>,<y2>)
            Then le joueur recoit un jeton feu de valeur <valeur>

            Examples:
                | crateres | t1     | x1 | y1 | x2 | y2 | valeur |
                | 1        | lac    | 2  | 0  | 1  | 0  | 1      |
                | 2        | jungle | 3  | 2  | 3  | 1  | 2      |
                | 3        | desert | 2  | 3  | 2  | 4  | 3      |

        Scenario: Domino sans volcan ne donne pas de jeton
            Given le joueur a deja pose une steppe en (2,1)
            When le joueur pose un domino steppe-steppe en (2,0) et (1,0)
            Then le joueur ne recoit aucun jeton feu

    Rule: Un jeton Feu se projette sur une case libre a portee

        Scenario: Projection d'un jeton feu sur la case depart
            Given un volcan en (2,3) avec feu 2
            When le joueur place un jeton feu de valeur 2 depuis le volcan en (2,3) sur la case (2,2)
            Then le jeton feu est place en (2,2)

        Scenario: Portee d'un jeton feu selon sa valeur
            Given un volcan en (2,3) avec feu 1
            Then le jeton feu 1 a une portee de 3

    Rule: Une projection de feu impossible entraine la defausse du jeton

        Scenario: Placement d'un jeton feu hors de portee
            Given un volcan en (2,3) avec feu 1
            When le joueur tente de placer un jeton feu de valeur 1 depuis le volcan en (2,3) sur la case (0,0)
            Then le jeton feu ne peut pas etre place

        Scenario: Pas de projection sur une case volcan
            Given un volcan en (2,3) avec feu 3
            When le joueur tente de placer un jeton feu de valeur 3 depuis le volcan en (2,3) sur la case (2,3)
            Then le jeton feu ne peut pas etre place

        Scenario: Pas de projection sur une case avec icone feu
            Given un volcan en (2,3) avec feu 3
            And une case jungle en (1,2) avec icone feu 3
            When le joueur tente de placer un jeton feu de valeur 3 depuis le volcan en (2,3) sur la case (1,2)
            Then le jeton feu ne peut pas etre place

        Scenario: Pas de deux jetons feu sur la meme case
            Given un volcan en (2,3) avec feu 2
            When le joueur place un jeton feu de valeur 2 depuis le volcan en (2,3) sur la case (2,2)
            And le joueur tente de placer un jeton feu de valeur 2 depuis le volcan en (2,3) sur la case (2,2)
            Then le jeton feu ne peut pas etre place

        Scenario: Defausse d'un jeton feu
            Given un volcan en (2,3) avec feu 3
            And un jeton feu de valeur 3
            And un jeton feu de valeur 3 place en (2,2)
            When le joueur tente de placer le jeton feu depuis le volcan en (2,3)
            Then le jeton feu est defausse
