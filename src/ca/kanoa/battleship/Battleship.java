package ca.kanoa.battleship;

import ca.kanoa.battleship.states.Splash;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Battleship extends StateBasedGame {



    public Battleship() {
        super("BATTLESHIP");
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new Splash());
    }

}
