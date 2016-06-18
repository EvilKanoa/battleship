package ca.kanoa.battleship.ui;

import ca.kanoa.battleship.input.Button;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

public class RequestButton extends Button {

    private String opponent;
    private TrueTypeFont text;
    private boolean requested;

    public RequestButton(String opponent, TrueTypeFont text, Image main, Image hover, float x, float y) {
        super("request:" + opponent, main, hover, x, y);
        this.opponent = opponent;
        this.text = text;
    }

    @Override
    public void render() {
        // render the button
        super.render();

        // render the text
        text.drawString(x - text.getWidth(opponent) - 5, y + 7, opponent, requested ? Color.blue : Color.red);
    }

    public String getOpponent() {
        return opponent;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }
}
