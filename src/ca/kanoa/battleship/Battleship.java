package ca.kanoa.battleship;

//imports the data from the other files
import ca.kanoa.battleship.network.BaseClient;
import ca.kanoa.battleship.ui.state.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Creates a class to start the program
 */
public class Battleship extends StateBasedGame {

    //Creates states for use in the program
    public final GameState gameState;
    public final LobbyState lobbyState;
    public final MenuState menuState;
    public final SplashState splashState;
    public final GameoverState gameoverState;
    private BaseClient networkClient;

    //Creates a method to activate the main states
    public Battleship() {
        super("BATTLESHIP");//Prints the super BATTLESHIP

        //Creates the game states
        gameState = new GameState(this);
        lobbyState = new LobbyState(this);
        menuState = new MenuState(this);
        splashState = new SplashState(this);
        gameoverState = new GameoverState();
    }

    @Override
    //Method to add the game states
    public void initStatesList(GameContainer gc) throws SlickException {
        //adds the game states
        this.addState(splashState);
        this.addState(gameState);
        this.addState(lobbyState);
        this.addState(menuState);
        this.addState(gameoverState);
    }

    //Method to search for the network
    public BaseClient getNetwork() {
        //searches for network
        return this.networkClient;
    }

    //Method to set the network for the duration of the game
    public void setNetwork(BaseClient client) {
        //Sets the network
        this.networkClient = client;
    }

}