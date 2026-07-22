package com.cts.domain.model.player;

import java.util.HashMap;
import java.util.Map;

public enum PlayerColor {
    ROSE("rose"),
    NOIR("noir"),
    VERT("vert"),
    BLEU("bleu");

    private final String nom;

    PlayerColor(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public static PlayerColor fromNom(String nom) {
        for (PlayerColor c : values()) {
            if (c.nom.equals(nom)) return c;
        }
        throw new IllegalArgumentException("Couleur inconnue: " + nom);
    }

    private static final Map<String, PlayerColor> NAME_MAP = new HashMap<>();
    static {
        NAME_MAP.put("Alice", ROSE);
        NAME_MAP.put("Bastien", NOIR);
        NAME_MAP.put("Camille", VERT);
        NAME_MAP.put("David", BLEU);
    }

    public static PlayerColor forPlayerName(String name) {
        PlayerColor c = NAME_MAP.get(name);
        if (c == null) throw new IllegalArgumentException("Pas de couleur pour: " + name);
        return c;
    }
}
