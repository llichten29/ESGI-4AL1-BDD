package com.cts.stepdefinitions.totem;

import com.cts.domain.model.board.FireToken;
import com.cts.domain.model.common.Position;
import com.cts.domain.model.common.Resource;
import com.cts.domain.model.player.PlayerColor;
import com.cts.domain.model.tile.Terrain;
import com.cts.domain.model.tile.Tile;
import com.cts.domain.model.tile.TileCell;
import com.cts.domain.service.board.BoardService;
import com.cts.domain.service.totem.TotemService;
import com.cts.stepdefinitions.WorldContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceSteps {
    private final WorldContext world;
    private final TotemService resourceService = new TotemService();

    public ResourceSteps(WorldContext world) {
        this.world = world;
    }

    @Given("une case {word} en \\({int},{int}\\) sans icone feu")
    public void uneCaseSansIconeFeu(String terrainStr, int x, int y) {
        Terrain terrain = WorldContext.parseTerrain(terrainStr);
        world.getKingdom().placeCell(new Position(x, y), new TileCell(terrain, 0));
    }

    @Given("une case {word} en \\({int},{int}\\) contenant une ressource {word}")
    public void uneCaseContenantUneRessource(String terrainStr, int x, int y, String resourceStr) {
        Terrain terrain = WorldContext.parseTerrain(terrainStr);
        Resource resource = WorldContext.parseResource(resourceStr);
        Position pos = new Position(x, y);
        world.getKingdom().placeCell(pos, new TileCell(terrain, 0, resource));
        addPlayerResource(PlayerColor.ROSE, resource, 1);
    }

    @Given("une case {word} en \\({int},{int}\\) sans icone feu contenant une ressource {word}")
    public void uneCaseSansIconeFeuContenantUneRessource(String terrainStr, int x, int y, String resourceStr) {
        uneCaseContenantUneRessource(terrainStr, x, y, resourceStr);
    }

    @Given("le joueur recoit un domino {word}-{word} avec une ressource {word}")
    public void leJoueurRecoitUnDominoAvecUneRessource(String t1, String t2, String resourceStr) {
        Resource resource = WorldContext.parseResource(resourceStr);
        Terrain terrainA = WorldContext.parseTerrain(t1);
        Terrain terrainB = WorldContext.parseTerrain(t2);
        TileCell cellA = new TileCell(terrainA, 0, resource);
        TileCell cellB = new TileCell(terrainB, 0, null);
        world.setCurrentTile(new Tile(0, cellA, cellB));
    }

    @Given("le joueur recoit un domino {word}-{word} avec {int} ressources {word}")
    public void leJoueurRecoitUnDominoAvecNRessources(String t1, String t2, int count, String resourceStr) {
        Resource resource = WorldContext.parseResource(resourceStr);
        Terrain terrainA = WorldContext.parseTerrain(t1);
        Terrain terrainB = WorldContext.parseTerrain(t2);
        TileCell cellA = new TileCell(terrainA, 0, count >= 1 ? resource : null);
        TileCell cellB = new TileCell(terrainB, 0, count >= 2 ? resource : null);
        world.setCurrentTile(new Tile(0, cellA, cellB));
    }

    @When("la ressource est placee sur la case \\({int},{int}\\)")
    public void laRessourceEstPlaceeSurLaCase(int x, int y) {
        resourceService.placeResourceOnCell(world.getKingdom(), new Position(x, y));
    }

    @When("le joueur pose le domino en \\({int},{int}\\) et \\({int},{int}\\)")
    public void leJoueurPoseLeDomino(int x1, int y1, int x2, int y2) {
        Tile tile = world.getCurrentTile();
        assertNotNull(tile, "Aucun domino prepare");
        new BoardService().place(world.getKingdom(), tile, new Position(x1, y1), new Position(x2, y2));
        Map<Resource, Integer> collected = resourceService.countResources(tile);
        Map<Resource, Integer> counts = world.getPlayerResources().computeIfAbsent(PlayerColor.ROSE, k -> new java.util.HashMap<>());
        for (Map.Entry<Resource, Integer> entry : collected.entrySet()) {
            counts.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
    }

    @When("le joueur projette le jeton feu de valeur {int} sur la case \\({int},{int}\\)")
    public void leJoueurProjetteLeJetonFeu(int value, int tx, int ty) {
        Position target = new Position(tx, ty);
        Position volcanoPos = findVolcano();
        assertNotNull(volcanoPos, "Aucun volcan dans le royaume");

        Resource destroyed = new BoardService().placeFireTokenAndDestroyResource(
            world.getKingdom(), target, volcanoPos, new FireToken(value));
        if (destroyed != null) {
            Map<Resource, Integer> counts = world.getPlayerResources().get(PlayerColor.ROSE);
            if (counts != null) {
                counts.put(destroyed, counts.getOrDefault(destroyed, 0) - 1);
            }
        }
    }

    @Then("la case \\({int},{int}\\) contient une ressource {word}")
    public void laCaseContientUneRessource(int x, int y, String resourceStr) {
        TileCell cell = world.getKingdom().getCell(new Position(x, y));
        assertNotNull(cell);
        assertNotNull(cell.getResource());
        assertEquals(WorldContext.parseResource(resourceStr), cell.getResource());
    }

    @Then("la case \\({int},{int}\\) ne contient aucune ressource")
    public void laCaseNeContientAucuneRessource(int x, int y) {
        TileCell cell = world.getKingdom().getCell(new Position(x, y));
        assertNotNull(cell);
        assertNull(cell.getResource());
    }

    @Then("{string} possede bien {int} ressource {word}")
    public void joueurPossedeRessourceSingulier(String playerRef, int expectedCount, String resourceStr) {
        assertPlayerResource(playerRef, expectedCount, resourceStr);
    }

    @Then("{string} possede bien {int} ressources {word}")
    public void joueurPossedeRessources(String playerRef, int expectedCount, String resourceStr) {
        assertPlayerResource(playerRef, expectedCount, resourceStr);
    }

    private void assertPlayerResource(String playerRef, int expectedCount, String resourceStr) {
        PlayerColor color = WorldContext.parsePlayerColor(playerRef);
        Resource resource = WorldContext.parseResource(resourceStr);
        Map<Resource, Integer> counts = world.getPlayerResources().get(color);
        int actual = counts != null ? counts.getOrDefault(resource, 0) : 0;
        assertEquals(expectedCount, actual);
    }

    private void addPlayerResource(PlayerColor player, Resource resource, int count) {
        Map<Resource, Integer> counts = world.getPlayerResources().computeIfAbsent(player, k -> new java.util.HashMap<>());
        counts.put(resource, counts.getOrDefault(resource, 0) + count);
    }

    private Position findVolcano() {
        for (Position pos : world.getKingdom().getOccupiedPositions()) {
            TileCell cell = world.getKingdom().getCell(pos);
            if (cell.getTerrain() == Terrain.VOLCAN) {
                return pos;
            }
        }
        return null;
    }

}
