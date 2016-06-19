package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

import java.util.Arrays;
import java.util.List;

public class ListPlayersPacket extends Packet {

    private List<String> players;

    public ListPlayersPacket(List<String> players) {
        this.players = players;
    }

    public ListPlayersPacket() {
        players = null;
    }

    @Override
    public byte getID() {
        return Config.PACKET_LIST_PLAYERS_ID;
    }

    @Override
    public byte[] toData() {
        if (players == null) {
            return new byte[]{getID()};
        } else {
            byte[] data = new byte[24 * players.size() + 1];
            data[0] = getID();
            byte[] strData = new byte[24];
            for (int i = 0; i < players.size(); i++) {
                Arrays.fill(strData, (byte) 0);
                System.arraycopy(players.get(i).getBytes(), 0, strData, 0, players.get(i).length());
                System.arraycopy(strData, 0, data, (i * 24) + 1, 24);
            }
            return data;
        }
    }

    public List<String> getPlayers() {
        return players;
    }

}
