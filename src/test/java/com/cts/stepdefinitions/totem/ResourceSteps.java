package com.cts.stepdefinitions.totem;

import com.cts.domain.model.FireToken;
import com.cts.domain.model.Kingdom;
import com.cts.domain.model.Position;
import com.cts.domain.model.Resource;
import com.cts.domain.model.Terrain;
import com.cts.domain.model.Tile;
import com.cts.domain.model.TileCell;
import com.cts.domain.service.PlacementService;
import com.cts.domain.service.ResourcePlacementService;
import com.cts.domain.service.VolcanoService;
import com.cts.stepdefinitions.WorldContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceSteps {
    private final WorldContext world;
    private final ResourcePlacementService resourceService = new ResourcePlacementService();

    public ResourceSteps(WorldContext world) {
        this.world = world;
    }

    @Given("une case {word} en \\({int},{int}\\) sans icone feu")
    public void uneCaseSansIconeFeu(String terrainStr, int x, int y) {
        Terrain terrain = parseTerrain(terrainStr);
        world.kingdom.placeCell(new Position(x, y), new TileCell(terrain, 0));
    }

    @Given("une case {word} en \\({int},{int}\\) contenant une ressource {word}")
    public void uneCaseContenantUneRessource(String terrainStr, int x, int y, String resourceStr) {
        Terrain terrain = parseTerrain(terrainStr);
        Resource resource = parseResource(resourceStr);
        Position pos = new Position(x, y);
        world.kingdom.placeCell(pos, new TileCell(terrain, 0, resource));
        addPlayerResource("Alice", resource, 1);
    }

    @Given("le joueur recoit un domino {word}-{word} avec une ressource {word}")
    public void leJoueurRecoitUnDominoAvecUneRessource(String t1, String t2, String resourceStr) {
        Resource resource = parseResource(resourceStr);
        Terrain terrainA = parseTerrain(t1);
        Terrain terrainB = parseTerrain(t2);
        TileCell cellA = new TileCell(terrainA, 0, resource);
        TileCell cellB = new TileCell(terrainB, 0, null);
        world.currentTile = new Tile(0, cellA, cellB);
    }

    @Given("le joueur recoit un domino {word}-{word} avec {int} ressources {word}")
    public void leJoueurRecoitUnDominoAvecNRessources(String t1, String t2, int count, String resourceStr) {
        Resource resource = parseResource(resourceStr);
        Terrain terrainA = parseTerrain(t1);
        Terrain terrainB = parseTerrain(t2);
        TileCell cellA = new TileCell(terrainA, 0, count >= 1 ? resource : null);
        TileCell cellB = new TileCell(terrainB, 0, count >= 2 ? resource : null);
        world.currentTile = new Tile(0, cellA, cellB);
    }

    @When("la ressource est placee sur la case \\({int},{int}\\)")
    public void laRessourceEstPlaceeSurLaCase(int x, int y) {
        resourceService.placeResourceOnCell(world.kingdom, new Position(x, y));
    }

    @When("le joueur pose le domino en \\({int},{int}\\) et \\({int},{int}\\)")
    public void leJoueurPoseLeDomino(int x1, int y1, int x2, int y2) {
        Tile tile = world.currentTile;
        assertNotNull(tile, "Aucun domino prepare");
        new PlacementService().place(world.kingdom, tile, new Position(x1, y1), new Position(x2, y2));
        collectResourcesFromTile(tile);
    }

    @When("le joueur projette le jeton feu de valeur {int} sur la case \\({int},{int}\\)")
    public void leJoueurProjetteLeJetonFeu(int value, int tx, int ty) {
        Position target = new Position(tx, ty);
        Position volcanoPos = findVolcano();
        assertNotNull(volcanoPos, "Aucun volcan dans le royaume");

        new VolcanoService().placeFireToken(world.kingdom, target, volcanoPos, new FireToken(value));

        Resource destroyed = resourceService.destroyResource(world.kingdom, target);
        if (destroyed != null) {
            Map<Resource, Integer> counts = world.playerResources.get("Alice");
            if (counts != null) {
                counts.put(destroyed, counts.getOrDefault(destroyed, 0) - 1);
            }
        }
    }

    @Then("la case \\({int},{int}\\) contient une ressource {word}")
    public void laCaseContientUneRessource(int x, int y, String resourceStr) {
        TileCell cell = world.kingdom.getCell(new Position(x, y));
        assertNotNull(cell);
        assertNotNull(cell.getResource());
        assertEquals(parseResource(resourceStr), cell.getResource());
    }

    @Then("la case \\({int},{int}\\) ne contient aucune ressource")
    public void laCaseNeContientAucuneRessource(int x, int y) {
        TileCell cell = world.kingdom.getCell(new Position(x, y));
        assertNotNull(cell);
        assertNull(cell.getResource());
    }

    @Then("{word} possede {int} ressource {word}")
    public void joueurPossedeRessourceSingulier(String playerName, int expectedCount, String resourceStr) {
        Resource resource = parseResource(resourceStr);
        Map<Resource, Integer> counts = world.playerResources.get(playerName);
        int actual = counts != null ? counts.getOrDefault(resource, 0) : 0;
        assertEquals(expectedCount, actual);
    }

    @Then("{word} possede {int} ressources {word}")
    public void joueurPossedeRessourcesPluriel(String playerName, int expectedCount, String resourceStr) {
        joueurPossedeRessourceSingulier(playerName, expectedCount, resourceStr);
    }

    private void collectResourcesFromTile(Tile tile) {
        Map<Resource, Integer> counts = world.playerResources.computeIfAbsent("Alice", k -> new java.util.HashMap<>());
        for (TileCell cell : new TileCell[]{tile.getCellA(), tile.getCellB()}) {
            if (cell.getResource() != null) {
                counts.put(cell.getResource(), counts.getOrDefault(cell.getResource(), 0) + 1);
            }
        }
    }

    private void addPlayerResource(String player, Resource resource, int count) {
        Map<Resource, Integer> counts = world.playerResources.computeIfAbsent(player, k -> new java.util.HashMap<>());
        counts.put(resource, counts.getOrDefault(resource, 0) + count);
    }

    private Position findVolcano() {
        for (Position pos : world.kingdom.getOccupiedPositions()) {
            TileCell cell = world.kingdom.getCell(pos);
            if (cell.getTerrain() == Terrain.VOLCAN) {
                return pos;
            }
        }
        return null;
    }

    private Resource parseResource(String s) {
        return switch (s.toLowerCase()) {
            case "mammouth" -> Resource.MAMMOUTH;
            case "poisson" -> Resource.POISSON;
            case "champignon" -> Resource.CHAMPIGNON;
            case "silex" -> Resource.SILEX;
            default -> throw new IllegalArgumentException("Ressource inconnue: " + s);
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
}
