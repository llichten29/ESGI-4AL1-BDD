package com.cts.domain.model.board;

public class FireToken {
    private final int count;

    public FireToken(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int getRange() {
        return switch (count) {
            case 1 -> 3;
            case 2 -> 2;
            case 3 -> 1;
            default -> 0;
        };
    }
}
