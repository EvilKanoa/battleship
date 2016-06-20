package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

//Creates a class to get the packet ready
public class ReadyPacket extends Packet {


    @Override
    public byte getID() { //gets the ID of the Packet
        return Config.PACKET_READY_ID;
    }

    @Override
    public byte[] toData() { //convetts the information from bytes to data
        return new byte[]{getID()};
    }
}
