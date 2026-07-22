package com.cts.domain.service.board;

import com.cts.domain.exception.InvalidPlacementException;
import com.cts.domain.model.board.Kingdom;
import com.cts.domain.model.board.FireToken;
import com.cts.domain.model.common.Position;
import com.cts.domain.model.tile.Terrain;
import com.cts.domain.model.tile.Tile;
import com.cts.domain.model.tile.TileCell;
import java.util.ArrayList;
import java.util.List;

public class BoardService {

    // -- Placement (domino) --

    public boolean canPlace(Kingdom kingdom, Tile tile, Position posA, Position posB) {
        return collectErrors(kingdom, tile, posA, posB).isEmpty();
    }

    public void place(Kingdom kingdom, Tile tile, Position posA, Position posB) {
        List<String> errors = collectErrors(kingdom, tile, posA, posB);
        if (!errors.isEmpty()) {
            throw new InvalidPlacementException(errors.get(0));
        }
        kingdom.placeTile(tile, posA, posB);
    }

    private List<String> collectErrors(Kingdom kingdom, Tile tile, Position posA, Position posB) {
        List<String> errors = new ArrayList<>();
        if (!withinGrid(posA) || !withinGrid(posB)) {
            errors.add("hors de la grille 5x5");
        }
        if (!posA.isAdjacent(posB)) {
            errors.add("les deux cases du domino ne sont pas adjacentes");
        }
        if (kingdom.isOccupied(posA) || kingdom.isOccupied(posB)) {
            errors.add("case deja occupee");
        }
        if (!hasAdjacentMatch(kingdom, tile, posA, posB)) {
            errors.add("aucun terrain adjacent compatible");
        }
        return errors;
    }

    public List<Position[]> findValidPlacements(Kingdom kingdom, Tile tile) {
        List<Position[]> results = new ArrayList<>();

        for (int x = 0; x < Kingdom.SIZE; x++) {
            for (int y = 0; y < Kingdom.SIZE; y++) {
                Position a = new Position(x, y);
                addValidPlacementsFrom(results, kingdom, tile, a);
            }
        }
        return results;
    }

    private void addValidPlacementsFrom(List<Position[]> results, Kingdom kingdom, Tile tile, Position a) {
        for (int[] d : Position.CARDINAL_DIRECTIONS) {
            Position b = new Position(a.x() + d[0], a.y() + d[1]);
            if (withinGrid(b) && canPlace(kingdom, tile, a, b)) {
                results.add(new Position[]{a, b});
            }
        }
    }

    public boolean hasAnyPlacement(Kingdom kingdom, Tile tile) {
        return !findValidPlacements(kingdom, tile).isEmpty();
    }

    private boolean withinGrid(Position p) {
        return p.x() >= 0 && p.x() < Kingdom.SIZE && p.y() >= 0 && p.y() < Kingdom.SIZE;
    }

    private boolean hasAdjacentMatch(Kingdom kingdom, Tile tile, Position posA, Position posB) {
        for (Position occupied : kingdom.getOccupiedPositions()) {
            Terrain occupiedTerrain = kingdom.getCell(occupied).getTerrain();
            boolean isChateau = occupiedTerrain == Terrain.CHATEAU;
            if (posA.isAdjacent(occupied) && (isChateau || tile.getCellA().getTerrain() == occupiedTerrain))
                return true;
            if (posB.isAdjacent(occupied) && (isChateau || tile.getCellB().getTerrain() == occupiedTerrain))
                return true;
        }
        return false;
    }

    // -- Fire tokens (volcan) --

    public static int getFireCountForVolcano(Terrain cell, Terrain other) {
        if (cell != Terrain.VOLCAN) return 0;
        return switch (other) {
            case LAC -> 1;
            case JUNGLE -> 2;
            case DESERT -> 3;
            default -> 0;
        };
    }

    public List<FireToken> collectFireTokens(Tile tile) {
        List<FireToken> tokens = new ArrayList<>();
        addIfVolcano(tile.getCellA(), tokens);
        addIfVolcano(tile.getCellB(), tokens);
        return tokens;
    }

    private void addIfVolcano(TileCell cell, List<FireToken> tokens) {
        if (cell.getTerrain() == Terrain.VOLCAN && cell.getFireCount() > 0) {
            tokens.add(new FireToken(cell.getFireCount()));
        }
    }

    public boolean canPlaceFireToken(Kingdom kingdom, Position target, Position volcanoPos, FireToken token) {
        if (!kingdom.isOccupied(target)) return false;
        if (target.equals(volcanoPos)) return false;
        TileCell cell = kingdom.getCell(target);
        if (cell.getTerrain() == Terrain.VOLCAN) return false;
        if (cell.getFireCount() > 0) return false;
        if (kingdom.hasFireToken(target)) return false;
        int distance = Math.max(
            Math.abs(target.x() - volcanoPos.x()),
            Math.abs(target.y() - volcanoPos.y())
        );
        return distance <= token.getRange();
    }

    public List<Position> findValidFireTokenPlacements(Kingdom kingdom, Position volcanoPos, FireToken token) {
        List<Position> valid = new ArrayList<>();
        for (Position pos : kingdom.getOccupiedPositions()) {
            if (canPlaceFireToken(kingdom, pos, volcanoPos, token)) {
                valid.add(pos);
            }
        }
        return valid;
    }

    public void placeFireToken(Kingdom kingdom, Position target, Position volcanoPos, FireToken token) {
        if (!canPlaceFireToken(kingdom, target, volcanoPos, token)) {
            throw new InvalidPlacementException("placement de jeton feu invalide");
        }
        kingdom.placeFireToken(target, token);
    }
}
