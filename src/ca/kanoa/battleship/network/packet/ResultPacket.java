package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

public class ResultPacket extends Packet {

    private int x, y;
    private boolean hit;

    public ResultPacket(int x, int y, boolean hit) {
        this.x = x;
        this.y = y;
        this.hit = hit;
    }

    @Override
    public byte getID() {
        return Config.PACKET_RESULT;
    }

    @Override
    public byte[] toData() {
        return new byte[]{getID(), (byte) x, (byte) y, (byte) (hit ? 1 : 0)};
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHit() {
        return hit;
    }

}
