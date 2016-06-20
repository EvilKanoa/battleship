package ca.kanoa.battleship.ui;

import ca.kanoa.battleship.input.Button;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

//Creates a request button to be used in the program
public class RequestButton extends Button {

    //creates variables to be used in the program
    private String opponent;
    private TrueTypeFont text;
    private boolean requested;

    //creates the request button
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

    //gets the opponent by clicking the button
    public String getOpponent() {
        return opponent;
    }

    //sets the clicked on opponent as requested
    public void setRequested(boolean requested) {
        this.requested = requested;
    }
}
