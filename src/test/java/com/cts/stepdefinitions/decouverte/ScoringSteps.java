package com.cts.stepdefinitions.decouverte;

import com.cts.domain.model.board.Region;
import com.cts.domain.model.scoring.ScoreResult;
import com.cts.domain.service.scoring.DecouverteScoringService;
import com.cts.stepdefinitions.WorldContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ScoringSteps {

    private final WorldContext world;
    private List<Region> lastRegions;
    private ScoreResult aliceScore;
    private ScoreResult bobScore;

    public ScoringSteps(WorldContext world) {
        this.world = world;
    }

    @When("le joueur calcule son score")
    public void leJoueurCalculeSonScore() {
        DecouverteScoringService service = new DecouverteScoringService();
        world.lastScore = service.calculate(world.kingdom);
        lastRegions = service.findRegions(world.kingdom);
    }

    @Then("le score total est de {int}")
    public void leScoreTotalEstDe(int expected) {
        assertNotNull(world.lastScore);
        assertEquals(expected, world.lastScore.totalScore());
    }

    @Then("la region {word} contient {int} cases et {int} feux")
    public void laRegionContientCasesEtFeux(String terrainStr, int expectedCells, int expectedFire) {
        assertNotNull(lastRegions);
        for (Region r : lastRegions) {
            if (r.terrain().name().equalsIgnoreCase(terrainStr)) {
                assertEquals(expectedCells, r.cellCount());
                assertEquals(expectedFire, r.fireCount());
                return;
            }
        }
        fail("Aucune region trouvee pour le terrain: " + terrainStr);
    }

    @Then("la region {word} rapporte 0 point")
    public void laRegionRapporteZeroPoint(String terrainStr) {
        assertNotNull(lastRegions);
        for (Region r : lastRegions) {
            if (r.terrain().name().equalsIgnoreCase(terrainStr)) {
                assertEquals(0, r.score());
                return;
            }
        }
        fail("Aucune region trouvee pour le terrain: " + terrainStr);
    }

    @Then("le joueur a un score de {int}")
    public void leJoueurAUnScoreDe(int expected) {
        leScoreTotalEstDe(expected);
    }

    @Then("la plus grande region fait {int} cases")
    public void laPlusGrandeRegionFaitCases(int expected) {
        assertNotNull(world.lastScore);
        assertEquals(expected, world.lastScore.largestRegionSize());
    }

    @Then("le nombre total d'icones feu est de {int}")
    public void leNombreTotalDIconesFeuEstDe(int expected) {
        assertNotNull(world.lastScore);
        assertEquals(expected, world.lastScore.totalFireCount());
    }

    @When("on compare deux scores egaux a {int}")
    public void onCompareDeuxScoresEgaux(int totalScore) {
        aliceScore = new ScoreResult(totalScore, 5, 2);
        bobScore = new ScoreResult(totalScore, 3, 2);
    }

    @When("on compare deux scores avec le meme total et la meme region mais des feux differents")
    public void onCompareDeuxScoresMemeTotalMemeRegionFeuxDifferents() {
        aliceScore = new ScoreResult(10, 5, 3);
        bobScore = new ScoreResult(10, 5, 1);
    }

    @Then("le joueur avec la plus grande region gagne")
    public void leJoueurAvecLaPlusGrandeRegionGagne() {
        int result = new DecouverteScoringService().compare(aliceScore, bobScore);
        assertTrue(result > 0, "Alice (plus grande region) devrait gagner");
    }

    @Then("le joueur avec le plus de feux gagne")
    public void leJoueurAvecLePlusDeFeuxGagne() {
        int result = new DecouverteScoringService().compare(aliceScore, bobScore);
        assertTrue(result > 0, "Alice (plus de feux) devrait gagner");
    }

    @Then("les joueurs sont ex aequo")
    public void lesJoueursSontExAequo() {
        aliceScore = new ScoreResult(10, 5, 3);
        bobScore = new ScoreResult(10, 5, 3);
        int result = new DecouverteScoringService().compare(aliceScore, bobScore);
        assertEquals(0, result);
    }
}
