package com.cts.framework;

import com.cts.domain.model.tile.Tile;
import java.util.List;

public class Utils {

    private Utils() {}

    public static boolean sameTileOrder(List<Tile> pileA, List<Tile> pileB) {
        if (pileA.size() != pileB.size()) {
            return false;
        }
        for (int i = 0; i < pileA.size(); i++) {
            if (pileA.get(i).getNumber() != pileB.get(i).getNumber()) {
                return false;
            }
        }
        return true;
    }
}
