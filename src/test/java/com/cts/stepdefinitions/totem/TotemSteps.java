package com.cts.stepdefinitions.totem;

import com.cts.domain.model.common.Resource;
import com.cts.domain.model.player.PlayerColor;
import com.cts.domain.service.totem.TotemService;
import com.cts.stepdefinitions.WorldContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.EnumMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class TotemSteps {
    private final WorldContext world;
    private final TotemService totemService = new TotemService();

    public TotemSteps(WorldContext world) {
        this.world = world;
    }

    @Given("{string} possede {int} ressources {word}")
    public void joueurPossedeRessources(String playerRef, int count, String resourceStr) {
        setPlayerResource(playerRef, count, resourceStr);
    }

    @Given("{string} possede {int} ressource {word}")
    public void joueurPossedeRessourceSingulier(String playerRef, int count, String resourceStr) {
        setPlayerResource(playerRef, count, resourceStr);
    }

    private void setPlayerResource(String playerRef, int count, String resourceStr) {
        PlayerColor color = WorldContext.parsePlayerColor(playerRef);
        Resource resource = parseResource(resourceStr);
        Map<Resource, Integer> counts = world.getPlayerResources().computeIfAbsent(color, k -> new EnumMap<>(Resource.class));
        counts.put(resource, count);
    }

    @Given("aucun joueur ne possede de ressource {word}")
    public void aucunJoueurNePossedeDeRessource(String resourceStr) {
        Resource resource = parseResource(resourceStr);
        for (Map<Resource, Integer> counts : world.getPlayerResources().values()) {
            counts.remove(resource);
        }
    }

    @Given("le totem {word} appartient a {string}")
    public void leTotemAppartientA(String resourceStr, String playerRef) {
        world.getTotemOwners().put(parseResource(resourceStr), WorldContext.parsePlayerColor(playerRef));
    }

    @Given("{string} possede le totem {word}")
    public void joueurPossedeLeTotem(String playerRef, String resourceStr) {
        leTotemAppartientA(resourceStr, playerRef);
    }

    @When("les totems sont reevalues")
    public void lesTotemsSontReevalues() {
        Map<Resource, PlayerColor> currentOwners = new EnumMap<>(world.getTotemOwners());
        world.getTotemOwners().clear();
        world.getTotemOwners().putAll(totemService.allocateTotems(world.getPlayerResources(), currentOwners));
    }

    @Then("le totem {word} revient a {string}")
    public void leTotemRevientA(String resourceStr, String playerRef) {
        PlayerColor owner = world.getTotemOwners().get(parseResource(resourceStr));
        assertEquals(WorldContext.parsePlayerColor(playerRef), owner);
    }

    @Then("le totem {word} reste chez {string}")
    public void leTotemResteChez(String resourceStr, String playerRef) {
        leTotemRevientA(resourceStr, playerRef);
    }

    @Then("le totem {word} n est attribue a personne")
    public void leTotemNAttribueAPersonne(String resourceStr) {
        PlayerColor owner = world.getTotemOwners().get(parseResource(resourceStr));
        assertNull(owner);
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
}
