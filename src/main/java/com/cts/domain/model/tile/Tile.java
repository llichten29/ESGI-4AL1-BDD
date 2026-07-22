package com.cts.domain.model.tile;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return number == tile.number && Objects.equals(cellA, tile.cellA) && Objects.equals(cellB, tile.cellB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, cellA, cellB);
    }
}
