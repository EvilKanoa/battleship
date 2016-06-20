package ca.kanoa.battleship.files;

import java.io.*;

/**
 * A file filled with bytes of data. Used to store more efficiently than text files.
 */
public class FlatFile {

    private String fileName;
    private File file;
    private DataInputStream input;
    private DataOutputStream output;
    private boolean append;

    /**
     * Creates a new flat file and opens it's I/O streams
     * @param fileName The name of the file to open
     * @param append Whether the data should be appended to the end of the current file or overwritten
     */
    public FlatFile(String fileName, boolean append) throws IOException {
        this.fileName = fileName;
        this.file = new File(fileName);
        this.append = append;

        if (!file.exists()) {
            file.createNewFile();
        }

        this.input = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
    }

    /**
     * Returns the number of available bytes to be read without blocking the thread
     */
    public int available() {
        try {
            return input.available();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Tries to read a set number of bytes from the input stream
     * @param data An array for the data to be put into
     * @param length The ideal length of bytes to be read
     * @return The Actual number of bytes read. Must be equal to or less than length
     */
    public int read(byte[] data, int length) throws IOException {
        return input.read(data, 0, length);
    }

    /**
     * Reads and returns a specified number of bytes from the file. If length is longer than available() the method
     * will block the thread
     * @param length The number of bytes to read
     * @return An array of the next length bytes
     */
    public byte[] getData(int length) {
        byte[] data = new byte[length];
        try {
            // try the read the next bytes into data
            input.read(data, 0, length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Appends an array of bytes to the end of the file
     * @param data The bytes to append
     * @return Whether the write was successful
     */
    public boolean write(byte[] data) {
        try {
            if (output == null) {
                output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file, append)));
            }
            output.write(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Closes the output file. Must be called after writing!
     */
    public void close() {
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the name of the file
     */
    public String getName() {
        return this.fileName;
    }

}
