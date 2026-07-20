package com.cts.domain.service;

import com.cts.domain.exception.InvalidPlayerCountException;
import com.cts.domain.exception.InvalidSelectionException;
import com.cts.domain.model.Draft;
import com.cts.domain.model.Player;
import com.cts.domain.model.Tile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GameService {

    private final List<Tile> drawPile;
    private final List<Player> players;
    private Draft currentDraft;
    private final long seed;
    private int turnNumber;

    public GameService(int playerCount, long seed) {
        this(playerCount, seed, new String[]{"Alice", "Bastien", "Camille", "David"});
    }

    public GameService(int playerCount, long seed, String[] playerNames) {
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
        for (int i = 0; i < playerCount; i++) {
            players.add(new Player(playerNames[i]));
        }

        prepareNextDraft();
    }

    private void prepareNextDraft() {
        int tilesToDraw = Math.min(4, drawPile.size());
        List<Tile> draftTiles = new ArrayList<>(drawPile.subList(0, tilesToDraw));
        drawPile.subList(0, tilesToDraw).clear();
        draftTiles.sort(Comparator.comparingInt(Tile::getNumber));
        currentDraft = new Draft(draftTiles);
        turnNumber++;
    }

    public void selectTileByNumber(Player player, int tileNumber) {
        Tile tile = findTileInDraft(tileNumber);
        if (tile == null) {
            throw new InvalidSelectionException(
                "domino numero " + tileNumber + " pas dans la draft"
            );
        }
        if (currentDraft.isTileNumberChosen(tileNumber)) {
            throw new InvalidSelectionException(
                "domino numero " + tileNumber + " deja choisi"
            );
        }
        if (player.hasChosen()) {
            throw new InvalidSelectionException(
                "le joueur a deja choisi un domino"
            );
        }
        player.chooseTile(tile);
        currentDraft.markChosen(tileNumber);
    }

    public List<Player> getTurnOrder() {
        List<Player> ordered = new ArrayList<>();
        for (Player p : getPlayers()) {
            if (p.hasChosen()) {
                ordered.add(p);
            }
        }
        ordered.sort((a, b) -> Integer.compare(
            a.getChosenTile().getNumber(),
            b.getChosenTile().getNumber()
        ));
        return Collections.unmodifiableList(ordered);
    }

    public Player findPlayerByColor(String color) {
        for (Player p : getPlayers()) {
            if (p.getColor().equals(color)) {
                return p;
            }
        }
        return null;
    }

    private Tile findTileInDraft(int tileNumber) {
        return currentDraft.getTiles().stream()
            .filter(t -> t.getNumber() == tileNumber)
            .findFirst()
            .orElse(null);
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
