package com.cts.stepdefinitions.decouverte;

import com.cts.domain.exception.InvalidPlacementException;
import com.cts.domain.model.board.Kingdom;
import com.cts.domain.model.board.Kingdom.FireToken;
import com.cts.domain.model.common.Position;
import com.cts.domain.model.tile.Terrain;
import com.cts.domain.model.tile.Tile;
import com.cts.domain.model.tile.Tile.TileCell;
import com.cts.domain.service.board.BoardService;
import com.cts.stepdefinitions.WorldContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PlacementSteps {

    private final WorldContext world;
    private Tile currentTile;
    private boolean lastPlacementAccepted;
    private String lastError;
    private List<Position[]> validPositions;
    private FireToken currentFireToken;

    public PlacementSteps(WorldContext world) {
        this.world = world;
    }

    @Given("un joueur {word} avec la tuile depart en position \\({int},{int}\\)")
    public void unJoueurAvecTuileDepart(String color, int x, int y) {
        world.kingdom = new Kingdom();
        assertEquals(Terrain.CHATEAU, world.kingdom.getCell(new Position(x, y)).getTerrain());
    }

    @Given("le joueur a deja pose un domino {word}-{word} en \\({int},{int}\\) et \\({int},{int}\\)")
    public void leJoueurADejaPoseUnDomino(String t1, String t2, int x1, int y1, int x2, int y2) {
        Terrain terrainA = parseTerrain(t1);
        Terrain terrainB = parseTerrain(t2);
        Tile tile = new Tile(0, new TileCell(terrainA, 0), new TileCell(terrainB, 0));
        new BoardService().place(world.kingdom, tile, new Position(x1, y1), new Position(x2, y2));
    }

    @Given("le joueur a deja pose une {word} en \\({int},{int}\\)")
    public void leJoueurADejaPose(String terrainStr, int x, int y) {
        Terrain terrain = parseTerrain(terrainStr);
        Position target = new Position(x, y);

        if (world.kingdom.isOccupied(target)) return;

        for (Position occupied : world.kingdom.getOccupiedPositions()) {
            if (target.isAdjacent(occupied)) {
                Position other = findAdjacentFreePosition(target);
                if (other != null) {
                    Tile tile = new Tile(0, new TileCell(terrain, 0), new TileCell(terrain, 0));
                    if (target.isAdjacent(other)) {
                        new BoardService().place(world.kingdom, tile, target, other);
                        return;
                    }
                }
            }
        }

        Position adj = findAdjacentOccupied(target);
        if (adj != null) {
            Tile tile = new Tile(0, new TileCell(terrain, 0), new TileCell(Terrain.DESERT, 0));
            TileCell cellAtTarget = (target.equals(adj)) ? tile.getCellB() : tile.getCellA();
            if (cellAtTarget.getTerrain() != terrain) {
                tile = new Tile(0, new TileCell(Terrain.DESERT, 0), new TileCell(terrain, 0));
            }
            new BoardService().place(world.kingdom, tile, adj, target);
        }
    }

    @Given("un volcan en \\({int},{int}\\) avec feu {int}")
    public void unVolcanEnAvecFeu(int x, int y, int fireCount) {
        world.kingdom.placeCell(new Position(x, y), new TileCell(Terrain.VOLCAN, fireCount));
    }

    @Given("une case {word} en \\({int},{int}\\) avec icone feu {int}")
    public void uneCaseEnAvecIconeFeu(String terrainStr, int x, int y, int fireCount) {
        Terrain terrain = parseTerrain(terrainStr);
        world.kingdom.placeCell(new Position(x, y), new TileCell(terrain, fireCount));
    }

    @Given("un jeton feu de valeur {int}")
    public void unJetonFeuDeValeur(int value) {
        currentFireToken = new FireToken(value);
    }

    @Given("un jeton feu de valeur {int} place en \\({int},{int}\\)")
    public void unJetonFeuDeValeurPlaceEn(int value, int x, int y) {
        world.kingdom.placeFireToken(new Position(x, y), new FireToken(value));
    }

    @When("le joueur pose un domino {word}-{word} en \\({int},{int}\\) et \\({int},{int}\\)")
    public void leJoueurPoseUnDomino(String t1, String t2, int x1, int y1, int x2, int y2) {
        Terrain terrainA = parseTerrain(t1);
        Terrain terrainB = parseTerrain(t2);
        int fireA = volcanoFireCount(terrainA, terrainB);
        int fireB = volcanoFireCount(terrainB, terrainA);
        currentTile = new Tile(0, new TileCell(terrainA, fireA), new TileCell(terrainB, fireB));
        Position posA = new Position(x1, y1);
        Position posB = new Position(x2, y2);

        try {
            new BoardService().place(world.kingdom, currentTile, posA, posB);
            lastPlacementAccepted = true;
            lastError = null;
        } catch (InvalidPlacementException e) {
            lastPlacementAccepted = false;
            lastError = e.getReason();
        }
    }

    @When("le joueur tente de poser un domino en \\({int},{int}\\) et \\({int},{int}\\)")
    public void leJoueurTenteDePoserSansTerrain(int x1, int y1, int x2, int y2) {
        if (currentTile == null) {
            currentTile = new Tile(0, new TileCell(Terrain.STEPPE, 0), new TileCell(Terrain.LAC, 0));
        }
        Position posA = new Position(x1, y1);
        Position posB = new Position(x2, y2);

        try {
            new BoardService().place(world.kingdom, currentTile, posA, posB);
            lastPlacementAccepted = true;
            lastError = null;
        } catch (InvalidPlacementException e) {
            lastPlacementAccepted = false;
            lastError = e.getReason();
        }
    }

    @When("le joueur tente de poser un domino {word}-{word} en \\({int},{int}\\) et \\({int},{int}\\)")
    public void leJoueurTenteDePoserUnDomino(String t1, String t2, int x1, int y1, int x2, int y2) {
        leJoueurPoseUnDomino(t1, t2, x1, y1, x2, y2);
    }

    @When("le joueur cherche les positions valides pour un domino {word}-{word}")
    public void leJoueurCherchePositionsValides(String t1, String t2) {
        Terrain terrainA = parseTerrain(t1);
        Terrain terrainB = parseTerrain(t2);
        currentTile = new Tile(0, new TileCell(terrainA, 0), new TileCell(terrainB, 0));
        validPositions = new BoardService().findValidPlacements(world.kingdom, currentTile);
    }

    @When("le joueur recoit un domino {word}-{word}")
    public void leJoueurRecoitUnDomino(String t1, String t2) {
        Terrain terrainA = parseTerrain(t1);
        Terrain terrainB = parseTerrain(t2);
        currentTile = new Tile(0, new TileCell(terrainA, 0), new TileCell(terrainB, 0));
    }

    @When("le joueur place un jeton feu de valeur {int} depuis le volcan en \\({int},{int}\\) sur la case \\({int},{int}\\)")
    public void leJoueurPlaceUnJetonFeuDepuisLeVolcanSurLaCase(int value, int vx, int vy, int tx, int ty) {
        try {
            new BoardService().placeFireToken(world.kingdom, new Position(tx, ty), new Position(vx, vy), new FireToken(value));
            lastPlacementAccepted = true;
            lastError = null;
        } catch (IllegalArgumentException e) {
            lastPlacementAccepted = false;
            lastError = e.getMessage();
        }
    }

    @When("le joueur tente de placer un jeton feu de valeur {int} depuis le volcan en \\({int},{int}\\) sur la case \\({int},{int}\\)")
    public void leJoueurTenteDePlacerUnJetonFeuDepuisLeVolcanSurLaCase(int value, int vx, int vy, int tx, int ty) {
        leJoueurPlaceUnJetonFeuDepuisLeVolcanSurLaCase(value, vx, vy, tx, ty);
    }

    @When("le joueur tente de placer le jeton feu depuis le volcan en \\({int},{int}\\)")
    public void leJoueurTenteDePlacerLeJetonFeuDepuisLeVolcanEn(int vx, int vy) {
        Position volcanoPos = new Position(vx, vy);
        List<Position> valid = new BoardService().findValidFireTokenPlacements(world.kingdom, volcanoPos, currentFireToken);
        if (valid.isEmpty()) {
            lastPlacementAccepted = false;
            lastError = "defausse";
        } else {
            lastPlacementAccepted = true;
            lastError = null;
        }
    }

    @Then("le placement est accepte")
    public void lePlacementEstAccepte() {
        assertTrue(lastPlacementAccepted, "Placement refusé: " + (lastError != null ? lastError : ""));
    }

    @Then("le territoire contient {int} cases")
    public void leTerritoireContientCases(int count) {
        assertEquals(count, world.kingdom.getCellCount());
    }

    @Then("plusieurs positions sont proposees")
    public void plusieursPositionsProposees() {
        assertNotNull(validPositions);
        assertTrue(validPositions.size() >= 2, "Au moins 2 positions valides attendues, mais " + validPositions.size() + " trouvees");
    }

    @Then("le domino ne peut pas etre place")
    public void leDominoNePeutPasEtrePlace() {
        assertNotNull(currentTile);
        boolean hasAny = new BoardService().hasAnyPlacement(world.kingdom, currentTile);
        assertFalse(hasAny, "Le domino peut etre place alors qu il devrait etre impossible");
    }

    @Then("le domino est defausse et ne rapporte aucun point")
    public void leDominoEstDefausse() {
        leDominoNePeutPasEtrePlace();
    }

    @Then("le joueur est oblige de le poser sur une position valide")
    public void leJoueurObligeDePoser() {
        assertNotNull(currentTile);
        boolean hasAny = new BoardService().hasAnyPlacement(world.kingdom, currentTile);
        assertTrue(hasAny, "Le domino n a aucune position valide, mais devrait en avoir au moins une");
        assertDoesNotThrow(() -> {
            List<Position[]> positions = new BoardService().findValidPlacements(world.kingdom, currentTile);
            assertFalse(positions.isEmpty());
            new BoardService().place(world.kingdom, currentTile, positions.get(0)[0], positions.get(0)[1]);
        });
    }

    @Then("le placement est refuse car {string}")
    public void lePlacementRefuseCar(String reason) {
        assertFalse(lastPlacementAccepted, "Le placement aurait du etre refuse");
        assertEquals(reason, lastError);
    }

    @Then("le joueur recoit un jeton feu de valeur {int}")
    public void leJoueurRecoitUnJetonFeu(int expectedValue) {
        assertNotNull(currentTile);
        List<FireToken> tokens = new BoardService().collectFireTokens(currentTile);
        assertEquals(1, tokens.size());
        assertEquals(expectedValue, tokens.get(0).getCount());
    }

    @Then("le joueur ne recoit aucun jeton feu")
    public void leJoueurNeRecoitAucunJetonFeu() {
        assertNotNull(currentTile);
        List<FireToken> tokens = new BoardService().collectFireTokens(currentTile);
        assertTrue(tokens.isEmpty());
    }

    @Then("le jeton feu {int} a une portee de {int}")
    public void leJetonFeuAUnePorteeDe(int value, int expectedRange) {
        assertEquals(expectedRange, new FireToken(value).getRange());
    }

    @Then("le jeton feu est place en \\({int},{int}\\)")
    public void leJetonFeuEstPlaceEn(int x, int y) {
        assertTrue(world.kingdom.hasFireToken(new Position(x, y)));
    }

    @Then("le jeton feu ne peut pas etre place")
    public void leJetonFeuNePeutPasEtrePlace() {
        assertFalse(lastPlacementAccepted, "Le jeton feu aurait du etre refuse");
    }

    @Then("le jeton feu est defausse")
    public void leJetonFeuEstDefausse() {
        leJetonFeuNePeutPasEtrePlace();
    }

    private int volcanoFireCount(Terrain cell, Terrain other) {
        if (cell != Terrain.VOLCAN) return 0;
        return switch (other) {
            case LAC -> 1;
            case JUNGLE -> 2;
            case DESERT -> 3;
            default -> 0;
        };
    }

    private Terrain parseTerrain(String s) {
        return switch (s.toLowerCase()) {
            case "volcan" -> Terrain.VOLCAN;
            case "steppe" -> Terrain.STEPPE;
            case "lac" -> Terrain.LAC;
            case "jungle" -> Terrain.JUNGLE;
            case "carriere" -> Terrain.CARRIERE;
            case "desert" -> Terrain.DESERT;
            case "chateau" -> Terrain.CHATEAU;
            default -> throw new IllegalArgumentException("Terrain inconnu: " + s);
        };
    }

    private Position findAdjacentFreePosition(Position target) {
        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        for (int[] d : dirs) {
            Position p = new Position(target.x() + d[0], target.y() + d[1]);
            if (p.x() >= 0 && p.x() < Kingdom.SIZE && p.y() >= 0 && p.y() < Kingdom.SIZE
                && !world.kingdom.isOccupied(p)) {
                return p;
            }
        }
        return null;
    }

    private Position findAdjacentOccupied(Position target) {
        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        for (int[] d : dirs) {
            Position p = new Position(target.x() + d[0], target.y() + d[1]);
            if (p.x() >= 0 && p.x() < Kingdom.SIZE && p.y() >= 0 && p.y() < Kingdom.SIZE
                && world.kingdom.isOccupied(p)) {
                return p;
            }
        }
        return null;
    }
}
