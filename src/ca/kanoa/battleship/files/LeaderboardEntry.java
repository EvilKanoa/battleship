package ca.kanoa.battleship.files;

import org.lwjgl.Sys;

import java.util.Arrays;

/**
 * Stores each entry in a leaderboard
 */
public class LeaderboardEntry {

    private String name;
    private int score;

    /**
     * Creates a new leaderboard entry with the given information
     */
    public LeaderboardEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Parses an entry from an array of bytes that was previously an entry
     */
    public static LeaderboardEntry fromData(byte[] data) {
        // check the length of data
        if (data.length != 18) {
            return null;
        }
        // read the name which is the first 16 bytes
        String name = new String(Arrays.copyOfRange(data, 0, 16)).trim();
        // read the score which is the last 2 bytes
        int score = (data[17] & 0xFF) << 8 | (data[16] & 0xFF);

        return new LeaderboardEntry(name, score);
    }

    /**
     * Converts an entry into an array of bytes to be written to a file
     */
    public static byte[] toData(LeaderboardEntry entry) {
        // create the arrays for the pure data and the string based name data
        byte[] data = new byte[18];
        byte[] nameData = "                ".getBytes();

        // populate the nameData array
        System.arraycopy(entry.getName().getBytes(), 0, nameData, 0, entry.getName().length() > 16 ? 16 :
                entry.getName().length());

        // add the name to the full array
        System.arraycopy(nameData, 0, data, 0, 16);

        // add the score to the array
        data[16] = (byte) (entry.getScore() & 0xFF);
        data[17] = (byte) ((entry.getScore() >> 8) & 0xFF);

        return data;
    }

    /**
     * Gets the name of the entries setter
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the score that was set in this entry
     */
    public int getScore() {
        return score;
    }

}
