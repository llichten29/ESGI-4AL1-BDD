package com.cts.domain.model.common;

public record Position(int x, int y) {
    public boolean isAdjacent(Position other) {
        int dx = Math.abs(this.x - other.x);
        int dy = Math.abs(this.y - other.y);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }
}
