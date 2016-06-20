package ca.kanoa.battleship.network;

import ca.kanoa.battleship.network.packet.*;
import org.newdawn.slick.SlickException;

//sets up a class for the network game
public interface NetworkGame {

    //gets the participating players
    boolean playerParticipating(ClientHandler player);

    //starts the game
    void startGame();

    //readys the players, initiates the game
    void readyUp();

    //has the code to determine when a ship is sunk
    void sunkenShip(ClientHandler responder, ShipSunkPacket packet);

    //gets the responce t the attack
    void attackResponse(ClientHandler responder, ResultPacket packet);

    //Gets the attack
    void attack(ClientHandler attacker, AttackPacket packet);

    //gats player one
    ClientHandler getPlayerOne();

    //gets player two
    ClientHandler getPlayerTwo();

}
