package ca.kanoa.battleship.game;

import org.newdawn.slick.Image;

public class Marker extends Entity {

    private boolean hit;

    public Marker(boolean hit, int x, int y) {
        super(x, y);
        this.hit = hit;
    }

    @Override
    public Image getImage() {
        return null;
    }
}
