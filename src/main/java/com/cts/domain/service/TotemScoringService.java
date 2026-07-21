package com.cts.domain.service;

import com.cts.domain.model.Kingdom;
import com.cts.domain.model.Region;
import com.cts.domain.model.ScoreResult;
import java.util.List;

public class TotemScoringService implements IScoringService {

    private final DecouverteScoringService decouverte = new DecouverteScoringService();

    @Override
    public ScoreResult calculate(Kingdom kingdom) {
        return decouverte.calculate(kingdom);
    }

    @Override
    public List<Region> findRegions(Kingdom kingdom) {
        return decouverte.findRegions(kingdom);
    }

    @Override
    public int compare(ScoreResult a, ScoreResult b) {
        return decouverte.compare(a, b);
    }
}
