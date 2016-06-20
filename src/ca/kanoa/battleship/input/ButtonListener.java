package ca.kanoa.battleship.input;

/**
 * Created by kanoa on 2016-06-09.
 */
//Creates class to listen for the Button being pressed
public interface ButtonListener {

    //Listens for the button being pressed and where the button is pressed
    void buttonPressed(String button, int mouseX, int mouseY);

}
