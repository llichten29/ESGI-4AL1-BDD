package com.cts.domain.service.totem;

import com.cts.domain.model.board.Kingdom;
import com.cts.domain.model.common.Position;
import com.cts.domain.model.common.Resource;
import com.cts.domain.model.player.PlayerColor;
import com.cts.domain.model.tile.Terrain;
import com.cts.domain.model.tile.Tile.TileCell;
import java.util.EnumMap;
import java.util.Map;

public class TotemService {

    private static final int TOTEM_POINTS = 5;

    public int getTotemPoints() {
        return TOTEM_POINTS;
    }

    public Map<Resource, PlayerColor> allocateTotems(
            Map<PlayerColor, Map<Resource, Integer>> allPlayerResources,
            Map<Resource, PlayerColor> currentOwners) {
        Map<Resource, PlayerColor> result = new EnumMap<>(Resource.class);
        for (Resource resource : Resource.values()) {
            PlayerColor owner = findMajorityOwner(allPlayerResources, resource, currentOwners.get(resource));
            result.put(resource, owner);
        }
        return result;
    }

    private PlayerColor findMajorityOwner(
            Map<PlayerColor, Map<Resource, Integer>> allPlayerResources,
            Resource resource,
            PlayerColor currentOwner) {
        PlayerColor topPlayer = null;
        int topCount = 0;
        boolean tie = false;

        for (Map.Entry<PlayerColor, Map<Resource, Integer>> entry : allPlayerResources.entrySet()) {
            int count = entry.getValue().getOrDefault(resource, 0);
            if (count > topCount) {
                topCount = count;
                topPlayer = entry.getKey();
                tie = false;
            } else if (count == topCount && count > 0) {
                tie = true;
            }
        }

        if (tie) return currentOwner;
        return topPlayer;
    }

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
