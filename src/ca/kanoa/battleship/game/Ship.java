package ca.kanoa.battleship.game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

//Creates a clss for controling the ships
public class Ship extends Entity {

    //creates variables for use in the program
    private ShipType type;
    private boolean vertical;
    private Image image = null;

    //Gets the ship location
    public Ship(ShipType type, int x, int y, boolean vertical) {
        this(type, x, y, vertical, true);
    }

    //sets the ship position
    public Ship(ShipType type, int x, int y, boolean vertical, boolean initialize) {
        super(x, y, vertical ? 1 : type.getShipLength(), vertical ? type.getShipLength() : 1);
        this.type = type;
        this.vertical = vertical;
        if (initialize) {
            init();
        }
    }

    @Override
    public void init() {//s=gets the ship image
        if (image == null) {
            try {
                image = new Image("img/ship/" + type.getName().toLowerCase() + ".tga");
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(float x, float y) {//draws the ship
        if (image == null) {
            init();
        }
        image.setCenterOfRotation(0, 0);
        if (vertical) {
            image.setRotation(90);
            image.draw(x + image.getHeight(), y);
        } else {
            image.setRotation(0);
            image.draw(x, y);
        }
    }

    //checks if the ship is verical
    public boolean isVertical() {
        return vertical;
    }

    //gets the type of ship
    public ShipType getType() {
        return type;
    }

    //sets whether or not the ship is vertical
    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        setWidth(vertical ? 1 : type.getShipLength());
        setHeight(vertical ? type.getShipLength() : 1);
    }

}
