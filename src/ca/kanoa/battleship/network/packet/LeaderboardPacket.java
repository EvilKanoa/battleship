package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.files.Leaderboard;
import ca.kanoa.battleship.files.LeaderboardEntry;

import java.util.Arrays;

public class LeaderboardPacket extends Packet {

    private LeaderboardEntry[] leaderboard;

    public LeaderboardPacket(Leaderboard leaderboard) {
        LeaderboardEntry[] allEntries = leaderboard.getEntries();
        this.leaderboard = new LeaderboardEntry[allEntries.length > 10 ? 10 : allEntries.length];
        System.arraycopy(allEntries, 0, this.leaderboard, 0, this.leaderboard.length);
    }

    public LeaderboardPacket(LeaderboardEntry[] leaderboard) {
        if (leaderboard.length <= 10) {
            this.leaderboard = leaderboard;
        } else {
            this.leaderboard = Arrays.copyOfRange(leaderboard, 0, 10);
        }
        this.leaderboard = Leaderboard.sort(Arrays.asList(this.leaderboard));
    }

    @Override
    public byte getID() {
        return Config.PACKET_LEADERBOARD_ID;
    }

    @Override
    public byte[] toData() {
        byte[] data = new byte[1 + leaderboard.length * 18];
        data[0] = getID();
        for (int i = 0; i < leaderboard.length; i++) {
            System.arraycopy(LeaderboardEntry.toData(leaderboard[i]), 0, data, 1 + i * 18, 18);
        }
        return data;
    }

    public LeaderboardEntry[] getLeaderboard() {
        return leaderboard;
    }

}
