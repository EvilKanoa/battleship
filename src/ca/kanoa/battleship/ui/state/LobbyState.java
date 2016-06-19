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

public class LobbyState extends BasicGameState implements ButtonListener {

    private Battleship battleship;
    private Image background;
    private String username;
    private TrueTypeFont text;
    private TrueTypeFont usernameText;
    private Button exitButton;
    private Button refreshButton;
    private Sound buttonSound;
    private Image requestButtonMain;
    private Image requestButtonHover;
    private List<String> onlinePlayers;
    private List<RequestButton> requestButtons;
    private Timer updateTimer;

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

        refreshButton.addListener(this);
        exitButton.addListener(this);

        onlinePlayers = new ArrayList<String>();
        requestButtons = new ArrayList<RequestButton>();

        updateTimer = new Timer(5000);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
            throws SlickException {
        background.draw(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        usernameText.drawString(Resize.width(78), 0, username, Color.gray);
        exitButton.render();
        refreshButton.render();
        for (RequestButton button : requestButtons) {
            button.render();
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        username = battleship.getNetwork().getUsername();
        exitButton.update();
        refreshButton.update();
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
    public void buttonPressed(String button, int mouseX, int mouseY) {
        if (button.equals("exit")) {
            System.exit(0);
        } else if (button.equals("refresh")) {
            battleship.getNetwork().refreshPlayers();
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