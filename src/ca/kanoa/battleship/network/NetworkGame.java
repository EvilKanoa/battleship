package ca.kanoa.battleship.network;

import ca.kanoa.battleship.game.Game;

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

    }

}
