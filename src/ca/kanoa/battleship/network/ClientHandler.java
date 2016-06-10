package ca.kanoa.battleship.network;

import java.net.Socket;

/**
 * Created by kanoa on 2016-06-09.
 */
public class ClientHandler extends Thread {

    private BaseServer server;
    private ClientConnection client;

    public ClientHandler(ClientConnection client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            loop();
        }
    }

    private void loop() {

    }

    public ClientConnection getClient() {
        return this.client;
    }

    public Socket getSocket() {
        return this.client.socket;
    }

    public String getUsername() {
        return this.client.username;
    }
}
