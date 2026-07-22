Feature: Attribution des tuiles Totem
    En tant que joueur
    Je veux recuperer les tuiles Totem correspondant a mes ressources
    Afin de marquer des points bonus

    Background:
        Given une partie en mode Totem avec 4 joueurs et seed 42

    # --- Majorite simple ---

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

    # --- Egalite ---

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

    # --- Changement de proprietaire ---

    Scenario: Un joueur depasse le leader et recupere le totem
        Given "Alice (rose)" possede 2 ressources Mammouth
        And le totem Mammouth appartient a "Alice (rose)"
        And "Bastien (noir)" possede 3 ressources Mammouth
        When les totems sont reevalues
        Then le totem Mammouth revient a "Bastien (noir)"

    # --- Aucune ressource ---

    Scenario: Aucune ressource d un type le totem reste libre
        Given aucun joueur ne possede de ressource Silex
        When les totems sont reevalues
        Then le totem Silex n est attribue a personne
