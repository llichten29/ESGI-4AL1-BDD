package com.cts.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import java.util.logging.Logger;

public class Hooks {

    private static final Logger LOG = Logger.getLogger(Hooks.class.getName());

    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
        LOG.info("=== Scenario: " + scenario.getName() + " ===");
    }
}
