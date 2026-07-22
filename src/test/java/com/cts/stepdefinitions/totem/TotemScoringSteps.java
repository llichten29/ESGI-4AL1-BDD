package com.cts.stepdefinitions.totem;

import com.cts.domain.model.player.PlayerColor;
import com.cts.domain.model.scoring.ScoreResult;
import com.cts.domain.service.scoring.TotemScoringService;
import com.cts.stepdefinitions.WorldContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;

public class TotemScoringSteps {
    private final WorldContext world;
    private final TotemScoringService totemScoringService = new TotemScoringService();
    private ScoreResult aliceScore;
    private ScoreResult bobScore;

    public TotemScoringSteps(WorldContext world) {
        this.world = world;
    }

    @Given("^le joueur \"([^\"]+)\" a un score de (\\d+) avec (\\d+) ressources?$")
    public void leJoueurAScoreAvecRessources(String playerRef, int score, int resourceCount) {
        PlayerColor color = WorldContext.parsePlayerColor(playerRef);
        world.getResourceCounts().put(color, resourceCount);
        world.setLastScore(new ScoreResult(score, 0, 0));
        if (WorldContext.extractPlayerName(playerRef).equals("Alice")) {
            aliceScore = new ScoreResult(score, 0, 0);
        } else {
            bobScore = new ScoreResult(score, 0, 0);
        }
    }

    @When("le joueur calcule son score en mode Totem")
    public void leJoueurCalculeSonScoreModeTotem() {
        world.setLastScore(totemScoringService.calculateScoreWithResources(world.getKingdom()));
    }

    @When("le score de totem est calcule")
    public void leScoreDeTotemEstCalcule() {
        world.setTotemTileScore(totemScoringService.countTotemPoints(world.getTotemOwners(), PlayerColor.ROSE));
    }

    @When("le joueur calcule son score complet en mode Totem")
    public void leJoueurCalculeSonScoreComplet() {
        world.setLastScore(totemScoringService.calculateTotemScore(
            world.getKingdom(), world.getTotemOwners(), PlayerColor.ROSE));
    }

    @When("on compare les deux scores en mode Totem")
    public void onCompareLesDeuxScoresModeTotem() {
        if (aliceScore == null) aliceScore = world.getLastScore();
        if (bobScore == null) bobScore = world.getOtherScore();

        int aliceResources = world.getResourceCounts().getOrDefault(PlayerColor.ROSE, 0);
        int bobResources = world.getResourceCounts().getOrDefault(PlayerColor.NOIR, 0);

        world.setWinner(totemScoringService.compareScores(
            aliceScore, bobScore, aliceResources, bobResources, world.getTotemOwners()));
    }

    @Then("le score de totem est de {int}")
    public void leScoreDeTotemEstDe(int expected) {
        assertEquals(expected, world.getTotemTileScore());
    }

    @Then("{string} est classee devant {string}")
    public void joueurEstClasseeDevant(String first, String second) {
        assertEquals(WorldContext.parsePlayerColor(first), world.getWinner());
    }
}
