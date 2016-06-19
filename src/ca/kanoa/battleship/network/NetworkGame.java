package ca.kanoa.battleship.network;

import ca.kanoa.battleship.game.Game;
import ca.kanoa.battleship.network.packet.StartGamePacket;

public class NetworkGame extends Game {

    private ClientHandler playerOne;
    private ClientHandler playerTwo;

    public NetworkGame(ClientHandler playerOne, ClientHandler playerTwo) {
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
