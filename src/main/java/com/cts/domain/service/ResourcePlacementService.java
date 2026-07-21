package com.cts.domain.service;

import com.cts.domain.model.Kingdom;
import com.cts.domain.model.Position;
import com.cts.domain.model.Resource;
import com.cts.domain.model.Terrain;
import com.cts.domain.model.TileCell;

public class ResourcePlacementService {

    public Resource getResourceForTerrain(Terrain terrain) {
        return switch (terrain) {
            case STEPPE -> Resource.MAMMOUTH;
            case LAC -> Resource.POISSON;
            case JUNGLE -> Resource.CHAMPIGNON;
            case CARRIERE -> Resource.SILEX;
            default -> null;
        };
    }

    public boolean canPlaceResource(TileCell cell) {
        return cell.getResource() == null
            && cell.getFireCount() == 0
            && getResourceForTerrain(cell.getTerrain()) != null;
    }

    public Resource placeResourceOnCell(Kingdom kingdom, Position pos) {
        TileCell cell = kingdom.getCell(pos);
        if (cell == null) return null;
        Resource resource = getResourceForTerrain(cell.getTerrain());
        if (resource != null && cell.getFireCount() == 0 && cell.getResource() == null) {
            kingdom.placeCell(pos, new TileCell(cell.getTerrain(), cell.getFireCount(), resource));
            return resource;
        }
        return null;
    }

    public Resource destroyResource(Kingdom kingdom, Position pos) {
        TileCell cell = kingdom.getCell(pos);
        if (cell == null) return null;
        Resource removed = cell.getResource();
        if (removed != null) {
            kingdom.placeCell(pos, new TileCell(cell.getTerrain(), cell.getFireCount(), null));
        }
        return removed;
    }
}
