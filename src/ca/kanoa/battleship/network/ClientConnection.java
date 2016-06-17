package ca.kanoa.battleship.network;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.util.Timer;

import java.io.*;
import java.net.Socket;

public class ClientConnection {

    protected final Socket socket;
    protected String username;
    protected Timer timer;

    public ClientConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.timer = new Timer(Config.NETWORK_TIMEOUT);
        this.username = socket.getInetAddress().toString();
    }

}
