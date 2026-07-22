package com.cts.stepdefinitions.decouverte;

import com.cts.domain.model.tile.Terrain;
import com.cts.domain.service.scoring.DecouverteScoringService;
import com.cts.stepdefinitions.WorldContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class BonusSteps {

    private final WorldContext world;
    private final DecouverteScoringService scoringService = new DecouverteScoringService();

    public BonusSteps(WorldContext world) {
        this.world = world;
    }

    @Given("un territoire de {int} cases")
    public void unTerritoireDeCases(int count) {
        world.kingdom.fillTerritory(count, Terrain.STEPPE);
    }

    @When("les bonus optionnels sont calcules")
    public void lesBonusOptionnelsSontCalcules() {
        world.lastScore = scoringService.calculateWithBonuses(world.kingdom);
    }
}
