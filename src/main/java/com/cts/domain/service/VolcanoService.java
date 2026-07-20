package com.cts.domain.service;

import com.cts.domain.model.FireToken;
import com.cts.domain.model.Terrain;
import com.cts.domain.model.Tile;
import java.util.ArrayList;
import java.util.List;

public class VolcanoService {

    public List<FireToken> collectFireTokens(Tile tile) {
        List<FireToken> tokens = new ArrayList<>();
        addIfVolcano(tile.getCellA(), tokens);
        addIfVolcano(tile.getCellB(), tokens);
        return tokens;
    }

    private void addIfVolcano(com.cts.domain.model.TileCell cell, List<FireToken> tokens) {
        if (cell.getTerrain() == Terrain.VOLCAN && cell.getFireCount() > 0) {
            tokens.add(new FireToken(cell.getFireCount()));
        }
    }
}
