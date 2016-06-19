package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

public class GameWonPacket extends Packet {

    private int winner;

    public GameWonPacket(int winner) {
        this.winner = winner;
    }

    @Override
    public byte getID() {
        return Config.PACKET_GAME_WON;
    }

    @Override
    public byte[] toData() {
        return new byte[]{getID(), (byte) winner};
    }

    public int getWinner() {
        return winner;
    }

}
