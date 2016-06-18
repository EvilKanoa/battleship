package ca.kanoa.battleship.states;

import ca.kanoa.battleship.Battleship;
import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.input.*;
import ca.kanoa.battleship.util.Resize;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import java.awt.Font;

public class LobbyState extends BasicGameState implements ButtonListener {

    private Battleship battleship;
    private Image background;
    private String username;
    private TrueTypeFont text;
    private TrueTypeFont usernameText;
    private Button exitButton;
    private Sound buttonSound;

    public LobbyState(Battleship battleship) {
        this.battleship = battleship;
    }

    @Override
    public int getID() {
        return Config.SCREEN_LOBBY;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        background = new Image("img/lobby.tga");
        text = new TrueTypeFont(new Font("Helvetica", Font.BOLD, 18), true);
        usernameText = new TrueTypeFont(new Font("TimesRoman", Font.BOLD, 12), true);
        buttonSound = new Sound("aud/click.wav");
        exitButton = new Button("exit", new Image("img/button/exit.tga"),
                new Image("img/button/exit_hover.tga"), (int) Resize.locationX(920), 8, 64, 32, buttonSound);
        exitButton.addListener(this);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        usernameText.drawString(Resize.locationX(78), 0, username, Color.gray);
        exitButton.render();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        username = battleship.getNetwork().getUsername();
        exitButton.update();
    }

    @Override
    public void buttonPressed(String button, int mouseX, int mouseY) {
        if (button.equals("exit")) {
            System.exit(0);
        }
    }
}
