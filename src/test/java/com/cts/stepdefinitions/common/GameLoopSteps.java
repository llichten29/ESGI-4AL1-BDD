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
        assertNotNull(world.getGame());
        List<Tile> available = world.getGame().getCurrentDraft().getUnchosenTiles();
        int idx = 0;
        for (Player p : world.getGame().getPlayers()) {
            if (!p.hasChosen() && idx < available.size()) {
                Tile tile = available.get(idx);
                world.getGame().selectTileByNumber(p, tile.getNumber());
                idx++;
            }
        }
    }

    @When("le tour suivant commence")
    public void leTourSuivantCommence() {
        assertNotNull(world.getGame());
        world.getGame().advanceRound();
    }

    @When("on joue jusqu'a la derniere ligne")
    public void onJoueJusquALaDerniereLigne() {
        assertNotNull(world.getGame());
        while (world.getGame().canAdvance()) {
            List<Tile> available = world.getGame().getCurrentDraft().getUnchosenTiles();
            int idx = 0;
            for (Player p : world.getGame().getPlayers()) {
                if (idx < available.size()) {
                    world.getGame().selectTileByNumber(p, available.get(idx).getNumber());
                    idx++;
                }
            }
            world.getGame().advanceRound();
        }
    }

    @Then("une nouvelle draft de {int} dominos est disponible")
    public void uneNouvelleDraftDeDominosEstDisponible(int expectedSize) {
        assertNotNull(world.getGame());
        assertEquals(expectedSize, world.getGame().getCurrentDraft().size());
    }

    @Then("le domino non choisi a ete defausse")
    public void leDominoNonChoisiAEteDefausse() {
        assertNotNull(world.getGame());
        assertNotNull(world.getGame().getLastDiscardedTile());
    }

    @Then("le numero du tour est {int}")
    public void leNumeroDuTourEst(int expectedTurn) {
        assertNotNull(world.getGame());
        assertEquals(expectedTurn, world.getGame().getTurnNumber());
    }

    @Then("la derniere ligne de dominos est formee")
    public void laDerniereLigneDeDominosEstFormee() {
        assertNotNull(world.getGame());
        assertTrue(world.getGame().isGameOver());
    }

    @Then("aucun nouveau tour ne peut commencer")
    public void aucunNouveauTourNePeutCommencer() {
        assertNotNull(world.getGame());
        assertFalse(world.getGame().canAdvance(),
            "canAdvance() devrait etre faux mais est vrai");
    }
}
