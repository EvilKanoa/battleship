package ca.kanoa.battleship.states;

import ca.kanoa.battleship.Battleship;
import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.input.Button;
import ca.kanoa.battleship.input.ButtonListener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by kanoa on 2016-06-09.
 */
public class Menu extends BasicGameState implements ButtonListener {

    private Battleship battleship;
    private Button testButton;

    public Menu(Battleship battleship) {
        this.battleship = battleship;
    }

    @Override
    public int getID() {
        return Config.SCREEN_MAINMENU;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        testButton = new Button("test button", new Image("img/splash.tga"), new Image("img/icon/32x32.tga"), 100, 100, 100, 100);
        testButton.addListener(this);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        testButton.render();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        testButton.update();
    }

    @Override
    public void buttonPressed(String button, int mouseX, int mouseY) {
        System.out.println(button + " was pressed!");
    }
}
