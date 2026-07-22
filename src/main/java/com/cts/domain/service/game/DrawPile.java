package com.cts.domain.service.game;

import com.cts.domain.model.tile.Tile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DrawPile {

    private final List<Tile> tiles;

    public DrawPile(long seed) {
        List<Tile> allTiles = TileCatalog.createAllTiles();
        Collections.shuffle(allTiles, new Random(seed));
        this.tiles = new ArrayList<>(allTiles);
    }

    public List<Tile> draw(int count) {
        int tilesToDraw = Math.min(count, tiles.size());
        List<Tile> drawn = new ArrayList<>(tiles.subList(0, tilesToDraw));
        tiles.subList(0, tilesToDraw).clear();
        return drawn;
    }

    public int size() {
        return tiles.size();
    }

    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    public List<Tile> getAll() {
        return Collections.unmodifiableList(tiles);
    }
}
