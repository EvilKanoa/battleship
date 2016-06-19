package ca.kanoa.battleship.game;

public enum ShipType {

    CARRIER("Carrier", 5, 1),
    BATTLESHIP("Battleship", 4, 2),
    SUBMARINE("Submarine", 3, 3),
    CRUISER("Cruiser", 3, 4),
    DESTROYER("Destroyer", 2, 5);

    private int length;
    private String name;
    private int shipID;

    ShipType(String name, int length, int id) {
        this.name = name;
        this.length = length;
        this.shipID = id;
    }

    public int getShipLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public int getShipID() {
        return shipID;
    }

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
