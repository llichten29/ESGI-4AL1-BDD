package com.cts.stepdefinitions.totem;

import com.cts.domain.model.common.Position;
import com.cts.domain.model.common.Resource;
import com.cts.domain.model.player.PlayerColor;
import com.cts.domain.model.scoring.ScoreResult;
import com.cts.domain.model.tile.TileCell;
import com.cts.domain.service.scoring.DecouverteScoringService;
import com.cts.domain.service.totem.TotemService;
import com.cts.stepdefinitions.WorldContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class TotemScoringSteps {
    private final WorldContext world;
    private final DecouverteScoringService decouverteService = new DecouverteScoringService();
    private final TotemService totemService = new TotemService();
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
        ScoreResult base = decouverteService.calculate(world.getKingdom());
        int resourcePoints = countResourcesOnCells();
        world.setLastScore(new ScoreResult(
            base.totalScore() + resourcePoints, base.largestRegionSize(), base.totalFireCount()));
    }

    @When("le score de totem est calcule")
    public void leScoreDeTotemEstCalcule() {
        int total = 0;
        for (Map.Entry<Resource, PlayerColor> entry : world.getTotemOwners().entrySet()) {
            if (PlayerColor.ROSE == entry.getValue()) {
                total += totemService.getTotemPoints();
            }
        }
        world.setTotemTileScore(total);
    }

    @When("le joueur calcule son score complet en mode Totem")
    public void leJoueurCalculeSonScoreComplet() {
        ScoreResult base = decouverteService.calculate(world.getKingdom());
        int resourcePoints = countResourcesOnCells();
        int totemPoints = 0;
        for (Map.Entry<Resource, PlayerColor> entry : world.getTotemOwners().entrySet()) {
            if (PlayerColor.ROSE == entry.getValue()) {
                totemPoints += totemService.getTotemPoints();
            }
        }
        world.setLastScore(new ScoreResult(
            base.totalScore() + resourcePoints + totemPoints,
            base.largestRegionSize(),
            base.totalFireCount()));
    }

    @When("on compare les deux scores en mode Totem")
    public void onCompareLesDeuxScoresModeTotem() {
        if (aliceScore == null) aliceScore = world.getLastScore();
        if (bobScore == null) bobScore = world.getOtherScore();

        int aliceTotal = aliceScore.totalScore();
        int bobTotal = bobScore.totalScore();

        if (aliceTotal != bobTotal) {
            world.setWinner(aliceTotal > bobTotal ? PlayerColor.ROSE : PlayerColor.NOIR);
            return;
        }

        int aliceResource = world.getResourceCounts().getOrDefault(PlayerColor.ROSE, 0);
        int bobResource = world.getResourceCounts().getOrDefault(PlayerColor.NOIR, 0);

        if (aliceResource != bobResource) {
            world.setWinner(aliceResource > bobResource ? PlayerColor.ROSE : PlayerColor.NOIR);
            return;
        }

        boolean aliceHasTotem = world.getTotemOwners().values().stream().anyMatch(PlayerColor.ROSE::equals);
        boolean bobHasTotem = world.getTotemOwners().values().stream().anyMatch(PlayerColor.NOIR::equals);

        if (aliceHasTotem && !bobHasTotem) {
            world.setWinner(PlayerColor.ROSE);
        } else if (bobHasTotem && !aliceHasTotem) {
            world.setWinner(PlayerColor.NOIR);
        } else {
            world.setWinner(null);
        }
    }

    @Then("le score de totem est de {int}")
    public void leScoreDeTotemEstDe(int expected) {
        assertEquals(expected, world.getTotemTileScore());
    }

    @Then("{string} est classee devant {string}")
    public void joueurEstClasseeDevant(String first, String second) {
        assertEquals(WorldContext.parsePlayerColor(first), world.getWinner());
    }

    private int countResourcesOnCells() {
        int count = 0;
        for (Position pos : world.getKingdom().getOccupiedPositions()) {
            TileCell cell = world.getKingdom().getCell(pos);
            if (cell.getResource() != null) {
                count++;
            }
        }
        return count;
    }
}
