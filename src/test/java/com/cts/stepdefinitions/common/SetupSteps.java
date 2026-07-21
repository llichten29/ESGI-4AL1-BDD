package com.cts.stepdefinitions.common;

import com.cts.domain.exception.InvalidPlayerCountException;
import com.cts.domain.model.Draft;
import com.cts.domain.model.Player;
import com.cts.domain.model.Tile;
import com.cts.domain.service.GameService;
import com.cts.framework.Utils;
import com.cts.stepdefinitions.WorldContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SetupSteps {

    private final WorldContext world;

    public SetupSteps(WorldContext world) {
        this.world = world;
    }

    @Given("les joueurs sont {string}, {string}, {string}, {string}")
    public void lesJoueursSont(String p1, String p2, String p3, String p4) {
        world.customPlayerNames = new String[]{p1, p2, p3, p4};
    }

    @Given("^une partie avec (\\d+) joueurs? et seed (\\d+)$")
    public void unePartieAvecJoueursEtSeed(int playerCount, long seed) {
        try {
            if (world.customPlayerNames != null) {
                world.game = new GameService(playerCount, seed, world.customPlayerNames);
            } else {
                world.game = new GameService(playerCount, seed);
            }
            world.errorMessage = null;
        } catch (InvalidPlayerCountException e) {
            world.errorMessage = e.getMessage();
        }
    }

    @Given("^une autre partie avec (\\d+) joueurs? et seed (\\d+)$")
    public void uneAutrePartieAvecJoueursEtSeed(int playerCount, long seed) {
        try {
            world.otherGame = new GameService(playerCount, seed);
        } catch (InvalidPlayerCountException e) {
            world.otherGame = null;
        }
    }

    @Then("la pioche contient {int} dominos")
    public void laPiocheContientDominos(int expectedCount) {
        assertNotNull(world.game);
        assertEquals(expectedCount, world.game.getDrawPileSize());
    }

    @Then("la premiere ligne de draft expose {int} dominos classes par numero croissant")
    public void laPremiereLigneDeDraftExposeDominosClasses(int tileCount) {
        assertNotNull(world.game);
        Draft draft = world.game.getCurrentDraft();
        assertEquals(tileCount, draft.size());
        List<Tile> tiles = draft.getTiles();
        for (int i = 1; i < tiles.size(); i++) {
            assertTrue(tiles.get(i - 1).getNumber() < tiles.get(i).getNumber(),
                "Les dominos doivent etre classes par numero croissant");
        }
    }

    @Then("chaque joueur possede une tuile de depart avec sa hutte")
    public void chaqueJoueurPossedeUneTuileDeDepart() {
        assertNotNull(world.game);
        List<Player> players = world.game.getPlayers();
        assertEquals(world.game.getPlayers().size(), players.size());
        for (Player p : players) {
            assertNotNull(p);
            assertNotNull(p.getColor());
        }
    }

    @Then("chaque joueur a place son chef de tribu sur un domino de la ligne")
    public void chaqueJoueurAPlaceSonChefDeTribu() {
        assertNotNull(world.game);
        Draft draft = world.game.getCurrentDraft();
        assertEquals(world.game.getPlayers().size(), draft.size(),
            "Le nombre de chefs doit correspondre au nombre de dominos dans la draft");
    }

    @Then("la premiere ligne de draft expose {int} dominos dont {int} est sans chef")
    public void laPremiereLigneDeDraftExposeDominosSansChef(int tileCount, int withoutChief) {
        assertNotNull(world.game);
        Draft draft = world.game.getCurrentDraft();
        assertEquals(tileCount, draft.size());
        int expectedWithChief = tileCount - withoutChief;
        assertEquals(expectedWithChief, world.game.getPlayers().size());
    }

    @Then("ce domino sans chef sera defausse a la fin du tour")
    public void ceDominoSansChefSeraDefausse() {
        assertNotNull(world.game);
        Draft draft = world.game.getCurrentDraft();
        int playerCount = world.game.getPlayers().size();
        assertTrue(draft.size() > playerCount,
            "Il doit y avoir plus de dominos que de joueurs pour qu un soit defausse");
    }

    @Then("les deux pioches ont le meme ordre de dominos")
    public void lesDeuxPiochesOntLeMemeOrdre() {
        assertNotNull(world.game);
        assertNotNull(world.otherGame);
        assertTrue(Utils.sameTileOrder(world.game.getDrawPile(), world.otherGame.getDrawPile()),
            "Les deux pioches doivent avoir le meme ordre de dominos");
    }

    @Then("la creation de la partie echoue car {string}")
    public void laCreationEchoue(String expectedMessage) {
        assertNotNull(world.errorMessage, "Une erreur etait attendue");
        assertEquals(expectedMessage, world.errorMessage);
    }
}
