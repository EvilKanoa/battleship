package ca.kanoa.battleship.ui.state;

import ca.kanoa.battleship.Config;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Font;

public class GameoverState extends BasicGameState {

    private boolean won = false;
    private TrueTypeFont text;

    @Override
    public int getID() {
        return Config.SCREEN_GAMEOVER;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        text = new TrueTypeFont(new Font("Arial", Font.PLAIN, 36), true);
    }

    @Override
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
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    public void setWon(boolean won) {
        this.won = won;
    }

}
