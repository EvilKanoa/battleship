package ca.kanoa.battleship.network;

import ca.kanoa.battleship.network.packet.StartGamePacket;
import org.newdawn.slick.SlickException;

public class NetworkGame {

    private ClientHandler playerOne;
    private ClientHandler playerTwo;

    public NetworkGame(ClientHandler playerOne, ClientHandler playerTwo) throws SlickException {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public boolean playerParticipating(ClientHandler player) {
        return player == playerOne || player == playerTwo;
    }

    public void startGame() {
        // send the game packets
        playerOne.getPacketHandler().sendPacket(new StartGamePacket(playerTwo.getUsername()));
        playerTwo.getPacketHandler().sendPacket(new StartGamePacket(playerOne.getUsername()));

        // TODO: Finish starting the game...
    }

    public ClientHandler getPlayerOne() {
        return playerOne;
    }

    public ClientHandler getPlayerTwo() {
        return playerTwo;
    }

}
