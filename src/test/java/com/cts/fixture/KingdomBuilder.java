package com.cts.fixture;

import com.cts.domain.model.board.Kingdom;
import com.cts.domain.model.common.Position;
import com.cts.domain.model.tile.Terrain;
import com.cts.domain.model.tile.Tile;
import com.cts.domain.model.tile.TileCell;
import com.cts.domain.service.board.BoardService;

public class KingdomBuilder {

    private final Kingdom kingdom;
    private final BoardService boardService;

    public KingdomBuilder() {
        this.kingdom = new Kingdom();
        this.boardService = new BoardService();
    }

    public KingdomBuilder(Kingdom kingdom) {
        this.kingdom = kingdom;
        this.boardService = new BoardService();
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void placeTile(Terrain t1, Terrain t2, int x1, int y1, int x2, int y2) {
        Tile tile = new Tile(0, new TileCell(t1, 0), new TileCell(t2, 0));
        boardService.place(kingdom, tile, new Position(x1, y1), new Position(x2, y2));
    }

    public void placeSingleCell(Terrain terrain, int x, int y) {
        Position target = new Position(x, y);
        if (kingdom.isOccupied(target)) return;

        for (Position occupied : kingdom.getOccupiedPositions()) {
            if (target.isAdjacent(occupied)) {
                Position other = findFreeNeighbor(target);
                if (other != null) {
                    Tile tile = new Tile(0, new TileCell(terrain, 0), new TileCell(terrain, 0));
                    boardService.place(kingdom, tile, target, other);
                    return;
                }
            }
        }

        Position adj = findOccupiedNeighbor(target);
        if (adj != null) {
            Tile tile = new Tile(0, new TileCell(terrain, 0), new TileCell(Terrain.DESERT, 0));
            TileCell cellAtTarget = target.equals(adj) ? tile.getCellB() : tile.getCellA();
            if (cellAtTarget.getTerrain() != terrain) {
                tile = new Tile(0, new TileCell(Terrain.DESERT, 0), new TileCell(terrain, 0));
            }
            boardService.place(kingdom, tile, adj, target);
        }
    }

    private Position findFreeNeighbor(Position target) {
        for (int[] d : Position.CARDINAL_DIRECTIONS) {
            Position p = new Position(target.x() + d[0], target.y() + d[1]);
            if (p.x() >= 0 && p.x() < Kingdom.SIZE && p.y() >= 0 && p.y() < Kingdom.SIZE
                && !kingdom.isOccupied(p)) {
                return p;
            }
        }
        return null;
    }

    private Position findOccupiedNeighbor(Position target) {
        for (int[] d : Position.CARDINAL_DIRECTIONS) {
            Position p = new Position(target.x() + d[0], target.y() + d[1]);
            if (p.x() >= 0 && p.x() < Kingdom.SIZE && p.y() >= 0 && p.y() < Kingdom.SIZE
                && kingdom.isOccupied(p)) {
                return p;
            }
        }
        return null;
    }
}
