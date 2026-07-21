package com.cts.domain.model.player;

import com.cts.domain.model.tile.Tile;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Draft {
    private final List<Tile> tiles;
    private final Set<Integer> chosenTileNumbers;

    public Draft(List<Tile> tiles) {
        List<Tile> sorted = new java.util.ArrayList<>(tiles);
        Collections.sort(sorted, (a, b) -> Integer.compare(a.getNumber(), b.getNumber()));
        this.tiles = Collections.unmodifiableList(sorted);
        this.chosenTileNumbers = new HashSet<>();
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public int size() {
        return tiles.size();
    }

    public boolean isTileNumberChosen(int tileNumber) {
        return chosenTileNumbers.contains(tileNumber);
    }

    public boolean isFull() {
        return chosenTileNumbers.size() >= tiles.size();
    }

    public void markChosen(int tileNumber) {
        chosenTileNumbers.add(tileNumber);
    }

    public List<Tile> getUnchosenTiles() {
        return tiles.stream()
            .filter(t -> !chosenTileNumbers.contains(t.getNumber()))
            .toList();
    }

    public int getUnchosenCount() {
        return tiles.size() - chosenTileNumbers.size();
    }
}
