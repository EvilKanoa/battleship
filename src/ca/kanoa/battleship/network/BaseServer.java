package ca.kanoa.battleship.network;

import ca.kanoa.battleship.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BaseServer {

    private ServerSocket serverSocket;
    private Set<ClientHandler> clients;

    public BaseServer() throws IOException {
        console("Starting server...");
        console("Initiating network...");
        serverSocket = new ServerSocket(Config.NETWORK_PORT);
        console("Network started");
        clients = new HashSet<ClientHandler>();
    }

    public void loop() throws IOException {
        console("Server started");
        while(true) {
            update();
        }
    }

    private void update() throws IOException {
        Socket socket = serverSocket.accept();
        ClientHandler newClient = new ClientHandler(socket, this);
        clients.add(newClient);
        console("New client connected: " + newClient.getUsername());

        console("Current clients: ");
        for (ClientHandler client : clients) {
            console("\t" + client.getUsername());
        }
    }

    public List<String> getPlayers() {
        List<String> players = new LinkedList<String>();
        for (ClientHandler client : clients) {
            players.add(client.getUsername());
        }
        return players;
    }

    public void console(String source, String message) {
        System.out.printf("%s -> %s\n", source, message);
    }

    public void console(String message) {
        console("server", message);
    }

    public void console(ClientHandler source, String message) {
        console(source.getUsername(), message);
    }

    protected void removeClient(ClientHandler client) {
        clients.remove(client);
    }

}
