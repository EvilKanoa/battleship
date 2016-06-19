package ca.kanoa.battleship.network;

import ca.kanoa.battleship.network.packet.*;
import org.newdawn.slick.SlickException;

public class NetworkGame {

    private ClientHandler playerOne;
    private ClientHandler playerTwo;
    private boolean[] playerOneShipsSunk, playerTwoShipsSunk;
    private NetworkGameStage stage;
    private BaseServer server;

    public NetworkGame(ClientHandler playerOne, ClientHandler playerTwo, BaseServer server) throws SlickException {
        this.server = server;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.stage = NetworkGameStage.NO_ONE_READY;
        this.playerOneShipsSunk = new boolean[]{false, false, false, false, false};
        this.playerTwoShipsSunk = new boolean[]{false, false, false, false, false};
    }

    public boolean playerParticipating(ClientHandler player) {
        return player == playerOne || player == playerTwo;
    }

    public void startGame() {
        // send the game packets
        playerOne.getPacketHandler().sendPacket(new StartGamePacket(playerTwo.getUsername()));
        playerTwo.getPacketHandler().sendPacket(new StartGamePacket(playerOne.getUsername()));
    }

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

    private int howManySunk(boolean[] sunk) {
        int i = 0;
        for (boolean b : sunk) {
            if (b) {
                i++;
            }
        }
        return i;
    }

    public void attackResponse(ClientHandler responder, ResultPacket packet) {
        if (responder == playerTwo) {
            playerOne.getPacketHandler().sendPacket(packet);
        } else if (responder == playerOne) {
            playerTwo.getPacketHandler().sendPacket(packet);
        }
    }

    public void attack(ClientHandler attacker, AttackPacket packet) {
        if (attacker == playerOne) {
            playerTwo.getPacketHandler().sendPacket(packet);
        } else if (attacker == playerTwo) {
            playerOne.getPacketHandler().sendPacket(packet);
        }
    }

    public ClientHandler getPlayerOne() {
        return playerOne;
    }

    public ClientHandler getPlayerTwo() {
        return playerTwo;
    }

    @Override
    public String toString() {
        return playerOne.getUsername() + " vs. " + playerTwo.getUsername();
    }

}
