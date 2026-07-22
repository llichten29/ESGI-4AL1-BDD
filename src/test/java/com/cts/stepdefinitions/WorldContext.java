package com.cts.stepdefinitions;

import com.cts.domain.model.board.Kingdom;
import com.cts.domain.model.common.Resource;
import com.cts.domain.model.player.PlayerColor;
import com.cts.domain.model.scoring.ScoreResult;
import com.cts.domain.model.tile.Tile;
import com.cts.domain.service.game.GameService;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class WorldContext {
    public GameService game;
    public GameService otherGame;
    public String errorMessage;
    public String[] customPlayerNames;
    public Kingdom kingdom;
    public ScoreResult lastScore;
    public ScoreResult otherScore;
    public Tile currentTile;
    public Map<PlayerColor, Map<Resource, Integer>> playerResources = new HashMap<>();
    public Map<Resource, PlayerColor> totemOwners = new EnumMap<>(Resource.class);
    public int totemTileScore;
    public Map<PlayerColor, Integer> resourceCounts = new HashMap<>();
    public PlayerColor winner;

    public static PlayerColor parsePlayerColor(String ref) {
        if (ref.contains("(")) {
            String colorStr = ref.substring(ref.indexOf('(') + 1, ref.indexOf(')')).trim();
            return PlayerColor.fromNom(colorStr);
        }
        return PlayerColor.forPlayerName(ref.trim());
    }

    public static String extractPlayerName(String ref) {
        if (ref.contains("(")) {
            return ref.substring(0, ref.indexOf('(')).trim();
        }
        return ref.trim();
    }
}
