package com.cts.domain.model.board;

public class FireToken {
    private static final int MAX_RANGE = 4;
    private final int count;

    public FireToken(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int getRange() {
        return MAX_RANGE - count;
    }
}
