package com.cts.domain.service;

import com.cts.domain.model.Terrain;
import com.cts.domain.model.Tile;
import com.cts.domain.model.TileCell;
import java.util.ArrayList;
import java.util.List;

public class TileFactory {

    private TileFactory() {}

    public static List<Tile> createAllTiles() {
        List<Tile> tiles = new ArrayList<>();
        int number = 1;

        number = addTiles(tiles, number, Terrain.STEPPE, Terrain.STEPPE, 0, 0, 3);
        number = addTiles(tiles, number, Terrain.STEPPE, Terrain.LAC, 0, 1, 2);
        number = addTiles(tiles, number, Terrain.LAC, Terrain.LAC, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.JUNGLE, Terrain.JUNGLE, 1, 0, 3);
        number = addTiles(tiles, number, Terrain.JUNGLE, Terrain.CARRIERE, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.CARRIERE, Terrain.CARRIERE, 1, 0, 2);
        number = addTiles(tiles, number, Terrain.DESERT, Terrain.DESERT, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.STEPPE, Terrain.JUNGLE, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.STEPPE, Terrain.CARRIERE, 1, 0, 2);
        number = addTiles(tiles, number, Terrain.STEPPE, Terrain.DESERT, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.LAC, Terrain.JUNGLE, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.LAC, Terrain.CARRIERE, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.LAC, Terrain.DESERT, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.JUNGLE, Terrain.DESERT, 1, 0, 2);
        number = addTiles(tiles, number, Terrain.CARRIERE, Terrain.DESERT, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.STEPPE, Terrain.VOLCAN, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.LAC, Terrain.VOLCAN, 0, 1, 2);
        number = addTiles(tiles, number, Terrain.JUNGLE, Terrain.VOLCAN, 0, 2, 2);
        number = addTiles(tiles, number, Terrain.CARRIERE, Terrain.VOLCAN, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.DESERT, Terrain.VOLCAN, 0, 3, 2);
        number = addTiles(tiles, number, Terrain.VOLCAN, Terrain.VOLCAN, 1, 1, 2);
        number = addTiles(tiles, number, Terrain.VOLCAN, Terrain.VOLCAN, 2, 0, 2);
        number = addTiles(tiles, number, Terrain.STEPPE, Terrain.STEPPE, 1, 0, 1);
        number = addTiles(tiles, number, Terrain.LAC, Terrain.LAC, 1, 0, 1);

        return tiles;
    }

    private static int addTiles(List<Tile> tiles, int startNumber,
                                  Terrain t1, Terrain t2,
                                  int fire1, int fire2,
                                  int count) {
        int n = startNumber;
        for (int i = 0; i < count; i++) {
            tiles.add(new Tile(n, new TileCell(t1, fire1), new TileCell(t2, fire2)));
            n++;
        }
        return n;
    }
}
