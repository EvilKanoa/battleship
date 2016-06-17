package ca.kanoa.battleship;

import ca.kanoa.battleship.input.Button;
import ca.kanoa.battleship.network.BaseServer;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.IOException;

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
        if (checkArgument(args, "server")) {
            // create and start a new server
            try {
                BaseServer server = new BaseServer();
                server.loop();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    /**
     * Searches through a list of arguments for a specified one
     * @param args The source arguments
     * @param target The argument to search for
     * @return Whther the specified argument exists
     */
    private static boolean checkArgument(String[] args, String target) {
        for (String arg : args) {
            arg = arg.toLowerCase();
            if (arg.equals(target) || arg.equals("-" + target) || arg.equals("/" + target)) {
                return true;
            }
        }
        return false;
    }
}
