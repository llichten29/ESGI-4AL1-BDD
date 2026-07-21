package com.cts.stepdefinitions.totem;

import com.cts.domain.model.Kingdom;
import com.cts.domain.model.Position;
import com.cts.domain.model.Region;
import com.cts.domain.model.Resource;
import com.cts.domain.model.ScoreResult;
import com.cts.domain.model.TileCell;
import com.cts.domain.service.DecouverteScoringService;
import com.cts.domain.service.TotemService;
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
    public void leJoueurAScoreAvecRessources(String player, int score, int resourceCount) {
        world.resourceCounts.put(player, resourceCount);
        world.lastScore = new ScoreResult(score, 0, 0);
        if (player.equals("Alice")) {
            aliceScore = new ScoreResult(score, 0, 0);
        } else {
            bobScore = new ScoreResult(score, 0, 0);
        }
    }

    @When("le joueur calcule son score en mode Totem")
    public void leJoueurCalculeSonScoreModeTotem() {
        ScoreResult base = decouverteService.calculate(world.kingdom);
        int resourcePoints = countResourcesOnCells();
        world.lastScore = new ScoreResult(
            base.totalScore() + resourcePoints, base.largestRegionSize(), base.totalFireCount());
    }

    @When("le score de totem est calcule")
    public void leScoreDeTotemEstCalcule() {
        int total = 0;
        for (Map.Entry<Resource, String> entry : world.totemOwners.entrySet()) {
            if ("Alice".equals(entry.getValue())) {
                total += totemService.getTotemPoints();
            }
        }
        world.totemTileScore = total;
    }

    @When("le joueur calcule son score complet en mode Totem")
    public void leJoueurCalculeSonScoreComplet() {
        ScoreResult base = decouverteService.calculate(world.kingdom);
        int resourcePoints = countResourcesOnCells();
        int totemPoints = 0;
        for (Map.Entry<Resource, String> entry : world.totemOwners.entrySet()) {
            if ("Alice".equals(entry.getValue())) {
                totemPoints += totemService.getTotemPoints();
            }
        }
        world.lastScore = new ScoreResult(
            base.totalScore() + resourcePoints + totemPoints,
            base.largestRegionSize(),
            base.totalFireCount());
    }

    @When("on compare les deux scores en mode Totem")
    public void onCompareLesDeuxScoresModeTotem() {
        if (aliceScore == null) aliceScore = world.lastScore;
        if (bobScore == null) bobScore = world.otherScore;

        int aliceTotal = aliceScore.totalScore();
        int bobTotal = bobScore.totalScore();

        if (aliceTotal != bobTotal) {
            world.winner = aliceTotal > bobTotal ? "Alice" : "Bastien";
            return;
        }

        int aliceResources = world.resourceCounts.getOrDefault("Alice", 0);
        int bobResources = world.resourceCounts.getOrDefault("Bastien", 0);

        if (aliceResources != bobResources) {
            world.winner = aliceResources > bobResources ? "Alice" : "Bastien";
            return;
        }

        boolean aliceHasTotem = world.totemOwners.values().stream().anyMatch("Alice"::equals);
        boolean bobHasTotem = world.totemOwners.values().stream().anyMatch("Bastien"::equals);

        if (aliceHasTotem && !bobHasTotem) {
            world.winner = "Alice";
        } else if (bobHasTotem && !aliceHasTotem) {
            world.winner = "Bastien";
        } else {
            world.winner = "egalite";
        }
    }

    @Then("le score de totem est de {int}")
    public void leScoreDeTotemEstDe(int expected) {
        assertEquals(expected, world.totemTileScore);
    }

    @Then("{word} est classee devant {word}")
    public void joueurEstClasseeDevant(String first, String second) {
        assertEquals(first, world.winner);
    }

    private int countResourcesOnCells() {
        int count = 0;
        for (Position pos : world.kingdom.getOccupiedPositions()) {
            TileCell cell = world.kingdom.getCell(pos);
            if (cell.getResource() != null) {
                count++;
            }
        }
        return count;
    }
}
