Feature: Scoring en Mode Totem
    En tant que joueur
    Je veux calculer mon score total incluant ressources et totems
    Afin de determiner le vainqueur

    Background:
        Given un joueur "Alice (rose)" avec la tuile depart en position (2,2)

    # --- Score des ressources ---

    Scenario: Chaque ressource rapporte 1 point
        Given une case steppe en (2,1) sans icone feu contenant une ressource Mammouth
        And une case steppe en (2,0) sans icone feu contenant une ressource Mammouth
        When le joueur calcule son score en mode Totem
        Then le score total est de 2

    Scenario: Score combine Decouverte et ressources
        Given une case steppe en (2,1) sans icone feu contenant une ressource Mammouth
        And une case steppe en (2,0) avec icone feu 2
        When le joueur calcule son score en mode Totem
        Then le score total est de 5

    # --- Score des totems ---

    Scenario: Totem Mammouth rapporte des points bonus
        Given "Alice (rose)" possede 3 ressources Mammouth
        And "Bastien (noir)" possede 1 ressource Mammouth
        And le totem Mammouth appartient a "Alice (rose)"
        When les totems sont reevalues
        And le score de totem est calcule
        Then le score de totem est de 5

    # --- Score complet ---

    Scenario: Score total combine Decouverte ressources et totems
        Given une case steppe en (2,1) sans icone feu contenant une ressource Mammouth
        And une case steppe en (2,0) avec icone feu 1
        And "Alice (rose)" possede 3 ressources Mammouth
        And "Bastien (noir)" possede 1 ressource Mammouth
        And le totem Mammouth appartient a "Alice (rose)"
        When le joueur calcule son score complet en mode Totem
        Then le score total est de 8

    Scenario: Egalite de score departagee par les ressources
        Given le joueur "Alice (rose)" a un score de 10 avec 5 ressources
        And le joueur "Bastien (noir)" a un score de 10 avec 3 ressources
        When on compare les deux scores en mode Totem
        Then "Alice (rose)" est classee devant "Bastien (noir)"

    Scenario: Comparaison avec totem comme critere de departage
        Given le joueur "Alice (rose)" a un score de 10 avec 5 ressources
        And le joueur "Bastien (noir)" a un score de 10 avec 5 ressources
        And "Alice (rose)" possede le totem Mammouth
        When on compare les deux scores en mode Totem
        Then "Alice (rose)" est classee devant "Bastien (noir)"
