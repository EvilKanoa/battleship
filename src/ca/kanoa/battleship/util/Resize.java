package ca.kanoa.battleship.util;

import ca.kanoa.battleship.Config;

//Creates a class to resize the window
public class Resize {

    //createws a float for the width
    public static float width(float x) {
        double multi = Config.WINDOW_WIDTH / 1024d;
        return (float) (x * multi);
    }

    //creates a float for the hight
    public static float height(float y) {
        double multi = Config.WINDOW_HEIGHT / 768d;
        return (float) (y * multi);
    }

}
