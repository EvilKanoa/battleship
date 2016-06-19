package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

public class ReadyPacket extends Packet {


    @Override
    public byte getID() {
        return Config.PACKET_READY_ID;
    }

    @Override
    public byte[] toData() {
        return new byte[]{getID()};
    }
}
