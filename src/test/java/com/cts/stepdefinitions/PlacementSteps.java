package com.cts.stepdefinitions;

import com.cts.domain.exception.InvalidPlacementException;
import com.cts.domain.model.Kingdom;
import com.cts.domain.model.Position;
import com.cts.domain.model.Terrain;
import com.cts.domain.model.Tile;
import com.cts.domain.model.TileCell;
import com.cts.domain.service.PlacementService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PlacementSteps {

    private Kingdom kingdom;
    private Tile currentTile;
    private boolean lastPlacementAccepted;
    private String lastError;
    private List<Position[]> validPositions;

    @Given("un joueur {word} avec la tuile depart en position \\({int},{int}\\)")
    public void unJoueurAvecTuileDepart(String color, int x, int y) {
        kingdom = new Kingdom();
        assertEquals(Terrain.CHATEAU, kingdom.getCell(new Position(x, y)).getTerrain());
    }

    @Given("le joueur a deja pose une {word} en \\({int},{int}\\)")
    public void leJoueurADejaPose(String terrainStr, int x, int y) {
        Terrain terrain = parseTerrain(terrainStr);
        Position target = new Position(x, y);

        if (kingdom.isOccupied(target)) return;

        for (Position occupied : kingdom.getOccupiedPositions()) {
            if (target.isAdjacent(occupied)) {
                Position other = findAdjacentFreePosition(target);
                if (other != null) {
                    Tile tile = new Tile(0, new TileCell(terrain, 0), new TileCell(terrain, 0));
                    if (target.isAdjacent(other)) {
                        new PlacementService().place(kingdom, tile, target, other);
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
            new PlacementService().place(kingdom, tile, adj, target);
        }
    }

    @When("le joueur pose un domino {word}-{word} en \\({int},{int}\\) et \\({int},{int}\\)")
    public void leJoueurPoseUnDomino(String t1, String t2, int x1, int y1, int x2, int y2) {
        Terrain terrainA = parseTerrain(t1);
        Terrain terrainB = parseTerrain(t2);
        currentTile = new Tile(0, new TileCell(terrainA, 0), new TileCell(terrainB, 0));
        Position posA = new Position(x1, y1);
        Position posB = new Position(x2, y2);

        try {
            new PlacementService().place(kingdom, currentTile, posA, posB);
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
            new PlacementService().place(kingdom, currentTile, posA, posB);
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
        validPositions = new PlacementService().findValidPlacements(kingdom, currentTile);
    }

    @When("le joueur recoit un domino {word}-{word}")
    public void leJoueurRecoitUnDomino(String t1, String t2) {
        Terrain terrainA = parseTerrain(t1);
        Terrain terrainB = parseTerrain(t2);
        currentTile = new Tile(0, new TileCell(terrainA, 0), new TileCell(terrainB, 0));
    }

    @Then("le placement est accepte")
    public void lePlacementEstAccepte() {
        assertTrue(lastPlacementAccepted, "Placement refusé: " + (lastError != null ? lastError : ""));
    }

    @Then("le territoire contient {int} cases")
    public void leTerritoireContientCases(int count) {
        assertEquals(count, kingdom.getCellCount());
    }

    @Then("plusieurs positions sont proposees")
    public void plusieursPositionsProposees() {
        assertNotNull(validPositions);
        assertTrue(validPositions.size() >= 2, "Au moins 2 positions valides attendues, mais " + validPositions.size() + " trouvees");
    }

    @Then("le domino ne peut pas etre place")
    public void leDominoNePeutPasEtrePlace() {
        assertNotNull(currentTile);
        boolean hasAny = new PlacementService().hasAnyPlacement(kingdom, currentTile);
        assertFalse(hasAny, "Le domino peut etre place alors qu il devrait etre impossible");
    }

    @Then("le domino est defausse et ne rapporte aucun point")
    public void leDominoEstDefausse() {
        leDominoNePeutPasEtrePlace();
    }

    @Then("le joueur est oblige de le poser sur une position valide")
    public void leJoueurObligeDePoser() {
        assertNotNull(currentTile);
        boolean hasAny = new PlacementService().hasAnyPlacement(kingdom, currentTile);
        assertTrue(hasAny, "Le domino n a aucune position valide, mais devrait en avoir au moins une");
        assertDoesNotThrow(() -> {
            List<Position[]> positions = new PlacementService().findValidPlacements(kingdom, currentTile);
            assertFalse(positions.isEmpty());
            new PlacementService().place(kingdom, currentTile, positions.get(0)[0], positions.get(0)[1]);
        });
    }

    @Then("le placement est refuse car {string}")
    public void lePlacementRefuseCar(String reason) {
        assertFalse(lastPlacementAccepted, "Le placement aurait du etre refuse");
        assertEquals(reason, lastError);
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
                && !kingdom.isOccupied(p)) {
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
                && kingdom.isOccupied(p)) {
                return p;
            }
        }
        return null;
    }
}
