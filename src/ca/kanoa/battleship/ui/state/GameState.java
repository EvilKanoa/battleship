package ca.kanoa.battleship.ui.state;

import ca.kanoa.battleship.Battleship;
import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.game.*;
import ca.kanoa.battleship.game.Game;
import ca.kanoa.battleship.input.ButtonListener;
import ca.kanoa.battleship.util.Timer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameState extends BasicGameState implements ButtonListener {

    private Battleship battleship;
    private String opponent;
    private Game game;
    private Image background;
    private ShipType nextShip;
    private Ship imageShip;
    private Sound reject;
    private Music backgroundMusic;
    private boolean mouseDown;
    private boolean attacked;
    private boolean switching;
    private Timer buttonProtect;
    private Timer switchTimer;
    private String status;
    private int winner;

    public GameState(Battleship battleship) {
        this.battleship = battleship;
        this.attacked = false;
        this.switching = false;
        this.buttonProtect = new Timer(500);
        this.switchTimer = new Timer(Config.GAME_SWITCH_VIEW_DELAY);
        this.winner = -1;
    }

    @Override
    public int getID() {
        return Config.SCREEN_GAME;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        game = new Game(this);
        background = new Image("img/gameback.tga");
        nextShip = ShipType.getShipType(1);
        backgroundMusic = new Music("aud/background.ogg");
        reject = new Sound("aud/reject.wav");
        mouseDown = false;
        status = "Choose your target!";
        imageShip = new Ship(nextShip, 0, 0, false);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
            throws SlickException {
        background.draw(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        game.render();
        switch (game.getStatus()) {
            case PLACE_SHIPS:
                drawCenterString(graphics, "Place your " + nextShip.getName() + "! (Hold space to rotate)",
                        Config.WINDOW_HEIGHT - 30);
                imageShip.draw(Mouse.getX() - 20, Config.WINDOW_HEIGHT - Mouse.getY() - 20);
                break;
            case PLAYER_ONE_TURN:
            case PLAYER_TWO_TURN:
                if (game.myTurn()) {
                    drawCenterString(graphics, status, 10);
                } else if (game.getMyPlayer() == -1) {
                    drawCenterString(graphics, "Waiting on " + opponent + "...", Config.WINDOW_HEIGHT - 30);
                } else {
                    drawCenterString(graphics, "It is " + opponent + "'s turn", Config.WINDOW_HEIGHT - 30);
                }
                break;
            case GAME_OVER:
                break;
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (!backgroundMusic.playing()) {
            backgroundMusic.loop();
        }

        game.update();
        switch (game.getStatus()) {
            case PLACE_SHIPS:
                game.setMiddle(Config.WINDOW_HEIGHT / 2 - 210);
                if (imageShip.getType() != nextShip) {
                    imageShip = new Ship(nextShip, 0, 0, false);
                }

                imageShip.setVertical(Keyboard.isKeyDown(Keyboard.KEY_SPACE));
                imageShip.setX(game.getMyMap().getCursorX());
                imageShip.setY(game.getMyMap().getCursorY());
                if (Mouse.isButtonDown(0) && !mouseDown && imageShip.getY() >= 0 && imageShip.getX() >= 0) {
                    // check if the placement would be valid
                    if (game.getMyMap().validPlacement(imageShip)) {
                        // place the ship and move onto the next
                        game.getMyMap().place(imageShip);
                        nextShip = ShipType.getShipType(nextShip.getShipID() + 1);
                        if (nextShip == null) {
                            battleship.getNetwork().readyUp();
                            game.setStatus(GameStatus.PLAYER_ONE_TURN);
                        } else {
                            imageShip = new Ship(nextShip, 0, 0, false);
                        }
                        buttonProtect.reset();
                    } else {
                        reject.play();
                    }
                    mouseDown = true;
                } else if (!Mouse.isButtonDown(0)) {
                    mouseDown = false;
                }
                break;
            case PLAYER_ONE_TURN:
            case PLAYER_TWO_TURN:
                game.setMiddle(game.myTurn() ? Config.WINDOW_HEIGHT / 2 + 210 : Config.WINDOW_HEIGHT / 2 - 210);
                if (switching && switchTimer.check()) {
                    if (game.getStatus() == GameStatus.PLAYER_ONE_TURN) {
                        game.setStatus(GameStatus.PLAYER_TWO_TURN);
                    } else if (game.getStatus() == GameStatus.PLAYER_TWO_TURN) {
                        game.setStatus(GameStatus.PLAYER_ONE_TURN);
                    }
                    if (game.myTurn()) {
                        status = "Choose your target!";
                    }
                    attacked = false;
                    switching = false;
                }
                break;
            case GAME_OVER:
                if (winner != -1) {
                    battleship.gameoverState.setWon(winner == game.getMyPlayer());
                    battleship.enterState(Config.SCREEN_GAMEOVER);
                }
                break;
        }
    }

    private void drawCenterString(Graphics g, String s, float height) {
        g.drawString(s, (Config.WINDOW_WIDTH / 2) - (g.getFont().getWidth(s) / 2), height);
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public Game getGame() {
        return game;
    }

    public void attackResult(int x, int y, boolean result) {
        game.getTheirMap().place(new Marker(result, x, y));
        if (result && status.equals("Firing!")) {
            status = "You hit a ship!";
        } else if (status.equals("Firing!")){
            status = "You missed! :(";
        }
        switching = true;
        switchTimer.reset();
    }

    public void sunkShip(Ship ship) {
        game.getTheirMap().place(ship);
        status = "You sunk " + opponent + "'s " + ship.getType().getName();
    }

    public boolean attack(int x, int y) {
        boolean hit = game.attack(x, y);

        switching = true;
        switchTimer.reset();
        return hit;
    }

    @Override
    public void buttonPressed(String button, int mouseX, int mouseY) {
        if (buttonProtect.check()) {
            if (button.startsWith("theirmap:cell:") && game.myTurn() && !attacked) {
                int x = Integer.parseInt(button.substring(14).split(",")[0]);
                int y = Integer.parseInt(button.substring(14).split(",")[1]);
                if (game.getTheirMap().getMarker(x, y) == null) {
                    battleship.getNetwork().attack(x, y);
                    status = "Firing!";
                    attacked = true;
                }
            }
            buttonProtect.reset();
        }
    }
}