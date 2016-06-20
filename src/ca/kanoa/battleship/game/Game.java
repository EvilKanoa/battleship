package ca.kanoa.battleship.game;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.input.ButtonListener;
import org.newdawn.slick.SlickException;

//Creates the main game class
public class Game {

    //Creates variables for use
    private Map myMap;
    private Map theirMap;
    private int myPlayer;
    private GameStatus status;
    private float middle;

    //Sets up the game
    public Game(ButtonListener buttonListener, boolean graphical) throws SlickException {
        status = GameStatus.PLACE_SHIPS;
        middle = Config.WINDOW_HEIGHT / 2;
        myMap = new Map("mymap", buttonListener, graphical);
        theirMap = new Map("theirmap", buttonListener, graphical);
        myPlayer = -1;
    }

    //Sets a syncronized attack
    public synchronized boolean attack(int x, int y) {
        return myMap.hit(x, y);
    }

    //Sets the staus of the game
    public void setStatus(GameStatus status) {
        this.status = status;
    }

    //Gets the map
    public synchronized Map getMyMap() {
        return myMap;
    }

    //Gets the opponents map
    public synchronized Map getTheirMap() {
        return theirMap;
    }

    //Renders the window based on screensize
    public void render() {
        float x = Config.WINDOW_WIDTH / 2 - 200;
        myMap.render(x, middle + 10);
        theirMap.render(x, middle - 410);
    }

    //Updates the window
    public void update() {
        float x = Config.WINDOW_WIDTH / 2 - 200;
        myMap.update(x, middle + 10);
        theirMap.update(x, middle - 410);
    }

    //Sets the users player
    public void setMyPlayer(int player) {
        this.myPlayer = player;
    }

    //gets the opponents player
    public int getMyPlayer() {
        return myPlayer;
    }

    //Gets whos turn it is
    public boolean myTurn() {
        return status.getPlayer() == myPlayer;
    }

    //Sets a mddle for use in the program
    public void setMiddle(float middle) {
        this.middle = middle;
    }

    //Gets the status of the game
    public GameStatus getStatus() {
        return status;
    }
}
