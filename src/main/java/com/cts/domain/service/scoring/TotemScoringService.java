package com.cts.domain.service.scoring;

import com.cts.domain.model.board.Kingdom;
import com.cts.domain.model.common.Position;
import com.cts.domain.model.common.Resource;
import com.cts.domain.model.player.PlayerColor;
import com.cts.domain.model.scoring.ScoreResult;
import com.cts.domain.model.tile.TileCell;
import com.cts.domain.service.totem.TotemService;
import java.util.Map;

public class TotemScoringService {

    private final DecouverteScoringService decouverteService;
    private final TotemService totemService;

    public TotemScoringService() {
        this.decouverteService = new DecouverteScoringService();
        this.totemService = new TotemService();
    }

    public ScoreResult calculateBaseScore(Kingdom kingdom) {
        return decouverteService.calculate(kingdom);
    }

    public int countResourcePoints(Kingdom kingdom) {
        int count = 0;
        for (Position pos : kingdom.getOccupiedPositions()) {
            TileCell cell = kingdom.getCell(pos);
            if (cell.getResource() != null) {
                count++;
            }
        }
        return count;
    }

    public int countTotemPoints(Map<Resource, PlayerColor> totemOwners, PlayerColor player) {
        int total = 0;
        for (Map.Entry<Resource, PlayerColor> entry : totemOwners.entrySet()) {
            if (player == entry.getValue()) {
                total += totemService.getTotemPoints();
            }
        }
        return total;
    }

    public ScoreResult calculateTotemScore(Kingdom kingdom, Map<Resource, PlayerColor> totemOwners, PlayerColor player) {
        ScoreResult base = decouverteService.calculate(kingdom);
        int resourcePoints = countResourcePoints(kingdom);
        int totemPoints = countTotemPoints(totemOwners, player);
        return new ScoreResult(
            base.totalScore() + resourcePoints + totemPoints,
            base.largestRegionSize(),
            base.totalFireCount()
        );
    }

    public ScoreResult calculateScoreWithResources(Kingdom kingdom) {
        ScoreResult base = decouverteService.calculate(kingdom);
        int resourcePoints = countResourcePoints(kingdom);
        return new ScoreResult(
            base.totalScore() + resourcePoints, base.largestRegionSize(), base.totalFireCount());
    }

    public PlayerColor compareScores(
            ScoreResult aliceScore, ScoreResult bobScore,
            int aliceResources, int bobResources,
            Map<Resource, PlayerColor> totemOwners) {
        int aliceTotal = aliceScore.totalScore();
        int bobTotal = bobScore.totalScore();

        if (aliceTotal != bobTotal) {
            return aliceTotal > bobTotal ? PlayerColor.ROSE : PlayerColor.NOIR;
        }

        if (aliceResources != bobResources) {
            return aliceResources > bobResources ? PlayerColor.ROSE : PlayerColor.NOIR;
        }

        boolean aliceHasTotem = totemOwners.values().stream().anyMatch(PlayerColor.ROSE::equals);
        boolean bobHasTotem = totemOwners.values().stream().anyMatch(PlayerColor.NOIR::equals);

        if (aliceHasTotem && !bobHasTotem) return PlayerColor.ROSE;
        if (bobHasTotem && !aliceHasTotem) return PlayerColor.NOIR;

        return null;
    }
}
