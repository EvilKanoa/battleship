package ca.kanoa.battleship.ui.state;

import ca.kanoa.battleship.Battleship;
import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.util.Timer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

//Creates a class for the backsplash
public class SplashState extends BasicGameState {

    //creates variables for use in the code
    private Battleship battleship;
    private Image splashImage;
    private Timer timer;

    //Sets the state of the splash
    public SplashState(Battleship battleship) {
        this.battleship = battleship;
    }

    @Override
    public int getID() { //gets the splash ID
        return Config.SCREEN_SPLASH ;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException { //gets the picture for the splash
        splashImage = new Image("img/splash.tga");
        timer = new Timer(1500);
    }

    @Override
    //renders the splash
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws
            SlickException {
        splashImage.draw(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
    }

    @Override
    //Updates the splash
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (timer.check()) {
            battleship.enterState(Config.SCREEN_MAINMENU);
        }
    }
}