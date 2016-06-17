package ca.kanoa.battleship.game;

import org.newdawn.slick.Image;

public class Ship extends Entity {

    private ShipType type;

    public Ship(ShipType type, int x, int y) {
        super(x, y);
        this.type = type;
    }

    @Override
    public Image getImage() {
        return null;
    }
}
