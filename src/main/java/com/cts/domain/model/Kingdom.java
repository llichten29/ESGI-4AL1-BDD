package com.cts.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kingdom {
    public static final int SIZE = 5;
    public static final Position STARTING_POSITION = new Position(2, 2);

    private final Map<Position, TileCell> grid;
    private int cellCount;

    public Kingdom() {
        grid = new HashMap<>();
        grid.put(STARTING_POSITION, new TileCell(Terrain.CHATEAU, 0));
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
}
