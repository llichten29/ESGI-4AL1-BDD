package com.cts.domain.model.board;

import com.cts.domain.model.common.Position;
import com.cts.domain.model.tile.Terrain;
import com.cts.domain.model.tile.Tile;
import com.cts.domain.model.tile.Tile.TileCell;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kingdom {
    public static final int SIZE = 5;
    public static final Position STARTING_POSITION = new Position(2, 2);

    private final Map<Position, TileCell> grid;
    private final Map<Position, FireToken> fireTokens;
    private int cellCount;

    public Kingdom() {
        grid = new HashMap<>();
        grid.put(STARTING_POSITION, new TileCell(Terrain.CHATEAU, 0));
        fireTokens = new HashMap<>();
        cellCount = 1;
    }

    public TileCell getCell(Position pos) {
        return grid.get(pos);
    }

    public boolean isOccupied(Position pos) {
        return grid.containsKey(pos);
    }

    public List<Position> getOccupiedPositions() {
        return new ArrayList<>(grid.keySet());
    }

    public void placeTile(Tile tile, Position posA, Position posB) {
        grid.put(posA, tile.getCellA());
        grid.put(posB, tile.getCellB());
        cellCount += 2;
    }

    public int getCellCount() {
        return cellCount;
    }

    public void placeFireToken(Position pos, FireToken token) {
        fireTokens.put(pos, token);
    }

    public boolean hasFireToken(Position pos) {
        return fireTokens.containsKey(pos);
    }

    public FireToken getFireToken(Position pos) {
        return fireTokens.get(pos);
    }

    public void placeCell(Position pos, TileCell cell) {
        boolean wasOccupied = grid.containsKey(pos);
        grid.put(pos, cell);
        if (!wasOccupied) {
            cellCount++;
        }
    }

    public void fillTerritory(int count, Terrain terrain) {
        for (int x = 0; x < SIZE && cellCount < count; x++) {
            for (int y = 0; y < SIZE && cellCount < count; y++) {
                Position pos = new Position(x, y);
                if (!isOccupied(pos)) {
                    placeCell(pos, new TileCell(terrain, 0));
                }
            }
        }
    }

    public Position findAdjacentFreePosition(Position target) {
        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        for (int[] d : dirs) {
            Position p = new Position(target.x() + d[0], target.y() + d[1]);
            if (p.x() >= 0 && p.x() < SIZE && p.y() >= 0 && p.y() < SIZE && !isOccupied(p)) {
                return p;
            }
        }
        return null;
    }

    public Position findAdjacentOccupied(Position target) {
        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        for (int[] d : dirs) {
            Position p = new Position(target.x() + d[0], target.y() + d[1]);
            if (p.x() >= 0 && p.x() < SIZE && p.y() >= 0 && p.y() < SIZE && isOccupied(p)) {
                return p;
            }
        }
        return null;
    }

    public static class FireToken {
        private final int count;

        public FireToken(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public int getRange() {
            return 4 - count;
        }
    }
}
