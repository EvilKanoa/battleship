package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

//Creates a class for the first player of the game
public class PlayerOnePacket extends Packet {

    @Override
    public byte getID() { //gets the ID of the packet
        return Config.PACKET_PLAYER_ONE_ID;
    }

    @Override
    public byte[] toData() { //converts the data from bytes
        return new byte[]{getID()};
    }

}
