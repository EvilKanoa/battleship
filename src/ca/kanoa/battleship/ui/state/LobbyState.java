package ca.kanoa.battleship.ui.state;

import ca.kanoa.battleship.Battleship;
import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.input.*;
import ca.kanoa.battleship.ui.RequestButton;
import ca.kanoa.battleship.util.Resize;
import ca.kanoa.battleship.util.Timer;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import javax.swing.*;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

//Creates class to control the lobby state
public class LobbyState extends BasicGameState implements ButtonListener {

    //creates variables for use in the program
    private Battleship battleship;
    private Image background;
    private String username;
    private TrueTypeFont text;
    private TrueTypeFont usernameText;
    private Button exitButton;
    private Button refreshButton;
    private Button aiButton;
    private Sound buttonSound;
    private Image requestButtonMain;
    private Image requestButtonHover;
    private List<String> onlinePlayers;
    private List<RequestButton> requestButtons;
    private Timer updateTimer;

    //sets the lobby state
    public LobbyState(Battleship battleship) {
        this.battleship = battleship;
    }

    @Override
    public int getID() { //gets the ID of the lobby state
        return Config.SCREEN_LOBBY;
    }

    @Override
    //Gets pictures and sounds for lobby state
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        background = new Image("img/lobby.tga");
        requestButtonMain = new Image("img/button/request.tga");
        requestButtonHover = new Image("img/button/request_hover.tga");
        buttonSound = new Sound("aud/click.wav");
        text = new TrueTypeFont(new Font("Helvetica", Font.PLAIN, 18), true);
        usernameText = new TrueTypeFont(new Font("TimesRoman", Font.BOLD, 12), true);

        exitButton = new Button("exit", new Image("img/button/exit.tga"),
                new Image("img/button/exit_hover.tga"), (int) Resize.width(920), 8, 64, 32, buttonSound);
        refreshButton = new Button("refresh", new Image("img/button/refresh.tga"),
                new Image("img/button/refresh_hover.tga"), Resize.width(348), Resize.height(177), Resize.width(40),
                Resize.height(40));
        aiButton = new Button("ai", new Image("img/button/ai.tga"), new Image("img/button/ai_hover.tga"),
                Resize.width(647), Resize.height(580));

        refreshButton.addListener(this);
        exitButton.addListener(this);
        aiButton.addListener(this);

        onlinePlayers = new ArrayList<String>();
        requestButtons = new ArrayList<RequestButton>();

        updateTimer = new Timer(5000);
    }

    @Override
    //renders the lobby state
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
            throws SlickException {
        background.draw(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        usernameText.drawString(Resize.width(78), 0, username, Color.gray);
        exitButton.render();
        refreshButton.render();
        aiButton.render();
        for (RequestButton button : requestButtons) {
            button.render();
        }
    }

    @Override
    //Updates the screen of the lobby state
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        username = battleship.getNetwork().getUsername();
        exitButton.update();
        refreshButton.update();
        aiButton.update();
        for (RequestButton button : requestButtons) {
            button.update();
            button.setRequested(battleship.getNetwork().getGameRequests().contains(button.getOpponent()));
        }

        if (updateTimer.check()) {
            updateTimer.reset();
            battleship.getNetwork().refreshPlayers();
        }

        onlinePlayers = battleship.getNetwork().getOnlinePlayers();
        if (onlinePlayers.size() != requestButtons.size()) {
            requestButtons.clear();
            RequestButton button;
            for (int i = 0; i < onlinePlayers.size(); i++) {
                button = new RequestButton(onlinePlayers.get(i), text, requestButtonMain, requestButtonHover,
                        Resize.width(415) - 43, Resize.height(233) + 45 * i);
                requestButtons.add(button);
                button.addListener(this);

            }
        }
    }

    @Override
    //Controls what happens when a button is pressed
    public void buttonPressed(String button, int mouseX, int mouseY) {
        if (button.equals("exit")) {
            System.exit(0);
        } else if (button.equals("refresh")) {
            battleship.getNetwork().refreshPlayers();
        } else if (button.equals("ai")) {
            battleship.getNetwork().requestAIGame();
        } else if (button.startsWith("request:")) {
            String opponent = button.substring(8);
            battleship.getNetwork().requestGame(opponent);
            if (battleship.getNetwork().getGameRequests().contains(opponent)) {
                JOptionPane.showMessageDialog(null, "Starting a game with " + opponent + "...");
            } else {
                JOptionPane.showMessageDialog(null, "A game request has been sent to " + opponent);
            }
        }
    }
}