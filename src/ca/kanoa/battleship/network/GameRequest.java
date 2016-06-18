package ca.kanoa.battleship.network;

public class GameRequest {

    private String challenger;
    private String opponent;

    public GameRequest(String challenger, String opponent) {
        this.challenger = challenger;
        this.opponent = opponent;
    }

    public boolean match(GameRequest other) {
        return other.challenger.trim().equalsIgnoreCase(opponent.trim()) &&
                other.opponent.trim().equalsIgnoreCase(challenger.trim());
    }

    public String getChallenger() {
        return challenger;
    }

    public String getOpponent() {
        return opponent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GameRequest) {
            GameRequest request = (GameRequest) obj;
            return request.opponent.trim().equalsIgnoreCase(opponent) &&
                    request.challenger.trim().equalsIgnoreCase(challenger);
        }
        return false;
    }

}
