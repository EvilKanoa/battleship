package ca.kanoa.battleship.ui.state;

import ca.kanoa.battleship.Battleship;
import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.game.Map;
import ca.kanoa.battleship.game.Ship;
import ca.kanoa.battleship.game.ShipType;
import ca.kanoa.battleship.input.Button;
import ca.kanoa.battleship.input.ButtonListener;
import ca.kanoa.battleship.network.BaseClient;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import javax.swing.*;

//Creates a class for the menu state
public class MenuState extends BasicGameState implements ButtonListener {

    //creates variables for use in the menu
    private Battleship battleship;
    private Button connectButton;
    private Button exitButton;
    private Image background;
    private Sound buttonSound;

    //Creates the menu state
    public MenuState(Battleship battleship) {
        this.battleship = battleship;
    }

    @Override
    public int getID() {//gets the ID of the menu state
        return Config.SCREEN_MAINMENU;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException { //gets all pictures and sounds of the menu state
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
    //renders the menu state
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws
            SlickException {
        background.draw(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        connectButton.render();
        exitButton.render();
    }

    @Override
    //updates the menu state
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        connectButton.update();
        exitButton.update();
    }

    @Override
    //Checks if a button is pressed in the menu state
    public void buttonPressed(String button, int mouseX, int mouseY) {
        if (button.equals("connect")) {
            String username = JOptionPane.showInputDialog("Please enter a username (16 characters max)");
            if (username == null || username.length() == 0) {
                return;
            } else if (username.length() > 16) {
                username = username.substring(0, 16);
            }
            battleship.setNetwork(new BaseClient(Config.GLOBAL_SERVER, battleship));
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