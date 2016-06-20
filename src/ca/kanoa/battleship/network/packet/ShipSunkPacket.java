package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.game.Ship;

//Creates a class for sending to sunk ships to the opponent
public class ShipSunkPacket extends Packet {

    //Creartes a variable for use in the program
    private Ship sunkShip;

    //creates the packet o send to the opponent
    public ShipSunkPacket(Ship sunkShip) {
        this.sunkShip = sunkShip;
    }

    @Override
    public byte getID() {//gets the ID of the sunken ship
        return Config.PACKET_SHIP_SUNK_ID;
    }

    @Override
    public byte[] toData() {//converts the informantion from bytes into data
        byte[] data = new byte[5];
        data[0] = getID();
        data[1] = (byte) sunkShip.getType().getShipID();
        data[2] = (byte) sunkShip.getX();
        data[3] = (byte) sunkShip.getY();
        data[4] = (byte) (sunkShip.isVertical() ? 1 : 0);
        return data;
    }

    //returns which ships have been sunk
    public Ship getSunkShip() {
        return sunkShip;
    }

}
