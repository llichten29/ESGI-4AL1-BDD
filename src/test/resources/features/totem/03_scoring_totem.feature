Feature: Scoring en Mode Totem
    En tant qu'arbitre de la partie entre Alice, Bastien, Camille et David
    Je veux calculer le score total de chaque joueur (ressources et totems compris)
    Afin de departager la troupe et designer le vainqueur

    Background:
        Given un joueur "Alice (rose)" avec la tuile depart en position (2,2)

    Rule: Chaque ressource restante sur le territoire rapporte 1 point

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

    Rule: Chaque tuile Totem possedee rapporte ses points bonus

        Scenario: Totem Mammouth rapporte des points bonus
            Given "Alice (rose)" possede 3 ressources Mammouth
            And "Bastien (noir)" possede 1 ressource Mammouth
            And le totem Mammouth appartient a "Alice (rose)"
            When les totems sont reevalues
            And le score de totem est calcule
            Then le score de totem est de 5

        Scenario: Score total combine Decouverte ressources et totems
            Given une case steppe en (2,1) sans icone feu contenant une ressource Mammouth
            And une case steppe en (2,0) avec icone feu 1
            And "Alice (rose)" possede 3 ressources Mammouth
            And "Bastien (noir)" possede 1 ressource Mammouth
            And le totem Mammouth appartient a "Alice (rose)"
            When le joueur calcule son score complet en mode Totem
            Then le score total est de 8

    Rule: Les egalites sont departagees par les ressources puis par les totems

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
