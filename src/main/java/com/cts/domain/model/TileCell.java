package com.cts.domain.model;

public class TileCell {
    private final Terrain terrain;
    private final int fireCount;

    public TileCell(Terrain terrain, int fireCount) {
        this.terrain = terrain;
        this.fireCount = fireCount;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public int getFireCount() {
        return fireCount;
    }
}
