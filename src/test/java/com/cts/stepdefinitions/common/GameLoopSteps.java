package com.cts.stepdefinitions.common;

import com.cts.domain.model.player.Player;
import com.cts.domain.model.tile.Tile;
import com.cts.stepdefinitions.WorldContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GameLoopSteps {

    private final WorldContext world;

    public GameLoopSteps(WorldContext world) {
        this.world = world;
    }

    @When("tous les joueurs choisissent un domino")
    public void tousLesJoueursChoisissentUnDomino() {
        assertNotNull(world.game);
        List<Tile> available = world.game.getCurrentDraft().getUnchosenTiles();
        int idx = 0;
        for (Player p : world.game.getPlayers()) {
            if (!p.hasChosen() && idx < available.size()) {
                Tile tile = available.get(idx);
                world.game.selectTileByNumber(p, tile.getNumber());
                idx++;
            }
        }
    }

    @When("le tour suivant commence")
    public void leTourSuivantCommence() {
        assertNotNull(world.game);
        world.game.advanceRound();
    }

    @When("on joue jusqu'a la derniere ligne")
    public void onJoueJusquALaDerniereLigne() {
        assertNotNull(world.game);
        while (world.game.canAdvance()) {
            List<Tile> available = world.game.getCurrentDraft().getUnchosenTiles();
            int idx = 0;
            for (Player p : world.game.getPlayers()) {
                if (idx < available.size()) {
                    world.game.selectTileByNumber(p, available.get(idx).getNumber());
                    idx++;
                }
            }
            world.game.advanceRound();
        }
    }

    @Then("une nouvelle draft de {int} dominos est disponible")
    public void uneNouvelleDraftDeDominosEstDisponible(int expectedSize) {
        assertNotNull(world.game);
        assertEquals(expectedSize, world.game.getCurrentDraft().size());
    }

    @Then("le domino non choisi a ete defausse")
    public void leDominoNonChoisiAEteDefausse() {
        assertNotNull(world.game);
        assertNotNull(world.game.getLastDiscardedTile());
    }

    @Then("le numero du tour est {int}")
    public void leNumeroDuTourEst(int expectedTurn) {
        assertNotNull(world.game);
        assertEquals(expectedTurn, world.game.getTurnNumber());
    }

    @Then("la derniere ligne de dominos est formee")
    public void laDerniereLigneDeDominosEstFormee() {
        assertNotNull(world.game);
        assertTrue(world.game.isGameOver());
    }

    @Then("aucun nouveau tour ne peut commencer")
    public void aucunNouveauTourNePeutCommencer() {
        assertNotNull(world.game);
        assertFalse(world.game.canAdvance(),
            "canAdvance() devrait etre faux mais est vrai");
    }
}
