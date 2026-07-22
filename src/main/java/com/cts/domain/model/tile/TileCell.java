package com.cts.domain.model.tile;

import com.cts.domain.model.common.Resource;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TileCell tileCell = (TileCell) o;
        return fireCount == tileCell.fireCount && terrain == tileCell.terrain && resource == tileCell.resource;
    }

    @Override
    public int hashCode() {
        return Objects.hash(terrain, fireCount, resource);
    }
}
