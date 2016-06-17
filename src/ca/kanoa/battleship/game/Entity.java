package ca.kanoa.battleship.game;

import org.newdawn.slick.Image;

/**
 * Represents any entity that is available to be placed on the game board.
 * Coordinates are from the top left.
 */
public abstract class Entity {

    private int posX;
    private int posY;
    private int width;
    private int height;

    protected Entity(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    /**
     * Check if a grid location collides with this entity
     * @param x The x coordinate
     * @param y The y coordinate
     * @return Whether the location collides with this entity
     */
    public boolean collision(int x, int y) {
        return x >= posX && x <= posX + width && y >= posY && y <= posY + height;
    }

    public abstract Image getImage();

}
