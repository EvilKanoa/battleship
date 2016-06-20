package ca.kanoa.battleship.game;

//Main class for controling the game status
public enum GameStatus {

    //Creates the four states of the game
    PLAYER_ONE_TURN(1),
    PLAYER_TWO_TURN(2),
    PLACE_SHIPS,
    GAME_OVER;

    //creates the variable player
    private int player;

    //Gets the game status
    GameStatus() {
        this(-1);
    }

    //Sets the game stus for who's turn it is
    GameStatus(int player) {
        this.player = player;
    }

    //Returns who's turn it is
    public int getPlayer() {
        return player;
    }

}
