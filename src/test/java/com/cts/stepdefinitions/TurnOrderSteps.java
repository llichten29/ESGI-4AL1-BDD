package com.cts.stepdefinitions;

import com.cts.domain.exception.InvalidSelectionException;
import com.cts.domain.model.Player;
import com.cts.domain.service.GameService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TurnOrderSteps {

    private final WorldContext world;

    public TurnOrderSteps(WorldContext world) {
        this.world = world;
    }

    @Given("une partie de {int} joueurs avec seed {long}")
    public void unePartieDeJoueurs(int playerCount, long seed) {
        world.game = new GameService(playerCount, seed);
    }

    @When("le joueur {string} choisit le domino numero {int}")
    public void leJoueurChoisitLeDomino(String color, int tileNumber) {
        Player player = world.game.findPlayerByColor(color);
        assertNotNull(player, "Joueur " + color + " introuvable");
        world.game.selectTileByNumber(player, tileNumber);
    }

    @Then("l'ordre de jeu est {string}, {string}, {string}, {string}")
    public void lOrdreDeJeuEst(String first, String second, String third, String fourth) {
        List<Player> order = world.game.getTurnOrder();
        assertEquals(4, order.size());
        assertEquals(first, order.get(0).getColor());
        assertEquals(second, order.get(1).getColor());
        assertEquals(third, order.get(2).getColor());
        assertEquals(fourth, order.get(3).getColor());
    }

    @Then("le premier joueur est {string}")
    public void lePremierJoueurEst(String color) {
        List<Player> order = world.game.getTurnOrder();
        assertFalse(order.isEmpty());
        assertEquals(color, order.get(0).getColor());
    }

    @Then("{int} domino reste sans chef et sera defausse")
    public void dominoResteSansChef(int count) {
        assertEquals(count, world.game.getCurrentDraft().getUnchosenCount(),
            "Nombre de dominos sans chef incorrect");
    }

    @Then("le joueur {string} ne peut pas choisir le domino numero {int}")
    public void leJoueurNePeutPasChoisir(String color, int tileNumber) {
        Player player = world.game.findPlayerByColor(color);
        assertNotNull(player);
        Player finalPlayer = player;
        assertThrows(InvalidSelectionException.class, () -> {
            world.game.selectTileByNumber(finalPlayer, tileNumber);
        });
    }
}
