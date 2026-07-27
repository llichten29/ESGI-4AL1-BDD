Feature: Attribution des tuiles Totem
    En tant qu'arbitre de la partie entre Alice, Bastien, Camille et David
    Je veux attribuer chaque tuile Totem au joueur majoritaire sur la ressource
    Afin de recompenser la domination de chaque type de ressource

    # Affinites des personas (voir docs/PERSONAS.md) : Alice=Mammouth, Bastien=Poisson,
    # Camille=Champignon, David=Silex.

    Background:
        Given une partie en mode Totem avec 4 joueurs et seed 42

    Rule: La majorite stricte sur un type de ressource donne la tuile Totem

        Scenario: Joueur majoritaire recupere le totem
            Given "Alice (rose)" possede 3 ressources Mammouth
            And "Bastien (noir)" possede 1 ressource Mammouth
            When les totems sont reevalues
            Then le totem Mammouth revient a "Alice (rose)"

        Scenario: Quatre totems attribues a quatre joueurs differents
            Given "Alice (rose)" possede 3 ressources Mammouth
            And "Bastien (noir)" possede 2 ressources Poisson
            And "Camille (vert)" possede 1 ressource Champignon
            And "David (bleu)" possede 4 ressources Silex
            When les totems sont reevalues
            Then le totem Mammouth revient a "Alice (rose)"
            And le totem Poisson revient a "Bastien (noir)"
            And le totem Champignon revient a "Camille (vert)"
            And le totem Silex revient a "David (bleu)"

        Scenario: Un joueur cumule plusieurs totems
            Given "Alice (rose)" possede 3 ressources Mammouth
            And "Alice (rose)" possede 2 ressources Poisson
            And "Bastien (noir)" possede 1 ressource Champignon
            When les totems sont reevalues
            Then le totem Mammouth revient a "Alice (rose)"
            And le totem Poisson revient a "Alice (rose)"

    Rule: Le totem change de main quand la majorite change

        Scenario: Un joueur depasse le leader et recupere le totem
            Given "Alice (rose)" possede 2 ressources Mammouth
            And le totem Mammouth appartient a "Alice (rose)"
            And "Bastien (noir)" possede 3 ressources Mammouth
            When les totems sont reevalues
            Then le totem Mammouth revient a "Bastien (noir)"

    Rule: En cas d'egalite, le totem reste au proprietaire actuel ou demeure libre

        Scenario: Egalite de majorite avec un proprietaire actuel
            Given "Alice (rose)" possede 2 ressources Mammouth
            And "Bastien (noir)" possede 2 ressources Mammouth
            And le totem Mammouth appartient a "Alice (rose)"
            When les totems sont reevalues
            Then le totem Mammouth reste chez "Alice (rose)"

        Scenario: Egalite de majorite sans proprietaire
            Given "Alice (rose)" possede 2 ressources Mammouth
            And "Bastien (noir)" possede 2 ressources Mammouth
            And "Camille (vert)" possede 1 ressource Mammouth
            When les totems sont reevalues
            Then le totem Mammouth n est attribue a personne

        Scenario: Aucune ressource d un type le totem reste libre
            Given aucun joueur ne possede de ressource Silex
            When les totems sont reevalues
            Then le totem Silex n est attribue a personne
