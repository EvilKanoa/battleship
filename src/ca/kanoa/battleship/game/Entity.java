package ca.kanoa.battleship.game;

import org.newdawn.slick.Image;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents any entity that is available to be placed on the game board.
 * Coordinates are from the top left.
 */
public abstract class Entity {

    //Creates variables for use in program
    private int posX;
    private int posY;
    private int width;
    private int height;

    //Creates the position of the entity
    protected Entity(int x, int y, int width, int height) {
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
    }

    //Places the entity
    protected Entity(int x, int y) {
        this(x, y, 1, 1);
    }

    //Sets the width of the entity
    public void setWidth(int width) {
        this.width = width;
    }

    //Sets the hight of the entity
    public void setHeight(int height) {
        this.height = height;
    }

    //gets x position of entity
    public int getX() {
        return posX;
    }

    //gets y position of entity
    public int getY() {
        return posY;
    }

    //sets y position of entity
    public void setY(int posY) {
        this.posY = posY;
    }

    //sets x position of entity
    public void setX(int posX) {
        this.posX = posX;
    }

    /**
     * Check if a grid location collides with this entity
     * @param x The x coordinate
     * @param y The y coordinate
     * @return Whether the location collides with this entity
     */
    public boolean collision(int x, int y) {
        boolean xCollision = (x >= posX) && (x < posX + width);
        boolean yCollision = (y >= posY) && (y < posY + height);
        boolean collision = xCollision && yCollision;
        return collision;
    }

    //Gest the occupied spaces of the entity
    public List<Integer[]> getOccupiedSpaces() {
        List<Integer[]> spaces = new LinkedList<Integer[]>();
        for (int x = posX; x < posX + width; x++) {
            for (int y = posY; y < posY + height; y++) {
                spaces.add(new Integer[]{x, y});
            }
        }
        return spaces;
    }

    //deraws the entity
    public abstract void draw(float x, float y);

    public abstract void init();

}
