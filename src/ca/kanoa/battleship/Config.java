package ca.kanoa.battleship;

/**
 * Static class used to store all global constants
 */
public class Config {

    // menu and state information
    public static final int SCREEN_SPLASH = 1;
    public static final int SCREEN_MAINMENU = 2;
    public static final int SCREEN_LOBBY = 3;
    public static final int SCREEN_GAME = 4;

    // window information
    public static final int WINDOW_WIDTH = 640;
    public static final int WINDOW_HEIGHT = 480;
    public static final int FPS = 60;
    public static final boolean SHOW_FPS = false;

    // network information
    public static final int NETWORK_PORT = 20001;
    public static final String GLOBAL_SERVER = "localhost";
    public static final long NETWORK_TIMEOUT = 5000;

    // packet IDs
    public static final byte PACKET_KEEP_ALIVE_ID = 0x00;
    public static final byte PACKET_USERNAME_ID = 0x01;
    public static final byte PACKET_LIST_PLAYERS = 0x02;
    public static final byte PACKET_GAME_REQUEST = 0x03;
    public static final byte PACKET_START_GAME = 0x04;

}
