package ca.kanoa.battleship.input;

import ca.kanoa.battleship.Config;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;

import java.util.HashSet;
import java.util.Set;

public class Button {

    private Set<ButtonListener> listeners;
    private final String id;
    private Image main, hover;
    private Sound sound;
    int x, y, width, height;
    private boolean mouseOver;
    private boolean enable;
    private boolean wasPressed;

    public Button(String buttonID, Image main, Image hover, int x, int y, int width, int height) {
        this.id = buttonID;
        this.main = main;
        this.hover = hover;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mouseOver = false;
        this.wasPressed = false;
        this.listeners = new HashSet<ButtonListener>();
        this.sound = null;
        enable();
    }

    public Button(String buttonID, Image main, Image hover, int x, int y, int width, int height, Sound sound) {
        this(buttonID, main, hover, x, y, width, height);
        this.sound = sound;
    }

    public void enable() {
        this.enable = true;
    }

    public void disable() {
        this.enable = false;
    }

    public String getId() {
        return id;
    }

    public void render() {
        if (enable && mouseOver) {
            hover.draw(x, y, width, height);
        } else if (enable) {
            main.draw(x, y, width, height);
        }
    }

    public void addListener(ButtonListener listener) {
        listeners.add(listener);
    }

    public boolean removeListener(ButtonListener listener) {
        return listeners.remove(listener);
    }

    public void update() {
        int xPos = Mouse.getX();
        int yPos = Config.WINDOW_HEIGHT - Mouse.getY();
        if (xPos >= x && xPos <= (x + width) && yPos >= y && yPos <= (y + height)) {
            // check if the mouse recently moved over for sounds
            if (!mouseOver && sound != null && !sound.playing()) {
                sound.play();
            }
            // set the current state
            mouseOver = true;
            // check if the button has been pressed
            if (enable && !Mouse.isButtonDown(0) && wasPressed) {
                // call the listeners
                for (ButtonListener listener : listeners) {
                    listener.buttonPressed(getId(), xPos, yPos);
                }
            }
        } else {
            mouseOver = false;
        }
        wasPressed = Mouse.isButtonDown(0);
    }

}
