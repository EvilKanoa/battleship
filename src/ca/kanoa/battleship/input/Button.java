package ca.kanoa.battleship.input;

import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

import java.util.Set;

/**
 * Created by kanoa on 2016-06-09.
 */
public class Button implements MouseListener {

    private Set<ButtonListener> listeners;
    private final String id;

    public Button(String buttonID) {
        this.id = buttonID;
    }

    public String getId() {
        return id;
    }

    public void addListener(ButtonListener listener) {
        listeners.add(listener);
    }

    public boolean removeListener(ButtonListener listener) {
        return listeners.remove(listener);
    }

    @Override
    public void mouseReleased(int i, int i1, int i2) {

    }

    @Override
    public void mouseMoved(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mouseWheelMoved(int i) {}

    @Override
    public void mouseClicked(int i, int i1, int i2, int i3) {}

    @Override
    public void mousePressed(int i, int i1, int i2) {}

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {}

    @Override
    public void setInput(Input input) {}

    @Override
    public boolean isAcceptingInput() {
        return false;
    }

    @Override
    public void inputEnded() {}

    @Override
    public void inputStarted() {}

}
