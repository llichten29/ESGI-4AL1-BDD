package com.cts.domain.service.game;

import com.cts.domain.exception.InvalidPlayerCountException;
import com.cts.domain.exception.InvalidSelectionException;
import com.cts.domain.model.common.GameMode;
import com.cts.domain.model.player.Draft;
import com.cts.domain.model.player.Player;
import com.cts.domain.model.player.PlayerColor;
import com.cts.domain.model.tile.Tile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameService {

    private final DrawPile drawPile;
    private final RoundManager roundManager;
    private final List<Player> players;
    private final long seed;
    private final GameMode gameMode;

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
        this.drawPile = new DrawPile(seed);
        this.roundManager = new RoundManager();

        this.players = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            PlayerColor color = (i < DEFAULT_COLORS.length) ? DEFAULT_COLORS[i] : PlayerColor.ROSE;
            players.add(new Player(playerNames[i], color));
        }

        prepareNextDraft();
    }

    public void prepareNextDraft() {
        roundManager.prepareNextDraft(drawPile);
    }

    public void advanceRound() {
        roundManager.advanceRound(drawPile, players);
    }

    public boolean canAdvance() {
        return roundManager.canAdvance();
    }

    public boolean isGameOver() {
        return roundManager.isGameOver();
    }

    public Tile getLastDiscardedTile() {
        return roundManager.getLastDiscardedTile();
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void selectTileByNumber(Player player, int tileNumber) {
        Draft draft = roundManager.getCurrentDraft();
        Tile tile = findTileInDraft(tileNumber, draft);
        if (tile == null) {
            throw new InvalidSelectionException(
                "domino numero " + tileNumber + " pas dans la draft"
            );
        }
        if (draft.isTileNumberChosen(tileNumber)) {
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
        draft.markChosen(tileNumber);
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

    private Tile findTileInDraft(int tileNumber, Draft draft) {
        return draft.getTiles().stream()
            .filter(t -> t.getNumber() == tileNumber)
            .findFirst()
            .orElse(null);
    }

    public int getDrawPileSize() {
        return drawPile.size();
    }

    public List<Tile> getDrawPile() {
        return drawPile.getAll();
    }

    public Draft getCurrentDraft() {
        return roundManager.getCurrentDraft();
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public long getSeed() {
        return seed;
    }

    public int getTurnNumber() {
        return roundManager.getTurnNumber();
    }

}
