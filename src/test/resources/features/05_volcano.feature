Feature: Volcans et jetons Feu
    En tant que joueur
    Je veux recevoir des jetons Feu en posant des dominos volcan
    Afin de marquer plus de points

    Background:
        Given un joueur "Alice" avec la tuile depart en position (2,2)

    Scenario: Volcan 1 cratere donne un jeton feu
        Given le joueur a deja pose une steppe en (2,1)
        When le joueur pose un domino lac-volcan en (2,0) et (1,0)
        Then le joueur recoit un jeton feu de valeur 1

    Scenario: Volcan 2 crateres donne un jeton feu
        Given le joueur a deja pose une steppe en (2,1)
        When le joueur pose un domino jungle-volcan en (3,2) et (3,1)
        Then le joueur recoit un jeton feu de valeur 2

    Scenario: Volcan 3 crateres donne un jeton feu
        Given le joueur a deja pose une steppe en (2,1)
        When le joueur pose un domino desert-volcan en (2,3) et (2,4)
        Then le joueur recoit un jeton feu de valeur 3

    Scenario: Domino sans volcan ne donne pas de jeton
        Given le joueur a deja pose une steppe en (2,1)
        When le joueur pose un domino steppe-steppe en (2,0) et (1,0)
        Then le joueur ne recoit aucun jeton feu
