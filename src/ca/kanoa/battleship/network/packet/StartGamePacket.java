package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

public class StartGamePacket extends Packet {

    private String opponent;

    public StartGamePacket(String opponent) {
        this.opponent = opponent;
    }

    @Override
    public byte getID() {
        return Config.PACKET_START_GAME;
    }

    @Override
    public byte[] toData() {
        byte[] chars = opponent.getBytes();
        byte[] data = new byte[chars.length + 1];
        data[0] = getID();
        System.arraycopy(chars, 0, data, 1, chars.length);
        return data;
    }

    public String getOpponent() {
        return opponent;
    }

}
