package ca.kanoa.battleship.network;

//Creates method for requesting a game
public class GameRequest {

    //Creates variables for use in the program
    private String challenger;
    private String opponent;

    //Runs the game request
    public GameRequest(String challenger, String opponent) {
        this.challenger = challenger;
        this.opponent = opponent;
    }

    //Matches the player with an opponent
    public boolean match(GameRequest other) {
        return other.challenger.trim().equalsIgnoreCase(opponent.trim()) &&
                other.opponent.trim().equalsIgnoreCase(challenger.trim());
    }

    //gets the challanger
    public String getChallenger() {
        return challenger;
    }

    //gets the opponent
    public String getOpponent() {
        return opponent;
    }

    @Override
    //Confirms that both people want to play together
    public boolean equals(Object obj) {
        if (obj instanceof GameRequest) {
            GameRequest request = (GameRequest) obj;
            return request.opponent.trim().equalsIgnoreCase(opponent) &&
                    request.challenger.trim().equalsIgnoreCase(challenger);
        }
        return false;
    }

}
