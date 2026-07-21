package com.cts.domain.model.tile;

import com.cts.domain.model.common.Resource;

public class Tile {
    private final int number;
    private final TileCell cellA;
    private final TileCell cellB;

    public Tile(int number, TileCell cellA, TileCell cellB) {
        this.number = number;
        this.cellA = cellA;
        this.cellB = cellB;
    }

    public int getNumber() {
        return number;
    }

    public TileCell getCellA() {
        return cellA;
    }

    public TileCell getCellB() {
        return cellB;
    }

    public static class TileCell {
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
}
