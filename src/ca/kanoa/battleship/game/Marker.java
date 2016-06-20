package ca.kanoa.battleship.game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

//Creates a class to control the markers
public class Marker extends Entity {

    //creates variables to use in the code
    private Image image = null;
    private boolean hit;

    //places the marker and checks for hit
    public Marker(boolean hit, int x, int y) {
        super(x, y);
        this.hit = hit;
    }

    @Override
    public void draw(float x, float y) {//Draws the marker
        if (image == null) {
            init();
        }
        image.draw(x, y);
    }

    @Override
    public void init() { //gets the pictures to be drawn
        if (image == null) {
            try {
                image = new Image(hit ? "img/marker/hit.tga" : "img/marker/miss.tga");
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isHit() {//checks if there is a hit
        return hit;
    }
}
