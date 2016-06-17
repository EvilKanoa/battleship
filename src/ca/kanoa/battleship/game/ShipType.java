package ca.kanoa.battleship.game;

public enum ShipType {

    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private int length;
    private String name;

    ShipType(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public int getShipLength() {
        return length;
    }

    public String getName() {
        return name;
    }
}
