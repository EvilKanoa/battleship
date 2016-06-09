package ca.kanoa.battleship;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * The main class for the game that will either start a new client or a new server
 */
public class Main {

    /**
     * The main entry point for the application
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        if (args[1].contains("server")) {

        } else {
            try {
                AppGameContainer container = new AppGameContainer(new Battleship());
                container.setDisplayMode(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, false);
                container.setTargetFrameRate(Config.FPS);
                container.setShowFPS(Config.SHOW_FPS);
                container.start();
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }
}
