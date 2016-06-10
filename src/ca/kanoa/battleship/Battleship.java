package ca.kanoa.battleship;

import ca.kanoa.battleship.input.Button;
import ca.kanoa.battleship.states.Game;
import ca.kanoa.battleship.states.Lobby;
import ca.kanoa.battleship.states.Menu;
import ca.kanoa.battleship.states.Splash;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Battleship extends StateBasedGame {

    protected final Game gameState;
    protected final Lobby lobbyState;
    protected final Menu menuState;
    protected final Splash splashState;

    public Battleship() {
        super("BATTLESHIP");

        gameState = new Game(this);
        lobbyState = new Lobby(this);
        menuState = new Menu(this);
        splashState = new Splash(this);
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(splashState);
        this.addState(gameState);
        this.addState(lobbyState);
        this.addState(menuState);
    }

}
