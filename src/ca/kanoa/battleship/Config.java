package ca.kanoa.battleship;

/**
 * Static class used to store all global constants
 */
public class Config {

    // menu and state information
    public static final int SCREEN_SPLASH               = 1;
    public static final int SCREEN_MAINMENU             = 2;
    public static final int SCREEN_LOBBY                = 3;
    public static final int SCREEN_GAME                 = 4;
    public static final int SCREEN_GAMEOVER             = 5;
    public static final int SCREEN_LEADERBOARD          = 6;

    // misc game information
    public static final int MAP_SIZE                    = 10;
    public static final long GAME_SWITCH_VIEW_DELAY     = 2500; // TODO: Make this longer

    // window information
    public static final int WINDOW_WIDTH                = 640;
    public static final int WINDOW_HEIGHT               = 480;
    public static final int FPS                         = 60;
    public static final boolean SHOW_FPS                = false;

    // network information
    public static final int NETWORK_PORT                = 20001;
    public static final String GLOBAL_SERVER            = "99.250.178.197";
    public static final long NETWORK_TIMEOUT            = 5000;

    // packet IDs
    public static final byte PACKET_KEEP_ALIVE_ID       = 0x00;
    public static final byte PACKET_USERNAME_ID         = 0x01;
    public static final byte PACKET_LIST_PLAYERS_ID     = 0x02;
    public static final byte PACKET_GAME_REQUEST_ID     = 0x03;
    public static final byte PACKET_START_GAME_ID       = 0x04;
    public static final byte PACKET_READY_ID            = 0x05;
    public static final byte PACKET_PLAYER_ONE_ID       = 0x06;
    public static final byte PACKET_PLAYER_TWO_ID       = 0x07;
    public static final byte PACKET_SHIP_SUNK_ID        = 0x08;
    public static final byte PACKET_ATTACK_ID           = 0x09;
    public static final byte PACKET_RESULT_ID           = 0x0A;
    public static final byte PACKET_GAME_WON_ID         = 0x0B;
    public static final byte PACKET_LEADERBOARD_ID      = 0x0C;
    public static final byte PACKET_AI_REQUEST_ID       = 0x0D;

}
