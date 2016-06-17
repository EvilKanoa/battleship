package ca.kanoa.battleship.network;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {

    private BaseServer server;
    private ClientConnection client;
    private PacketHandler packetHandler;

    public ClientHandler(ClientConnection client, BaseServer server) throws IOException {
        this.server = server;
        this.client = client;
        this.packetHandler = new PacketHandler(client.socket);
        super.start();
    }

    @Override
    public void run() {
        while (true) {
            loop();
        }
    }

    private void loop() {
        packetHandler.update();
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
