package com.cts.domain.model;

public class TileCell {
    private final Terrain terrain;
    private final int fireCount;
    private final Resource resource;

    public TileCell(Terrain terrain, int fireCount) {
        this(terrain, fireCount, null);
    }

    public TileCell(Terrain terrain, int fireCount, Resource resource) {
        this.terrain = terrain;
        this.fireCount = fireCount;
        this.resource = resource;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public int getFireCount() {
        return fireCount;
    }

    public Resource getResource() {
        return resource;
    }
}
