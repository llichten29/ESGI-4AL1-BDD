package com.cts.domain.service.game;

import com.cts.domain.model.player.Draft;
import com.cts.domain.model.player.Player;
import com.cts.domain.model.tile.Tile;
import java.util.Comparator;
import java.util.List;

public class RoundManager {

    private static final int MAX_ROUNDS = 12;

    private int turnNumber;
    private Draft currentDraft;
    private Tile lastDiscardedTile;

    public RoundManager() {
        this.turnNumber = 0;
    }

    public void prepareNextDraft(DrawPile drawPile) {
        List<Tile> draftTiles = drawPile.draw(4);
        draftTiles.sort(Comparator.comparingInt(Tile::getNumber));
        currentDraft = new Draft(draftTiles);
        turnNumber++;
    }

    public void advanceRound(DrawPile drawPile, List<Player> players) {
        if (turnNumber >= MAX_ROUNDS) {
            throw new IllegalStateException("la derniere ligne est deja formee");
        }
        List<Tile> unchosen = currentDraft.getUnchosenTiles();
        lastDiscardedTile = unchosen.isEmpty() ? null : unchosen.get(0);
        for (Player p : players) {
            p.resetChoice();
        }
        prepareNextDraft(drawPile);
    }

    public boolean canAdvance() {
        return turnNumber < MAX_ROUNDS;
    }

    public boolean isGameOver() {
        return turnNumber >= MAX_ROUNDS;
    }

    public Draft getCurrentDraft() {
        return currentDraft;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public Tile getLastDiscardedTile() {
        return lastDiscardedTile;
    }
}
