package ca.kanoa.battleship.ui;

import ca.kanoa.battleship.input.Button;
import org.newdawn.slick.Image;

public class CellButton extends Button {

    int posX, posY;

    public CellButton(Image hover, int posX, int posY) {
        super("cell:" + posX + "," + posY, null, hover, 0, 0, 39, 39);
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * This method should NOT be used for CellButtons. Use CellButton#update(float x, float y) insead.
     */
    @Override
    public void update() { }

    /**
     * Advanced update for cell buttons
     * @param x The x position of the grid
     * @param y The y position of the grid
     */
    public void update(float x, float y) {
        super.x = x + (posX * 40);
        super.y = y + (posY * 40);
        super.update();
    }

    @Override
    public void render() {
        if (enable && mouseOver) {
            hover.draw(x, y);
        }
    }

    public boolean selected() {
        return mouseOver;
    }

}
