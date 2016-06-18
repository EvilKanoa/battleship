package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

public class GameRequestPacket extends Packet {

    private String requestedOpponent;

    public GameRequestPacket(String requestedOpponent) {
        this.requestedOpponent = requestedOpponent;
    }

    @Override
    public byte getID() {
        return Config.PACKET_GAME_REQUEST;
    }

    @Override
    public byte[] toData() {
        byte[] chars = requestedOpponent.getBytes();
        byte[] data = new byte[chars.length + 1];
        data[0] = getID();
        System.arraycopy(chars, 0, data, 1, chars.length);
        return data;
    }

    public String getRequestedOpponent() {
        return requestedOpponent;
    }

}
