package ca.kanoa.battleship.game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Marker extends Entity {

    private Image image;

    public Marker(boolean hit, int x, int y) {
        super(x, y);
        try {
            image = new Image(hit ? "img/marker/hit.tga" : "img/marker/miss.tga");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(int x, int y) {

    }
}
