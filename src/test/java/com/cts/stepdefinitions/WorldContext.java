package com.cts.stepdefinitions;

import com.cts.domain.model.board.Kingdom;
import com.cts.domain.model.common.Resource;
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
    public Map<String, Map<Resource, Integer>> playerResources = new HashMap<>();
    public Map<Resource, String> totemOwners = new EnumMap<>(Resource.class);
    public int totemTileScore;
    public Map<String, Integer> resourceCounts = new HashMap<>();
    public String winner;
}
