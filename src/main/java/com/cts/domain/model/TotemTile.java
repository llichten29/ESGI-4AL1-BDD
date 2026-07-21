package com.cts.domain.model;

public class TotemTile {
    private final Resource resource;
    private final int points;
    private String owner;

    public TotemTile(Resource resource, int points) {
        this.resource = resource;
        this.points = points;
    }

    public Resource getResource() { return resource; }
    public int getPoints() { return points; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
}
