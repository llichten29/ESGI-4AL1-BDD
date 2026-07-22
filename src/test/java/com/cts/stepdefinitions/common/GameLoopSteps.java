package com.cts.stepdefinitions.common;

import com.cts.stepdefinitions.WorldContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;

public class GameLoopSteps {

    private final WorldContext world;

    public GameLoopSteps(WorldContext world) {
        this.world = world;
    }

    @When("tous les joueurs choisissent un domino")
    public void tousLesJoueursChoisissentUnDomino() {
        assertNotNull(world.getGame());
        world.getGame().autoSelectTiles();
    }

    @When("le tour suivant commence")
    public void leTourSuivantCommence() {
        assertNotNull(world.getGame());
        world.getGame().advanceRound();
    }

    @When("on joue jusqu'a la derniere ligne")
    public void onJoueJusquALaDerniereLigne() {
        assertNotNull(world.getGame());
        world.getGame().playUntilEnd();
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
