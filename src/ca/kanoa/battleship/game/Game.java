package ca.kanoa.battleship.game;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.input.ButtonListener;
import org.newdawn.slick.SlickException;

public class Game {

    private Map myMap;
    private Map theirMap;
    private int myPlayer;
    private GameStatus status;
    private float middle;

    public Game(ButtonListener buttonListener) throws SlickException {
        status = GameStatus.PLACE_SHIPS;
        middle = Config.WINDOW_HEIGHT / 2;
        myMap = new Map("mymap", buttonListener);
        theirMap = new Map("theirmap", buttonListener);
        myMap.setActive(false);
        theirMap.setActive(false);
        myPlayer = -1;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Map getMyMap() {
        return myMap;
    }

    public Map getTheirMap() {
        return theirMap;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void render() {
        float x = Config.WINDOW_WIDTH / 2 - 200;
        myMap.render(x, middle + 10);
        theirMap.render(x, middle - 410);
    }

    public void update() {
        float x = Config.WINDOW_WIDTH / 2 - 200;
        myMap.update(x, middle + 10);
        theirMap.update(x, middle - 410);
    }

    public void setMyPlayer(int player) {
        this.myPlayer = player;
    }

    public int getMyPlayer() {
        return myPlayer;
    }

    public boolean myTurn() {
        return status.getPlayer() == myPlayer;
    }

    public void setMiddle(float middle) {
        this.middle = middle;
    }
}
