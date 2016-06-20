package ca.kanoa.battleship.game;

public enum ShipType {

    //sets 5 ship types
    CARRIER("Carrier", 5, 1),
    BATTLESHIP("Battleship", 4, 2),
    SUBMARINE("Submarine", 3, 3),
    CRUISER("Cruiser", 3, 4),
    DESTROYER("Destroyer", 2, 5);

    //creates variables for use in the program
    private int length;
    private String name;
    private int shipID;

    //gets the ship type
    ShipType(String name, int length, int id) {
        this.name = name;
        this.length = length;
        this.shipID = id;
    }

    //gets the ship's length
    public int getShipLength() {
        return length;
    }

    //gets the ship's name
    public String getName() {
        return name;
    }

    //get's the ship's ID
    public int getShipID() {
        return shipID;
    }

    //Returns the type of ship
    public static ShipType getShipType(int id) {
        switch (id) {
            case 1: return CARRIER;
            case 2: return BATTLESHIP;
            case 3: return SUBMARINE;
            case 4: return CRUISER;
            case 5: return DESTROYER;
            default: return null;
        }

    }

}
