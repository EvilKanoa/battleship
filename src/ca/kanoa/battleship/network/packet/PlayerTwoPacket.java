package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

//gets the information for the seconf player
public class PlayerTwoPacket extends Packet {

    @Override
    public byte getID() {//gets the ID of the packet
        return Config.PACKET_PLAYER_TWO_ID;
    }

    @Override
    public byte[] toData() {//converts the packet from bytes into data for the player to use
        return new byte[]{getID()};
    }

}
