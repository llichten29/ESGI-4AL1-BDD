package com.cts.stepdefinitions.totem;

import com.cts.domain.model.common.Resource;
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

    @Given("{word} possede {int} ressources {word}")
    public void joueurPossedeRessources(String player, int count, String resourceStr) {
        setPlayerResource(player, count, resourceStr);
    }

    @Given("{word} possede {int} ressource {word}")
    public void joueurPossedeRessourceSingulier(String player, int count, String resourceStr) {
        setPlayerResource(player, count, resourceStr);
    }

    private void setPlayerResource(String player, int count, String resourceStr) {
        Resource resource = parseResource(resourceStr);
        Map<Resource, Integer> counts = world.playerResources.computeIfAbsent(player, k -> new EnumMap<>(Resource.class));
        counts.put(resource, count);
    }

    @Given("aucun joueur ne possede de ressource {word}")
    public void aucunJoueurNePossedeDeRessource(String resourceStr) {
        Resource resource = parseResource(resourceStr);
        for (Map<Resource, Integer> counts : world.playerResources.values()) {
            counts.remove(resource);
        }
    }

    @Given("le totem {word} appartient a {word}")
    public void leTotemAppartientA(String resourceStr, String player) {
        world.totemOwners.put(parseResource(resourceStr), player);
    }

    @Given("{word} possede le totem {word}")
    public void joueurPossedeLeTotem(String player, String resourceStr) {
        leTotemAppartientA(resourceStr, player);
    }

    @When("les totems sont reevalues")
    public void lesTotemsSontReevalues() {
        Map<Resource, String> currentOwners = new EnumMap<>(world.totemOwners);
        world.totemOwners = totemService.allocateTotems(world.playerResources, currentOwners);
    }

    @Then("le totem {word} revient a {word}")
    public void leTotemRevientA(String resourceStr, String player) {
        String owner = world.totemOwners.get(parseResource(resourceStr));
        assertEquals(player, owner);
    }

    @Then("le totem {word} reste chez {word}")
    public void leTotemResteChez(String resourceStr, String player) {
        leTotemRevientA(resourceStr, player);
    }

    @Then("le totem {word} n est attribue a personne")
    public void leTotemNAttribueAPersonne(String resourceStr) {
        String owner = world.totemOwners.get(parseResource(resourceStr));
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
