package com.cts.domain.model;

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
}
