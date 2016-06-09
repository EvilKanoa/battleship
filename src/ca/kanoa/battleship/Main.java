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
        // check if any server argument was passed in, if not, start the client
        if (args.length > 0 && args[1].contains("server")) {

        } else {
            try {
                // create and configure a container for the game
                AppGameContainer container = new AppGameContainer(new Battleship());
                container.setDisplayMode(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, false);
                container.setIcons(new String[]{"img/icon/16x16.tga", "img/icon/24x24.tga", "img/icon/32x32.tga"});
                container.setTargetFrameRate(Config.FPS);
                container.setShowFPS(Config.SHOW_FPS);
                // begin the game
                container.start();
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }
}
