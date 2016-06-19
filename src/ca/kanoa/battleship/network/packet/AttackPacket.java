package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

public class AttackPacket extends Packet {

    private int x, y;

    public AttackPacket(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public byte getID() {
        return Config.PACKET_ATTACK;
    }

    @Override
    public byte[] toData() {
        return new byte[]{getID(), (byte) x, (byte) y};
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
