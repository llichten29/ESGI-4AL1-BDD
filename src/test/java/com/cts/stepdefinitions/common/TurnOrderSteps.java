package com.cts.stepdefinitions.common;

import com.cts.domain.exception.InvalidSelectionException;
import com.cts.domain.model.player.Player;
import com.cts.domain.model.player.PlayerColor;
import com.cts.domain.service.game.GameService;
import com.cts.stepdefinitions.WorldContext;
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
    public void leJoueurChoisitLeDomino(String playerRef, int tileNumber) {
        PlayerColor color = WorldContext.parsePlayerColor(playerRef);
        Player player = world.game.findPlayerByColor(color);
        assertNotNull(player, "Joueur " + playerRef + " introuvable");
        world.game.selectTileByNumber(player, tileNumber);
    }

    @Then("l'ordre de jeu est {string}, {string}, {string}, {string}")
    public void lOrdreDeJeuEst(String first, String second, String third, String fourth) {
        List<Player> order = world.game.getTurnOrder();
        assertEquals(4, order.size());
        assertEquals(WorldContext.extractPlayerName(first), order.get(0).getName());
        assertEquals(WorldContext.extractPlayerName(second), order.get(1).getName());
        assertEquals(WorldContext.extractPlayerName(third), order.get(2).getName());
        assertEquals(WorldContext.extractPlayerName(fourth), order.get(3).getName());
    }

    @Then("le premier joueur est {string}")
    public void lePremierJoueurEst(String playerRef) {
        List<Player> order = world.game.getTurnOrder();
        assertFalse(order.isEmpty());
        assertEquals(WorldContext.extractPlayerName(playerRef), order.get(0).getName());
    }

    @Then("{int} domino reste sans chef et sera defausse")
    public void dominoResteSansChef(int count) {
        assertEquals(count, world.game.getCurrentDraft().getUnchosenCount(),
            "Nombre de dominos sans chef incorrect");
    }

    @Then("le joueur {string} ne peut pas choisir le domino numero {int}")
    public void leJoueurNePeutPasChoisir(String playerRef, int tileNumber) {
        PlayerColor color = WorldContext.parsePlayerColor(playerRef);
        Player player = world.game.findPlayerByColor(color);
        assertNotNull(player);
        Player finalPlayer = player;
        assertThrows(InvalidSelectionException.class, () -> {
            world.game.selectTileByNumber(finalPlayer, tileNumber);
        });
    }
}
