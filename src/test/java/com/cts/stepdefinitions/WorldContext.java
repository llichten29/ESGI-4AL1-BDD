package com.cts.stepdefinitions;

import com.cts.domain.model.Kingdom;
import com.cts.domain.model.ScoreResult;
import com.cts.domain.model.Tile;
import com.cts.domain.service.GameService;

public class WorldContext {
    public GameService game;
    public GameService otherGame;
    public String errorMessage;
    public String[] customPlayerNames;
    public Kingdom kingdom;
    public ScoreResult lastScore;
    public ScoreResult otherScore;
}
