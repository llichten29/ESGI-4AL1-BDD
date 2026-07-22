package com.cts.domain.service.game;

import com.cts.domain.exception.InvalidPlayerCountException;
import com.cts.domain.exception.InvalidSelectionException;
import com.cts.domain.model.common.GameMode;
import com.cts.domain.model.player.Draft;
import com.cts.domain.model.player.Player;
import com.cts.domain.model.player.PlayerColor;
import com.cts.domain.model.tile.Terrain;
import com.cts.domain.model.tile.Tile;
import com.cts.domain.model.tile.Tile.TileCell;
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
    private final GameMode gameMode;
    private int turnNumber;
    private Tile lastDiscardedTile;

    private static final PlayerColor[] DEFAULT_COLORS = {
        PlayerColor.ROSE, PlayerColor.NOIR, PlayerColor.VERT, PlayerColor.BLEU
    };

    public GameService(int playerCount, long seed) {
        this(playerCount, seed, GameMode.DECOUVERTE, new String[]{"Alice", "Bastien", "Camille", "David"});
    }

    public GameService(int playerCount, long seed, String[] playerNames) {
        this(playerCount, seed, GameMode.DECOUVERTE, playerNames);
    }

    public GameService(int playerCount, long seed, GameMode gameMode) {
        this(playerCount, seed, gameMode, new String[]{"Alice", "Bastien", "Camille", "David"});
    }

    public GameService(int playerCount, long seed, GameMode gameMode, String[] playerNames) {
        if (playerCount < 3 || playerCount > 4) {
            throw new InvalidPlayerCountException(
                "une partie necessite 3 ou 4 joueurs"
            );
        }
        this.seed = seed;
        this.gameMode = gameMode;
        this.turnNumber = 0;

        List<Tile> allTiles = createAllTiles();
        Collections.shuffle(allTiles, new Random(seed));
        this.drawPile = new ArrayList<>(allTiles);

        this.players = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            PlayerColor color = (i < DEFAULT_COLORS.length) ? DEFAULT_COLORS[i] : PlayerColor.ROSE;
            players.add(new Player(playerNames[i], color));
        }

        prepareNextDraft();
    }

    public void prepareNextDraft() {
        int tilesToDraw = Math.min(4, drawPile.size());
        List<Tile> draftTiles = new ArrayList<>(drawPile.subList(0, tilesToDraw));
        drawPile.subList(0, tilesToDraw).clear();
        draftTiles.sort(Comparator.comparingInt(Tile::getNumber));
        currentDraft = new Draft(draftTiles);
        turnNumber++;
    }

    public void advanceRound() {
        if (turnNumber >= 12) {
            throw new IllegalStateException("la derniere ligne est deja formee");
        }
        List<Tile> unchosen = currentDraft.getUnchosenTiles();
        lastDiscardedTile = unchosen.isEmpty() ? null : unchosen.get(0);
        for (Player p : players) {
            p.resetChoice();
        }
        prepareNextDraft();
    }

    public boolean canAdvance() {
        return turnNumber < 12;
    }

    public boolean isGameOver() {
        return turnNumber >= 12;
    }

    public Tile getLastDiscardedTile() {
        return lastDiscardedTile;
    }

    public GameMode getGameMode() {
        return gameMode;
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

    public Player findPlayerByColor(PlayerColor color) {
        for (Player p : getPlayers()) {
            if (p.getColor() == color) {
                return p;
            }
        }
        return null;
    }

    public Player findPlayerByName(String name) {
        for (Player p : getPlayers()) {
            if (p.getName().equals(name)) {
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

    private static List<Tile> createAllTiles() {
        List<Tile> tiles = new ArrayList<>();
        int number = 1;

        number = addTiles(tiles, number, Terrain.STEPPE, Terrain.STEPPE, 0, 0, 3);
        number = addTiles(tiles, number, Terrain.STEPPE, Terrain.LAC, 0, 1, 2);
        number = addTiles(tiles, number, Terrain.LAC, Terrain.LAC, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.JUNGLE, Terrain.JUNGLE, 1, 0, 3);
        number = addTiles(tiles, number, Terrain.JUNGLE, Terrain.CARRIERE, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.CARRIERE, Terrain.CARRIERE, 1, 0, 2);
        number = addTiles(tiles, number, Terrain.DESERT, Terrain.DESERT, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.STEPPE, Terrain.JUNGLE, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.STEPPE, Terrain.CARRIERE, 1, 0, 2);
        number = addTiles(tiles, number, Terrain.STEPPE, Terrain.DESERT, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.LAC, Terrain.JUNGLE, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.LAC, Terrain.CARRIERE, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.LAC, Terrain.DESERT, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.JUNGLE, Terrain.DESERT, 1, 0, 2);
        number = addTiles(tiles, number, Terrain.CARRIERE, Terrain.DESERT, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.STEPPE, Terrain.VOLCAN, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.LAC, Terrain.VOLCAN, 0, 1, 2);
        number = addTiles(tiles, number, Terrain.JUNGLE, Terrain.VOLCAN, 0, 2, 2);
        number = addTiles(tiles, number, Terrain.CARRIERE, Terrain.VOLCAN, 0, 0, 2);
        number = addTiles(tiles, number, Terrain.DESERT, Terrain.VOLCAN, 0, 3, 2);
        number = addTiles(tiles, number, Terrain.VOLCAN, Terrain.VOLCAN, 1, 1, 2);
        number = addTiles(tiles, number, Terrain.VOLCAN, Terrain.VOLCAN, 2, 0, 2);
        number = addTiles(tiles, number, Terrain.STEPPE, Terrain.STEPPE, 1, 0, 1);
        addTiles(tiles, number, Terrain.LAC, Terrain.LAC, 1, 0, 1);

        return tiles;
    }

    private static int addTiles(List<Tile> tiles, int startNumber,
                                  Terrain t1, Terrain t2,
                                  int fire1, int fire2,
                                  int count) {
        int n = startNumber;
        for (int i = 0; i < count; i++) {
            tiles.add(new Tile(n, new TileCell(t1, fire1), new TileCell(t2, fire2)));
            n++;
        }
        return n;
    }
}
