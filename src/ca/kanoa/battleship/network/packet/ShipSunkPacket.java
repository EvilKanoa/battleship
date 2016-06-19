package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.game.Ship;

public class ShipSunkPacket extends Packet {

    private Ship sunkShip;

    public ShipSunkPacket(Ship sunkShip) {
        this.sunkShip = sunkShip;
    }

    @Override
    public byte getID() {
        return Config.PACKET_SHIP_SUNK_ID;
    }

    @Override
    public byte[] toData() {
        byte[] data = new byte[5];
        data[0] = getID();
        data[1] = (byte) sunkShip.getType().getShipID();
        data[2] = (byte) sunkShip.getX();
        data[3] = (byte) sunkShip.getY();
        data[4] = (byte) (sunkShip.isVertical() ? 1 : 0);
        return data;
    }

    public Ship getSunkShip() {
        return sunkShip;
    }

}
