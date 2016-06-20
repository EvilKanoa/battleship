package ca.kanoa.battleship.network;

import ca.kanoa.battleship.network.packet.*;
import org.newdawn.slick.SlickException;

//sets up a class for the network game
public class NetworkGame {

    //sets up variables for use in the program
    private ClientHandler playerOne;
    private ClientHandler playerTwo;
    private boolean[] playerOneShipsSunk, playerTwoShipsSunk;
    private NetworkGameStage stage;
    private BaseServer server;

    //Sets up the network game
    public NetworkGame(ClientHandler playerOne, ClientHandler playerTwo, BaseServer server) throws SlickException {
        this.server = server;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.stage = NetworkGameStage.NO_ONE_READY;
        this.playerOneShipsSunk = new boolean[]{false, false, false, false, false};
        this.playerTwoShipsSunk = new boolean[]{false, false, false, false, false};
    }

    //gets the participating players
    public boolean playerParticipating(ClientHandler player) {
        return player == playerOne || player == playerTwo;
    }

    //starts the game
    public void startGame() {
        // send the game packets
        playerOne.getPacketHandler().sendPacket(new StartGamePacket(playerTwo.getUsername()));
        playerTwo.getPacketHandler().sendPacket(new StartGamePacket(playerOne.getUsername()));
    }

    //readys the players, initiates the game
    public void readyUp() {
        if (stage.getID() > 1) {
            return;
        }
        stage = stage.nextStage();
        if (stage == NetworkGameStage.BOTH_PLAYERS_READY) {
            server.console(this, "initiating game");
            playerOne.getPacketHandler().sendPacket(new PlayerOnePacket());
            playerTwo.getPacketHandler().sendPacket(new PlayerTwoPacket());
            stage = NetworkGameStage.IN_GAME;
        }
    }

    //has the code to determine when a ship is sunk
    public void sunkenShip(ClientHandler responder, ShipSunkPacket packet) {
        if (responder == playerOne) {
            playerOneShipsSunk[packet.getSunkShip().getType().getShipID() - 1] = true;
            playerTwo.getPacketHandler().sendPacket(packet);
        } else if (responder == playerTwo) {
            playerTwoShipsSunk[packet.getSunkShip().getType().getShipID() - 1] = true;
            playerOne.getPacketHandler().sendPacket(packet);
        }

        if (howManySunk(playerOneShipsSunk) >= 5) {
            server.console(this, "game won by " + playerTwo.getUsername());
            playerOne.getPacketHandler().sendPacket(new GameWonPacket(2));
            playerTwo.getPacketHandler().sendPacket(new GameWonPacket(2));
        } else if (howManySunk(playerTwoShipsSunk) >= 5) {
            server.console(this, "game won by " + playerOne.getUsername());
            playerOne.getPacketHandler().sendPacket(new GameWonPacket(1));
            playerTwo.getPacketHandler().sendPacket(new GameWonPacket(1));
        }
    }

    //determines how many ships have been sunk
    private int howManySunk(boolean[] sunk) {
        int i = 0;
        for (boolean b : sunk) {
            if (b) {
                i++;
            }
        }
        return i;
    }

    //gets the responce t the attack
    public void attackResponse(ClientHandler responder, ResultPacket packet) {
        if (responder == playerTwo) {
            playerOne.getPacketHandler().sendPacket(packet);
        } else if (responder == playerOne) {
            playerTwo.getPacketHandler().sendPacket(packet);
        }
    }

    //Gets the attack
    public void attack(ClientHandler attacker, AttackPacket packet) {
        if (attacker == playerOne) {
            playerTwo.getPacketHandler().sendPacket(packet);
        } else if (attacker == playerTwo) {
            playerOne.getPacketHandler().sendPacket(packet);
        }
    }

    //gats player one
    public ClientHandler getPlayerOne() {
        return playerOne;
    }

    //gets player two
    public ClientHandler getPlayerTwo() {
        return playerTwo;
    }

    @Override
    //Creates a vs script
    public String toString() {
        return playerOne.getUsername() + " vs. " + playerTwo.getUsername();
    }

}
