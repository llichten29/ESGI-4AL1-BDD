package com.cts.domain.model.player;

import com.cts.domain.model.tile.Tile;

public class Player {
    private final String color;
    private Tile chosenTile;

    public Player(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void chooseTile(Tile tile) {
        this.chosenTile = tile;
    }

    public Tile getChosenTile() {
        return chosenTile;
    }

    public boolean hasChosen() {
        return chosenTile != null;
    }

    public void resetChoice() {
        this.chosenTile = null;
    }
}
