package ca.kanoa.battleship.states;

import ca.kanoa.battleship.Battleship;
import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.input.Button;
import ca.kanoa.battleship.input.ButtonListener;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by kanoa on 2016-06-09.
 */
public class Menu extends BasicGameState implements ButtonListener {

    private Battleship battleship;
    private Button connectButton;
    private Button exitButton;
    private Image background;
    private Sound buttonSound;
    private boolean connecting;

    public Menu(Battleship battleship) {
        this.battleship = battleship;
        this.connecting = false;
    }

    @Override
    public int getID() {
        return Config.SCREEN_MAINMENU;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        background = new Image("img/menu.tga");
        buttonSound = new Sound("aud/click.wav");
        connectButton = new Button("connect", new Image("img/button/connect.tga"),
                new Image("img/button/connect_hover.tga"), 60, 50, 256, 64, buttonSound);
        exitButton = new Button("exit", new Image("img/button/exit.tga"),
                new Image("img/button/exit_hover.tga"), 60, 114, 128, 64, buttonSound);
        connectButton.addListener(this);
        exitButton.addListener(this);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws
            SlickException {
        background.draw(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        connectButton.render();
        exitButton.render();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        connectButton.update();
        exitButton.update();
    }

    @Override
    public void buttonPressed(String button, int mouseX, int mouseY) {
        if (button.equals("connect")) {
            // TODO: Connect to lobby
        } else if (button.equals("exit")) {
            System.exit(0);
        }
    }
}
