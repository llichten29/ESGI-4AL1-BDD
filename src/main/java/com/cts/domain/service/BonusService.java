package com.cts.domain.service;

import com.cts.domain.model.Kingdom;
import com.cts.domain.model.Position;

public class BonusService {

    public static final int EMPIRE_DU_FEU_POINTS = 10;
    public static final int HOMO_HABILIS_POINTS = 5;

    public int calculateEmpireDuFeu(Kingdom kingdom) {
        Position hut = Kingdom.STARTING_POSITION;
        if (kingdom.isOccupied(new Position(hut.x(), hut.y() + 1))
            && kingdom.isOccupied(new Position(hut.x(), hut.y() - 1))
            && kingdom.isOccupied(new Position(hut.x() - 1, hut.y()))
            && kingdom.isOccupied(new Position(hut.x() + 1, hut.y()))) {
            return EMPIRE_DU_FEU_POINTS;
        }
        return 0;
    }

    public int calculateHomoHabilis(Kingdom kingdom) {
        return kingdom.getCellCount() >= Kingdom.SIZE * Kingdom.SIZE
            ? HOMO_HABILIS_POINTS : 0;
    }
}
