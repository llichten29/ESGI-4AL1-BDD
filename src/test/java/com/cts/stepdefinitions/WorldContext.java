package com.cts.stepdefinitions;

import com.cts.domain.model.Kingdom;
import com.cts.domain.model.Resource;
import com.cts.domain.model.ScoreResult;
import com.cts.domain.model.Tile;
import com.cts.domain.service.GameService;
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
    public Map<Resource, String> totemOwners = new HashMap<>();
    public int totemTileScore;
    public Map<String, Integer> resourceCounts = new HashMap<>();
    public String winner;
}
