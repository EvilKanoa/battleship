package ca.kanoa.battleship.network;

import ca.kanoa.battleship.Config;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class BaseServer {

    private ServerSocket serverSocket;
    private Set<ClientHandler> clients;
    private List<GameRequest> requests;
    private List<NetworkGame> games;

    public BaseServer() throws IOException {
        console("Starting server...");
        console("Initiating network...");
        serverSocket = new ServerSocket(Config.NETWORK_PORT);
        console("Network started");
        clients = new HashSet<ClientHandler>();
        requests = Collections.synchronizedList(new LinkedList<GameRequest>());
        games = Collections.synchronizedList(new ArrayList<NetworkGame>());
    }

    public void loop() throws IOException {
        console("Server started");
        while(true) {
            update();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

        // clean requests
        List<String> online = getPlayers();
        GameRequest request;
        for (Iterator<GameRequest> iterator = requests.iterator(); iterator.hasNext();) {
            request = iterator.next();
            if (!online.contains(request.getChallenger()) || !online.contains(request.getOpponent())) {
                iterator.remove();
            }
        }

        // clean games
        NetworkGame game;
        for (Iterator<NetworkGame> iterator = games.iterator(); iterator.hasNext();) {
            game = iterator.next();
            if (!getClients().contains(game.getPlayerOne()) || !getClients().contains(game.getPlayerTwo())) {
                iterator.remove();
            }
        }
    }

    public synchronized void startGame(ClientHandler playerOne, ClientHandler playerTwo) {
        if (playerActive(playerOne) || playerActive(playerTwo) || !playerOne.online() || !playerTwo.online()) {
            return;
        }

        NetworkGame newGame = null;
        try {
            newGame = new NetworkGame(playerOne, playerTwo);
            playerOne.setActiveGame(newGame);
            playerTwo.setActiveGame(newGame);
            games.add(newGame);
            newGame.startGame();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<String> getPlayers() {
        List<String> players = new LinkedList<String>();
        for (ClientHandler client : clients) {
            players.add(client.getUsername());
        }
        return players;
    }

    public synchronized Set<ClientHandler> getClients() {
        return clients;
    }

    public synchronized boolean playerActive(ClientHandler player) {
        for (NetworkGame game : games) {
            if (game.playerParticipating(player)) {
                return true;
            }
        }
        return false;
    }

    public synchronized NetworkGame getGame(ClientHandler player) {
        return player.getActiveGame();
    }

    public synchronized List<GameRequest> getGameRequests() {
        return requests;
    }

    public synchronized ClientHandler getClient(String username) {
        for (ClientHandler handler : clients) {
            if (handler.getUsername().equalsIgnoreCase(username)) {
                return handler;
            }
        }
        return null;
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
