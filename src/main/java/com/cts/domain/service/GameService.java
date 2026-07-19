package com.cts.domain.service;

import com.cts.domain.exception.InvalidPlayerCountException;
import com.cts.domain.model.Draft;
import com.cts.domain.model.Player;
import com.cts.domain.model.Tile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameService {

    private final List<Tile> drawPile;
    private final List<Player> players;
    private Draft currentDraft;
    private final long seed;
    private int turnNumber;

    public GameService(int playerCount, long seed) {
        if (playerCount < 3 || playerCount > 4) {
            throw new InvalidPlayerCountException(
                "une partie necessite 3 ou 4 joueurs"
            );
        }
        this.seed = seed;
        this.turnNumber = 0;

        List<Tile> allTiles = TileFactory.createAllTiles();
        Collections.shuffle(allTiles, new Random(seed));
        this.drawPile = new ArrayList<>(allTiles);

        this.players = new ArrayList<>();
        String[] colors = {"rose", "noir", "vert", "bleu"};
        for (int i = 0; i < playerCount; i++) {
            players.add(new Player(colors[i]));
        }

        prepareNextDraft();
    }

    private void prepareNextDraft() {
        List<Tile> draftTiles = new ArrayList<>();
        for (int i = 0; i < 4 && !drawPile.isEmpty(); i++) {
            draftTiles.add(drawPile.remove(0));
        }
        currentDraft = new Draft(draftTiles);
        turnNumber++;
    }

    public int getDrawPileSize() {
        return drawPile.size();
    }

    public List<Tile> getDrawPile() {
        return Collections.unmodifiableList(drawPile);
    }

    public Draft getCurrentDraft() {
        return currentDraft;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public long getSeed() {
        return seed;
    }

    public int getTurnNumber() {
        return turnNumber;
    }
}
