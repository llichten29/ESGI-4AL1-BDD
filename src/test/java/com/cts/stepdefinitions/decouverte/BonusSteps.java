package com.cts.stepdefinitions.decouverte;

import com.cts.domain.model.Kingdom;
import com.cts.domain.model.Position;
import com.cts.domain.model.ScoreResult;
import com.cts.domain.model.Terrain;
import com.cts.domain.model.TileCell;
import com.cts.domain.service.BonusService;
import com.cts.stepdefinitions.WorldContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BonusSteps {

    private final WorldContext world;
    private final BonusService bonusService = new BonusService();

    public BonusSteps(WorldContext world) {
        this.world = world;
    }

    @Given("un territoire de {int} cases")
    public void unTerritoireDeCases(int count) {
        int placed = 1;
        for (int x = 0; x < Kingdom.SIZE && placed < count; x++) {
            for (int y = 0; y < Kingdom.SIZE && placed < count; y++) {
                Position pos = new Position(x, y);
                if (!world.kingdom.isOccupied(pos)) {
                    world.kingdom.placeCell(pos, new TileCell(Terrain.STEPPE, 0));
                    placed++;
                }
            }
        }
    }

    @When("les bonus optionnels sont calcules")
    public void lesBonusOptionnelsSontCalcules() {
        int empire = bonusService.calculateEmpireDuFeu(world.kingdom);
        int habilis = bonusService.calculateHomoHabilis(world.kingdom);
        world.lastScore = new ScoreResult(
            world.lastScore.totalScore() + empire + habilis,
            world.lastScore.largestRegionSize(),
            world.lastScore.totalFireCount()
        );
    }
}
