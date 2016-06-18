package ca.kanoa.battleship.game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Ship extends Entity {

    private ShipType type;
    private boolean vertical;
    private Image image;

    public Ship(ShipType type, int x, int y, boolean vertical) {
        super(x, y, vertical ? 1 : type.getShipLength(), vertical ? type.getShipLength() : 1);
        this.type = type;
        this.vertical = vertical;
        try {
            image = new Image("img/ship/" + type.getName().toLowerCase() + ".tga");
            if (vertical) {
                image.setRotation(90);
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(int x, int y) {
        if (vertical) {
            image.setCenterOfRotation(0, 0);
            image.draw(x + image.getHeight(), y);
        } else {
            image.draw(x, y);
        }
    }
}
