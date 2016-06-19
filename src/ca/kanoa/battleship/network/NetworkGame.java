package ca.kanoa.battleship.network;

import ca.kanoa.battleship.network.packet.PlayerOnePacket;
import ca.kanoa.battleship.network.packet.PlayerTwoPacket;
import ca.kanoa.battleship.network.packet.StartGamePacket;
import org.newdawn.slick.SlickException;

public class NetworkGame {

    private ClientHandler playerOne;
    private ClientHandler playerTwo;
    private NetworkGameStage stage;
    private BaseServer server;
    private int turn;

    public NetworkGame(ClientHandler playerOne, ClientHandler playerTwo, BaseServer server) throws SlickException {
        this.server = server;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.stage = NetworkGameStage.NO_ONE_READY;
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
            stage = NetworkGameStage.PLAYER_ONE_TURN;
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
