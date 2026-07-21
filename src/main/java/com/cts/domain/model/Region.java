package com.cts.domain.model;

public record Region(Terrain terrain, int cellCount, int fireCount) {
    public int score() {
        return cellCount * fireCount;
    }
}
