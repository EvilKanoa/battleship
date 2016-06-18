package ca.kanoa.battleship.util;

import ca.kanoa.battleship.Config;

public class Resize {

    public static float locationX(float x) {
        double multi = Config.WINDOW_WIDTH / 1024d;
        return (float) (x * multi);
    }

    public static float locationY(float y) {
        double multi = Config.WINDOW_HEIGHT / 1024d;
        return (float) (y * multi);
    }

}
