package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

//Creates a class to keep the program alive
public class KeepAlivePacket extends Packet {
    @Override
    public byte getID() { //Gets the ID of the packet
        return Config.PACKET_KEEP_ALIVE_ID;
    }

    @Override
    public byte[] toData() { //Converts the packet from bytes to data
        return new byte[]{Config.PACKET_KEEP_ALIVE_ID};
    }
}
