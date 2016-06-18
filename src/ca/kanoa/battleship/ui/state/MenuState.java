package ca.kanoa.battleship.ui.state;

import ca.kanoa.battleship.Battleship;
import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.input.Button;
import ca.kanoa.battleship.input.ButtonListener;
import ca.kanoa.battleship.network.BaseClient;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import javax.swing.*;

public class MenuState extends BasicGameState implements ButtonListener {

    private Battleship battleship;
    private Button connectButton;
    private Button exitButton;
    private Image background;
    private Sound buttonSound;

    public MenuState(Battleship battleship) {
        this.battleship = battleship;
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
            String username = JOptionPane.showInputDialog("Please enter a username (16 characters max)");
            if (username == null || username.length() == 0) {
                return;
            } else if (username.length() > 16) {
                username = username.substring(0, 16);
            }
            battleship.setNetwork(new BaseClient(Config.GLOBAL_SERVER));
            if (battleship.getNetwork().connect(username)) {
                battleship.enterState(Config.SCREEN_LOBBY);
            } else {
                JOptionPane.showMessageDialog(null, "Unable to connect...");
            }
        } else if (button.equals("exit")) {
            System.exit(0);
        }
    }
}