package ca.kanoa.battleship.network;

import ca.kanoa.battleship.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class BaseServer {

    private ServerSocket serverSocket;
    private Set<ClientHandler> clients;

    public BaseServer() throws IOException {
        serverSocket = new ServerSocket(Config.NETWORK_PORT);
        clients = new HashSet<ClientHandler>();
        loop();
    }

    private void loop() throws IOException {
        while(true) {
            update();
        }
    }

    private void update() throws IOException {
        Socket client = serverSocket.accept();
        clients.add(new ClientHandler(new ClientConnection(client)));
    }

}
