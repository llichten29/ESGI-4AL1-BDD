package com.cts.domain.service.scoring;

import com.cts.domain.model.board.Kingdom;
import com.cts.domain.model.board.Region;
import com.cts.domain.model.common.Position;
import com.cts.domain.model.scoring.ScoreResult;
import com.cts.domain.model.tile.Terrain;
import com.cts.domain.model.tile.TileCell;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DecouverteScoringService {

    public ScoreResult calculate(Kingdom kingdom) {
        List<Region> regions = findRegions(kingdom);
        int totalScore = 0;
        int largestRegionSize = 0;
        int totalFireCount = 0;
        for (Region r : regions) {
            totalScore += r.score();
            if (r.cellCount() > largestRegionSize) {
                largestRegionSize = r.cellCount();
            }
            totalFireCount += r.fireCount();
        }
        return new ScoreResult(totalScore, largestRegionSize, totalFireCount);
    }

    public List<Region> findRegions(Kingdom kingdom) {
        Set<Position> visited = new HashSet<>();
        List<Region> regions = new ArrayList<>();

        for (Position pos : kingdom.getOccupiedPositions()) {
            if (!visited.contains(pos)) {
                TileCell cell = kingdom.getCell(pos);
                if (cell.getTerrain() != Terrain.VOLCAN && cell.getTerrain() != Terrain.CHATEAU) {
                    Region region = flood(kingdom, pos, cell.getTerrain(), visited);
                    regions.add(region);
                } else {
                    visited.add(pos);
                }
            }
        }
        return regions;
    }

    private Region flood(Kingdom kingdom, Position start, Terrain terrain, Set<Position> visited) {
        List<Position> stack = new ArrayList<>();
        stack.add(start);
        visited.add(start);

        int cellCount = 0;
        int fireCount = 0;

        while (!stack.isEmpty()) {
            Position pos = stack.remove(stack.size() - 1);
            TileCell cell = kingdom.getCell(pos);
            cellCount++;
            fireCount += cell.getFireCount();
            if (kingdom.hasFireToken(pos)) {
                fireCount += kingdom.getFireToken(pos).getCount();
            }

            for (int[] d : Position.CARDINAL_DIRECTIONS) {
                Position next = new Position(pos.x() + d[0], pos.y() + d[1]);
                if (kingdom.isOccupied(next) && !visited.contains(next)) {
                    TileCell nextCell = kingdom.getCell(next);
                    if (nextCell.getTerrain() == terrain) {
                        visited.add(next);
                        stack.add(next);
                    }
                }
            }
        }
        return new Region(terrain, cellCount, fireCount);
    }

    public ScoreResult calculateWithBonuses(Kingdom kingdom) {
        ScoreResult base = calculate(kingdom);
        int empire = new BonusService().calculateEmpireDuFeu(kingdom);
        int habilis = new BonusService().calculateHomoHabilis(kingdom);
        return new ScoreResult(
            base.totalScore() + empire + habilis,
            base.largestRegionSize(),
            base.totalFireCount()
        );
    }

    public int compare(ScoreResult a, ScoreResult b) {
        if (a.totalScore() != b.totalScore()) {
            return Integer.compare(a.totalScore(), b.totalScore());
        }
        if (a.largestRegionSize() != b.largestRegionSize()) {
            return Integer.compare(a.largestRegionSize(), b.largestRegionSize());
        }
        return Integer.compare(a.totalFireCount(), b.totalFireCount());
    }
}
