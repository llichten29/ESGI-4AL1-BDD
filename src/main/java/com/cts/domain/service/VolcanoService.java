package com.cts.domain.service;

import com.cts.domain.model.FireToken;
import com.cts.domain.model.Kingdom;
import com.cts.domain.model.Position;
import com.cts.domain.model.Terrain;
import com.cts.domain.model.Tile;
import com.cts.domain.model.TileCell;
import java.util.ArrayList;
import java.util.List;

public class VolcanoService {

    public List<FireToken> collectFireTokens(Tile tile) {
        List<FireToken> tokens = new ArrayList<>();
        addIfVolcano(tile.getCellA(), tokens);
        addIfVolcano(tile.getCellB(), tokens);
        return tokens;
    }

    private void addIfVolcano(TileCell cell, List<FireToken> tokens) {
        if (cell.getTerrain() == Terrain.VOLCAN && cell.getFireCount() > 0) {
            tokens.add(new FireToken(cell.getFireCount()));
        }
    }

    public boolean canPlace(Kingdom kingdom, Position target, Position volcanoPos, FireToken token) {
        if (!kingdom.isOccupied(target)) return false;
        if (target.equals(volcanoPos)) return false;
        TileCell cell = kingdom.getCell(target);
        if (cell.getTerrain() == Terrain.VOLCAN) return false;
        if (cell.getFireCount() > 0) return false;
        if (kingdom.hasFireToken(target)) return false;
        int distance = Math.max(
            Math.abs(target.x() - volcanoPos.x()),
            Math.abs(target.y() - volcanoPos.y())
        );
        return distance <= token.getRange();
    }

    public List<Position> findValidPlacements(Kingdom kingdom, Position volcanoPos, FireToken token) {
        List<Position> valid = new ArrayList<>();
        for (Position pos : kingdom.getOccupiedPositions()) {
            if (canPlace(kingdom, pos, volcanoPos, token)) {
                valid.add(pos);
            }
        }
        return valid;
    }

    public void placeFireToken(Kingdom kingdom, Position target, Position volcanoPos, FireToken token) {
        if (!canPlace(kingdom, target, volcanoPos, token)) {
            throw new IllegalArgumentException("placement de jeton feu invalide");
        }
        kingdom.placeFireToken(target, token);
    }
}
