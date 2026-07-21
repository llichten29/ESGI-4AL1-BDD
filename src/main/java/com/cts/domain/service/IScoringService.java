package com.cts.domain.service;

import com.cts.domain.model.Kingdom;
import com.cts.domain.model.Region;
import com.cts.domain.model.ScoreResult;
import java.util.List;

public interface IScoringService {
    ScoreResult calculate(Kingdom kingdom);
    List<Region> findRegions(Kingdom kingdom);
    int compare(ScoreResult a, ScoreResult b);
}
