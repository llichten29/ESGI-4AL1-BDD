package com.cts.domain.service;

import com.cts.domain.model.Resource;
import java.util.HashMap;
import java.util.Map;

public class TotemService {

    private static final int TOTEM_POINTS = 5;

    public int getTotemPoints() {
        return TOTEM_POINTS;
    }

    public Map<Resource, String> allocateTotems(
            Map<String, Map<Resource, Integer>> allPlayerResources,
            Map<Resource, String> currentOwners) {
        Map<Resource, String> result = new HashMap<>();
        for (Resource resource : Resource.values()) {
            String owner = findMajorityOwner(allPlayerResources, resource, currentOwners.get(resource));
            result.put(resource, owner);
        }
        return result;
    }

    private String findMajorityOwner(
            Map<String, Map<Resource, Integer>> allPlayerResources,
            Resource resource,
            String currentOwner) {
        String topPlayer = null;
        int topCount = 0;
        boolean tie = false;

        for (Map.Entry<String, Map<Resource, Integer>> entry : allPlayerResources.entrySet()) {
            int count = entry.getValue().getOrDefault(resource, 0);
            if (count > topCount) {
                topCount = count;
                topPlayer = entry.getKey();
                tie = false;
            } else if (count == topCount && count > 0) {
                tie = true;
            }
        }

        if (tie) return currentOwner;
        return topPlayer;
    }
}
