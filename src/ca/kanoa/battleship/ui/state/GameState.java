package ca.kanoa.battleship.ui.state;

import ca.kanoa.battleship.Battleship;
import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.game.Game;
import ca.kanoa.battleship.game.GameStatus;
import ca.kanoa.battleship.game.Ship;
import ca.kanoa.battleship.game.ShipType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameState extends BasicGameState {

    private Battleship battleship;
    private String opponent;
    private Game game;
    private Image background;
    private ShipType nextShip;
    private Ship imageShip;
    private Sound reject;
    private Music backgroundMusic;
    private boolean mouseDown;

    public GameState(Battleship battleship) {
        this.battleship = battleship;
    }

    @Override
    public int getID() {
        return Config.SCREEN_GAME;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        game = new Game();
        background = new Image("img/gameback.tga");
        nextShip = ShipType.getShipType(1);
        imageShip = new Ship(nextShip, 0, 0, false);
        backgroundMusic = new Music("aud/background.ogg");
        reject = new Sound("aud/reject.wav");
        mouseDown = false;
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
                break;
            case PLAYER_TWO_TURN:
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
                            game.setStatus(GameStatus.PLAYER_ONE_TURN);
                        } else {
                            imageShip = new Ship(nextShip, 0, 0, false);
                        }
                    } else {
                        reject.play();
                    }
                    mouseDown = true;
                } else if (!Mouse.isButtonDown(0)) {
                    mouseDown = false;
                }
                break;
            case PLAYER_ONE_TURN:
                break;
            case PLAYER_TWO_TURN:
                break;
            case GAME_OVER:
                break;
        }
    }

    private void drawCenterString(Graphics g, String s, float height) {
        g.drawString(s, (Config.WINDOW_WIDTH / 2) - (g.getFont().getWidth(s) / 2), height);
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }
}