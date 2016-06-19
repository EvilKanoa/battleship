package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Packet {

    public abstract byte getID();

    public abstract byte[] toData();

    public static Packet read(byte[] data) {
        switch (data[0]) {
            case Config.PACKET_KEEP_ALIVE_ID:
                return new KeepAlivePacket();
            case Config.PACKET_USERNAME_ID:
                String username = new String(Arrays.copyOfRange(data, 1, data.length));
                return new UsernamePacket(username);
            case Config.PACKET_LIST_PLAYERS_ID:
                ArrayList<String> players = new ArrayList<String>((data.length - 1) / 24);
                for (int i = 0; i < (data.length - 1) / 24; i++) {
                    players.add(i, (new String(Arrays.copyOfRange(data, (i * 24) + 1, (i * 24) + 25)).trim()));
                }
                return new ListPlayersPacket(players);
            case Config.PACKET_GAME_REQUEST_ID:
                String requestedOpponent = new String(Arrays.copyOfRange(data, 1, data.length));
                return new GameRequestPacket(requestedOpponent);
            case Config.PACKET_START_GAME_ID:
                String opponent = new String(Arrays.copyOfRange(data, 1, data.length));
                return new StartGamePacket(opponent);
            case Config.PACKET_READY_ID:
                return new ReadyPacket();
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
