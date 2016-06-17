package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

import java.util.Arrays;

public abstract class Packet {

    abstract byte getID();

    abstract int getLength();

    abstract byte[] toData();

    public static Packet read(byte[] data) {
        switch (data[0]) {
            case Config.PACKET_KEEP_ALIVE_ID:

                return null;
            case Config.PACKET_USERNAME_ID:
                String username = new String(Arrays.copyOfRange(data, 1, data.length - 1));
                return new UsernamePacket(username);
            default:
                return null;
        }
    }

}
