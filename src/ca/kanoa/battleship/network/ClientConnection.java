package ca.kanoa.battleship.network;

import java.io.*;
import java.net.Socket;

public class ClientConnection {

    protected final Socket socket;
    protected String username;
    protected long updated;

    public ClientConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.updated = System.currentTimeMillis();
        this.username = socket.getInetAddress().toString();
    }

}
