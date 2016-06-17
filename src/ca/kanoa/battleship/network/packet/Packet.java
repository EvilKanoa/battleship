package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

import java.util.Arrays;

public abstract class Packet {

    public abstract byte getID();

    public abstract byte[] toData();

    public static Packet read(byte[] data) {
        switch (data[0]) {
            case Config.PACKET_KEEP_ALIVE_ID:
                return new KeepAlivePacket();
            case Config.PACKET_USERNAME_ID:
                String username = new String(Arrays.copyOfRange(data, 2, data.length));
                return new UsernamePacket(username);
            default:
                return null;
        }
    }

    /**
     * Inserts a packet length byte in the beginning of a packet
     */
    public static byte[] buildPackage(byte[] packet) {
        byte[] data = new byte[packet.length + 1];
        data[0] = (byte) packet.length;
        for (int i = 0; i < data[0]; i++) {
            data[i + 1] = packet[i];
        }
        return data;
    }

}
