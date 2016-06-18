package ca.kanoa.battleship.util;

import ca.kanoa.battleship.Config;

public class Resize {

    public static float width(float x) {
        double multi = Config.WINDOW_WIDTH / 1024d;
        return (float) (x * multi);
    }

    public static float height(float y) {
        double multi = Config.WINDOW_HEIGHT / 768d;
        return (float) (y * multi);
    }

}
