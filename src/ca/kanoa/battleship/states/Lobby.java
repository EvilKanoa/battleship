package ca.kanoa.battleship.states;

import ca.kanoa.battleship.Battleship;
import ca.kanoa.battleship.Config;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by kanoa on 2016-06-09.
 */
public class Lobby extends BasicGameState {

    private Battleship battleship;

    public Lobby(Battleship battleship) {
        this.battleship = battleship;
    }

    @Override
    public int getID() {
        return Config.SCREEN_LOBBY;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }
}
