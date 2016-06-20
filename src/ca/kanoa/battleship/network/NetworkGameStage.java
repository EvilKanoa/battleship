package ca.kanoa.battleship.network;

//creates a enum to control the network game stage
public enum NetworkGameStage {

    //sets the states for use in the network game state
    NO_ONE_READY(0),
    ONE_PLAYER_READY(1),
    BOTH_PLAYERS_READY(2),
    IN_GAME(3),
    GAME_OVER(4);

    // sets a variable for use in the program
    private int id;

    NetworkGameStage(int id) { //gets the state ID for use in the code
        this.id = id;
    }

    // gets he next stage for use in the cide
    public NetworkGameStage nextStage() {
        return getStage(id + 1);
    }

    //sets the cases for the switch used in the code
    public static NetworkGameStage getStage(int id) {
        switch (id) {
            case 0: return NO_ONE_READY;
            case 1: return ONE_PLAYER_READY;
            case 2: return BOTH_PLAYERS_READY;
            case 3: return IN_GAME;
            case 4: return GAME_OVER;
            default: return null;
        }
    }

    //returns the state ID gotten in the code
    public int getID() {
        return id;
    }

}
