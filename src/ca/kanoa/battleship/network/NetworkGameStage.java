package ca.kanoa.battleship.network;

public enum NetworkGameStage {

    NO_ONE_READY(0),
    ONE_PLAYER_READY(1),
    BOTH_PLAYERS_READY(2),
    PLAYER_ONE_TURN(3),
    PLAYER_TWO_TURN(4),
    GAME_OVER(5);

    private int id;

    NetworkGameStage(int id) {
        this.id = id;
    }

    public NetworkGameStage nextStage() {
        return getStage(id + 1);
    }

    public static NetworkGameStage getStage(int id) {
        switch (id) {
            case 0: return NO_ONE_READY;
            case 1: return ONE_PLAYER_READY;
            case 2: return BOTH_PLAYERS_READY;
            case 3: return PLAYER_ONE_TURN;
            case 4: return PLAYER_TWO_TURN;
            case 5: return GAME_OVER;
            default: return null;
        }
    }

    public int getID() {
        return id;
    }

}
