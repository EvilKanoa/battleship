package ca.kanoa.battleship.states;

import ca.kanoa.battleship.Battleship;
import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.util.Timer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Splash extends BasicGameState {

    private Battleship battleship;
    private Image splashImage;
    private Timer timer;

    public Splash(Battleship battleship) {
        this.battleship = battleship;
    }

    @Override
    public int getID() {
        return Config.SCREEN_SPLASH ;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        splashImage = new Image("img/splash.tga");
        timer = new Timer(1500);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws
            SlickException {
        splashImage.draw(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (timer.check()) {
            battleship.enterState(Config.SCREEN_MAINMENU);
        }
    }
}
