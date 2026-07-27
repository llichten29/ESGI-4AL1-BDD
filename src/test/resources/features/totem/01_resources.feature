Feature: Ressources en Mode Totem
    En tant qu'Alice, l'eleveuse de mammouths
    Je veux collecter des ressources sur mes dominos
    Afin de gagner des tuiles Totem et des points

    Background:
        Given un joueur "Alice (rose)" avec la tuile depart en position (2,2)

    Rule: Chaque terrain exploitable sans feu recoit sa ressource

        Scenario Outline: Une case <terrain> avec <feu> icone(s) feu recoit la ressource <ressource>
            Given une case <terrain> en (2,1) avec icone feu <feu>
            When la ressource est placee sur la case (2,1)
            Then la case (2,1) contient la ressource "<ressource>"

            Examples: Terrains exploitables sans feu
                | terrain  | feu | ressource  |
                | steppe   | 0   | Mammouth   |
                | lac      | 0   | Poisson    |
                | jungle   | 0   | Champignon |
                | carriere | 0   | Silex      |

            Examples: Terrains sans ressource ou cases occupees par le feu
                | terrain  | feu | ressource |
                | desert   | 0   | aucune    |
                | volcan   | 0   | aucune    |
                | steppe   | 1   | aucune    |

    Rule: Le joueur collecte les ressources des dominos qu'il pose

        Scenario: Joueur collecte une ressource en posant un domino
            Given le joueur a deja pose une steppe en (1,2)
            And le joueur recoit un domino steppe-lac avec une ressource Mammouth
            When le joueur pose le domino en (2,1) et (2,0)
            Then "Alice (rose)" possede bien 1 ressource Mammouth

        Scenario: Joueur collecte deux ressources sur un meme domino
            Given le joueur a deja pose une steppe en (1,2)
            And le joueur recoit un domino steppe-steppe avec 2 ressources Mammouth
            When le joueur pose le domino en (3,2) et (3,1)
            Then "Alice (rose)" possede bien 2 ressources Mammouth

    Rule: Une projection de feu detruit la ressource de la case ciblee

        Scenario: Feu detruit la ressource sur la case ciblee
            Given une case steppe en (2,1) contenant une ressource Mammouth
            And un volcan en (1,2) avec feu 1
            When le joueur projette le jeton feu de valeur 1 sur la case (2,1)
            Then la case (2,1) ne contient aucune ressource
            And "Alice (rose)" possede bien 0 ressource Mammouth

        Scenario: Projeter le feu sur une case sans ressource ne change rien
            Given une case steppe en (2,1) sans icone feu
            And un volcan en (1,2) avec feu 1
            When le joueur projette le jeton feu de valeur 1 sur la case (2,1)
            Then la case (2,1) ne contient aucune ressource
