package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

public class AIRequestPacket extends Packet {

    @Override
    public byte getID() {
        return Config.PACKET_AI_REQUEST_ID;
    }

    @Override
    public byte[] toData() {
        return new byte[]{getID()};
    }

}
