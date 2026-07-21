package com.cts.domain.model.board;

import com.cts.domain.model.tile.Terrain;

public record Region(Terrain terrain, int cellCount, int fireCount) {
    public int score() {
        return cellCount * fireCount;
    }
}
