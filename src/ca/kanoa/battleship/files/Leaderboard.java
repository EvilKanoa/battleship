package ca.kanoa.battleship.files;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * A file based leaderboard containing a list of entries with a username and a score
 */
public class Leaderboard {

    private List<LeaderboardEntry> entries;
    private FlatFile file;

    /**
     * Creates a new leaderboard from/to a specified file
     */
    public Leaderboard(String fileName) throws IOException {
        file = new FlatFile(fileName, false);
        entries = new LinkedList<LeaderboardEntry>();
        read();
    }

    /**
     * Reads the data from the file
     */
    public void read() {
        byte[] entryData = new byte[18];
        try {
            while (file.read(entryData, 18) == 18) {
                entries.add(LeaderboardEntry.fromData(entryData));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the sorted list of all entries
     */
    public LeaderboardEntry[] getEntries() {
        return sort(entries);
    }

    /**
     * Adds an entry to the leaderboard
     */
    public synchronized void addEntry(LeaderboardEntry entry) {
        entries.add(entry);
    }

    /**
     * Adds an entry to the leaderboard
     */
    public synchronized void add(String name, int score) {
        addEntry(new LeaderboardEntry(name, score));
    }

    /**
     * Outputs the leaderboard to the file
     */
    public boolean write() {
        boolean error = false;
        for (LeaderboardEntry entry : sort(entries)) {
            if (!file.write(LeaderboardEntry.toData(entry))) {
                error = true;
            }
        }
        file.close();
        return !error;
    }

    /**
     * Used to sort a list of leaderboard entries based on score and then name
     */
    public static LeaderboardEntry[] sort(List<LeaderboardEntry> unsorted) {
        LeaderboardEntry[] sorted = unsorted.toArray(new LeaderboardEntry[0]);

        // sort the leaderboard using insertion sort
        // temporary index counter used for moving every value
        int j;
        // variable used for comparison and swapping values
        LeaderboardEntry entry;

        // iterate over every slot of the array except the first
        for (int i = 1; i < sorted.length; i++) {
            // store the current value into x
            entry = sorted[i];
            // set the temporary index counter to one less than the current
            //  index
            j = i - 1;
            // continue moving backwards through the list while the entry held is more than the current checked
            // entry at j
            while (j >= 0 && sorted[j].getScore() > entry.getScore()) {
                // push the numbers into position
                sorted[j+1] = sorted[j];
                // decrease the temporary counter
                j--;
            }
            // set the current index to the held value within x
            sorted[j+1] = entry;
        }

        return sorted;
    }

    /**
     * Converts the leaderboard to a human readable string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LeaderboardEntry entry : getEntries()) {
            sb.append(entry.getName());
            sb.append(": ");
            sb.append(entry.getScore());
            sb.append("\n");
        }
        return sb.toString();
    }

}
