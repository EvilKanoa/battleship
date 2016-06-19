package ca.kanoa.battleship.game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Marker extends Entity {

    private Image image = null;
    private boolean hit;

    public Marker(boolean hit, int x, int y) {
        super(x, y);
        this.hit = hit;
    }

    @Override
    public void draw(float x, float y) {
        if (image == null) {
            init();
        }
        image.draw(x, y);
    }

    @Override
    public void init() {
        if (image == null) {
            try {
                image = new Image(hit ? "img/marker/hit.tga" : "img/marker/miss.tga");
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isHit() {
        return hit;
    }
}
