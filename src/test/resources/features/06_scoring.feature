Feature: Scoring des territoires
    En tant que joueur
    Je veux calculer mon score en fin de partie
    Afin de determiner le vainqueur

    Background:
        Given un joueur "Alice" avec la tuile depart en position (2,2)

    Scenario: Region avec icones feu imprimees
        Given une case steppe en (2,1) avec icone feu 0
        And une case steppe en (2,0) avec icone feu 2
        When le joueur calcule son score
        Then la region steppe contient 2 cases et 2 feux
        And le score total est de 4

    Scenario: Region sans icone feu ne rapporte rien
        Given une case steppe en (2,1) avec icone feu 0
        And une case steppe en (2,0) avec icone feu 0
        When le joueur calcule son score
        Then le score total est de 0

    Scenario: Region volcan ne rapporte rien
        Given un volcan en (2,3) avec feu 2
        When le joueur calcule son score
        Then le score total est de 0

    Scenario: Jeton feu ajoute des points a la region
        Given une case steppe en (2,1) avec icone feu 0
        And une case steppe en (2,0) avec icone feu 1
        And un jeton feu de valeur 1 place en (2,0)
        When le joueur calcule son score
        Then la region steppe contient 2 cases et 2 feux
        And le score total est de 4

    Scenario: Regions separees du meme terrain comptees individuellement
        Given une case steppe en (2,1) avec icone feu 0
        And une case steppe en (2,0) avec icone feu 1
        Given une case steppe en (1,2) avec icone feu 0
        And une case steppe en (0,2) avec icone feu 1
        When le joueur calcule son score
        Then le score total est de 4

    Scenario: Chateau n affecte pas le scoring
        Given une case steppe en (2,1) avec icone feu 0
        And une case steppe en (2,0) avec icone feu 1
        When le joueur calcule son score
        Then la region steppe contient 2 cases et 1 feux
        And le joueur a un score de 2

    Scenario: Egalite departagee par plus grande region
        When on compare deux scores egaux a 10
        Then le joueur avec la plus grande region gagne

    Scenario: Egalite departagee par plus d icones feu
        When on compare deux scores avec le meme total et la meme region mais des feux differents
        Then le joueur avec le plus de feux gagne

    Scenario: Egalite persistante victoire partagee
        Then les joueurs sont ex aequo
