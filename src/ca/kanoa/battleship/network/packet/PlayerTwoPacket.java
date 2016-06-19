package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

public class PlayerTwoPacket extends Packet {

    @Override
    public byte getID() {
        return Config.PACKET_PLAYER_TWO_ID;
    }

    @Override
    public byte[] toData() {
        return new byte[]{getID()};
    }

}
