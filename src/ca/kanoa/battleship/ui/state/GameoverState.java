package ca.kanoa.battleship.ui.state;

import ca.kanoa.battleship.Config;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Font;

//Creates a class for the ending state
public class GameoverState extends BasicGameState {

    //Creates variables for use in the program
    private boolean won = false;
    private TrueTypeFont text;

    @Override
    public int getID() {//gets the ID of the end screen
        return Config.SCREEN_GAMEOVER;
    }

    @Override
    //Sets the gameover screen up
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        text = new TrueTypeFont(new Font("Arial", Font.PLAIN, 36), true);
    }

    @Override
    //renders the gameover screen
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        String output;
        if (won) {
            output = "You won the game! :)";
        } else {
            output = "You lost the game. :(";
        }
        // draw the result in the middle of the screen
        text.drawString((Config.WINDOW_WIDTH / 2) - (text.getWidth(output) / 2),
                (Config.WINDOW_HEIGHT / 2) - (text.getHeight(output) / 2), output);
    }

    @Override
    //updates the gameover screen with the given information
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    //Sets who won the game
    public void setWon(boolean won) {
        this.won = won;
    }

}
