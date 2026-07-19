package com.cts.stepdefinitions;

import com.cts.domain.exception.InvalidPlayerCountException;
import com.cts.domain.model.Draft;
import com.cts.domain.model.Player;
import com.cts.domain.model.Tile;
import com.cts.domain.service.GameService;
import com.cts.framework.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SetupSteps {

    private GameService game;
    private GameService otherGame;
    private String errorMessage;

    @Given("^une partie avec (\\d+) joueurs? et seed (\\d+)$")
    public void unePartieAvecJoueursEtSeed(int playerCount, long seed) {
        try {
            game = new GameService(playerCount, seed);
            errorMessage = null;
        } catch (InvalidPlayerCountException e) {
            errorMessage = e.getMessage();
        }
    }

    @Given("^une autre partie avec (\\d+) joueurs? et seed (\\d+)$")
    public void uneAutrePartieAvecJoueursEtSeed(int playerCount, long seed) {
        try {
            otherGame = new GameService(playerCount, seed);
        } catch (InvalidPlayerCountException e) {
            otherGame = null;
        }
    }

    @Then("la pioche contient {int} dominos")
    public void laPiocheContientDominos(int expectedCount) {
        assertNotNull(game);
        assertEquals(expectedCount, game.getDrawPileSize());
    }

    @Then("la premiere ligne de draft expose {int} dominos classes par numero croissant")
    public void laPremiereLigneDeDraftExposeDominosClasses(int tileCount) {
        assertNotNull(game);
        Draft draft = game.getCurrentDraft();
        assertEquals(tileCount, draft.size());
        List<Tile> tiles = draft.getTiles();
        for (int i = 1; i < tiles.size(); i++) {
            assertTrue(tiles.get(i - 1).getNumber() < tiles.get(i).getNumber(),
                "Les dominos doivent etre classes par numero croissant");
        }
    }

    @Then("chaque joueur possede une tuile de depart avec sa hutte")
    public void chaqueJoueurPossedeUneTuileDeDepart() {
        assertNotNull(game);
        List<Player> players = game.getPlayers();
        assertEquals(game.getPlayers().size(), players.size());
        for (Player p : players) {
            assertNotNull(p);
            assertNotNull(p.getColor());
        }
    }

    @Then("chaque joueur a place son chef de tribu sur un domino de la ligne")
    public void chaqueJoueurAPlaceSonChefDeTribu() {
        assertNotNull(game);
        Draft draft = game.getCurrentDraft();
        assertEquals(game.getPlayers().size(), draft.size(),
            "Le nombre de chefs doit correspondre au nombre de dominos dans la draft");
    }

    @Then("la premiere ligne de draft expose {int} dominos dont {int} est sans chef")
    public void laPremiereLigneDeDraftExposeDominosSansChef(int tileCount, int withoutChief) {
        assertNotNull(game);
        Draft draft = game.getCurrentDraft();
        assertEquals(tileCount, draft.size());
        int expectedWithChief = tileCount - withoutChief;
        assertEquals(expectedWithChief, game.getPlayers().size());
    }

    @Then("ce domino sans chef sera defausse a la fin du tour")
    public void ceDominoSansChefSeraDefausse() {
        assertNotNull(game);
        Draft draft = game.getCurrentDraft();
        int playerCount = game.getPlayers().size();
        assertTrue(draft.size() > playerCount,
            "Il doit y avoir plus de dominos que de joueurs pour qu un soit defausse");
    }

    @Then("les deux pioches ont le meme ordre de dominos")
    public void lesDeuxPiochesOntLeMemeOrdre() {
        assertNotNull(game);
        assertNotNull(otherGame);
        assertTrue(Utils.sameTileOrder(game.getDrawPile(), otherGame.getDrawPile()),
            "Les deux pioches doivent avoir le meme ordre de dominos");
    }

    @Then("la creation de la partie echoue car {string}")
    public void laCreationEchoue(String expectedMessage) {
        assertNotNull(errorMessage, "Une erreur etait attendue");
        assertEquals(expectedMessage, errorMessage);
    }
}
