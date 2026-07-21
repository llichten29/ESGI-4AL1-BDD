Feature: Ressources en Mode Totem
    En tant que joueur
    Je veux collecter des ressources sur mes dominos
    Afin de gagner des tuiles Totem et des points

    Background:
        Given un joueur "Alice" avec la tuile depart en position (2,2)

    # --- Attribution des ressources selon le terrain ---

    Scenario: Steppe recoit une ressource Mammouth
        Given une case steppe en (2,1) sans icone feu
        When la ressource est placee sur la case (2,1)
        Then la case (2,1) contient une ressource Mammouth

    Scenario: Lac recoit une ressource Poisson
        Given une case lac en (2,1) sans icone feu
        When la ressource est placee sur la case (2,1)
        Then la case (2,1) contient une ressource Poisson

    Scenario: Jungle recoit une ressource Champignon
        Given une case jungle en (2,1) sans icone feu
        When la ressource est placee sur la case (2,1)
        Then la case (2,1) contient une ressource Champignon

    Scenario: Carriere recoit une ressource Silex
        Given une case carriere en (2,1) sans icone feu
        When la ressource est placee sur la case (2,1)
        Then la case (2,1) contient une ressource Silex

    Scenario: Desert ne recoit aucune ressource
        Given une case desert en (2,1) sans icone feu
        When la ressource est placee sur la case (2,1)
        Then la case (2,1) ne contient aucune ressource

    Scenario: Volcan ne recoit aucune ressource
        Given une case volcan en (2,1) sans icone feu
        When la ressource est placee sur la case (2,1)
        Then la case (2,1) ne contient aucune ressource

    Scenario: Case avec icone feu ne recoit pas de ressource
        Given une case steppe en (2,1) avec icone feu 1
        When la ressource est placee sur la case (2,1)
        Then la case (2,1) ne contient aucune ressource

    # --- Collecte des ressources par le joueur ---

    Scenario: Joueur collecte une ressource en posant un domino
        Given le joueur a deja pose une steppe en (1,2)
        And le joueur recoit un domino steppe-lac avec une ressource Mammouth
        When le joueur pose le domino en (2,1) et (2,0)
        Then Alice possede bien 1 ressource Mammouth

    Scenario: Joueur collecte deux ressources sur un meme domino
        Given le joueur a deja pose une steppe en (1,2)
        And le joueur recoit un domino steppe-steppe avec 2 ressources Mammouth
        When le joueur pose le domino en (3,2) et (3,1)
        Then Alice possede bien 2 ressources Mammouth

    # --- Destruction par le feu ---

    Scenario: Feu detruit la ressource sur la case ciblee
        Given une case steppe en (2,1) contenant une ressource Mammouth
        And un volcan en (1,2) avec feu 1
        When le joueur projette le jeton feu de valeur 1 sur la case (2,1)
        Then la case (2,1) ne contient aucune ressource
        And Alice possede bien 0 ressource Mammouth

    Scenario: Projeter le feu sur une case sans ressource ne change rien
        Given une case steppe en (2,1) sans icone feu
        And un volcan en (1,2) avec feu 1
        When le joueur projette le jeton feu de valeur 1 sur la case (2,1)
        Then la case (2,1) ne contient aucune ressource
