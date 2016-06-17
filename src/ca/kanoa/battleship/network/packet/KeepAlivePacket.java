package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

public class KeepAlivePacket extends Packet {
    @Override
    public byte getID() {
        return Config.PACKET_KEEP_ALIVE_ID;
    }

    @Override
    public byte[] toData() {
        return new byte[]{Config.PACKET_KEEP_ALIVE_ID};
    }
}
