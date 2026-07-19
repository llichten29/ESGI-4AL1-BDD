package com.cts.domain.model;

import java.util.Collections;
import java.util.List;

public class Draft {
    private final List<Tile> tiles;

    public Draft(List<Tile> tiles) {
        List<Tile> sorted = new java.util.ArrayList<>(tiles);
        Collections.sort(sorted, (a, b) -> Integer.compare(a.getNumber(), b.getNumber()));
        this.tiles = Collections.unmodifiableList(sorted);
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public int size() {
        return tiles.size();
    }
}
