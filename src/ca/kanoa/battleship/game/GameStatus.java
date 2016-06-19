package ca.kanoa.battleship.game;

public enum GameStatus {

    PLAYER_ONE_TURN(1),
    PLAYER_TWO_TURN(2),
    PLACE_SHIPS,
    GAME_OVER;

    private int player;

    GameStatus() {
        this(-1);
    }

    GameStatus(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

}
