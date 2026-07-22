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
    private GameService game;
    private GameService otherGame;
    private String errorMessage;
    private String[] customPlayerNames;
    private Kingdom kingdom;
    private ScoreResult lastScore;
    private ScoreResult otherScore;
    private Tile currentTile;
    private final Map<PlayerColor, Map<Resource, Integer>> playerResources = new HashMap<>();
    private final Map<Resource, PlayerColor> totemOwners = new EnumMap<>(Resource.class);
    private int totemTileScore;
    private final Map<PlayerColor, Integer> resourceCounts = new HashMap<>();
    private PlayerColor winner;

    public GameService getGame() {
        return game;
    }

    public void setGame(GameService game) {
        this.game = game;
    }

    public GameService getOtherGame() {
        return otherGame;
    }

    public void setOtherGame(GameService otherGame) {
        this.otherGame = otherGame;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String[] getCustomPlayerNames() {
        return customPlayerNames;
    }

    public void setCustomPlayerNames(String[] customPlayerNames) {
        this.customPlayerNames = customPlayerNames;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

    public ScoreResult getLastScore() {
        return lastScore;
    }

    public void setLastScore(ScoreResult lastScore) {
        this.lastScore = lastScore;
    }

    public ScoreResult getOtherScore() {
        return otherScore;
    }

    public void setOtherScore(ScoreResult otherScore) {
        this.otherScore = otherScore;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public Map<PlayerColor, Map<Resource, Integer>> getPlayerResources() {
        return playerResources;
    }

    public Map<Resource, PlayerColor> getTotemOwners() {
        return totemOwners;
    }

    public int getTotemTileScore() {
        return totemTileScore;
    }

    public void setTotemTileScore(int totemTileScore) {
        this.totemTileScore = totemTileScore;
    }

    public Map<PlayerColor, Integer> getResourceCounts() {
        return resourceCounts;
    }

    public PlayerColor getWinner() {
        return winner;
    }

    public void setWinner(PlayerColor winner) {
        this.winner = winner;
    }

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
