package ca.kanoa.battleship.network;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.files.Leaderboard;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

//Sets up the base server
public class BaseServer {

    //Crates variables for use in the program
    private ServerSocket serverSocket;
    private Set<ClientHandler> clients;
    private List<GameRequest> requests;
    private List<NetworkGame> games;
    private Leaderboard leaderboard;

    //Sets up the base server
    public BaseServer() throws IOException {
        console("Starting server...");
        console("Initiating network...");
        serverSocket = new ServerSocket(Config.NETWORK_PORT);
        console("Network started");
        clients = new HashSet<ClientHandler>();
        requests = Collections.synchronizedList(new LinkedList<GameRequest>());
        games = Collections.synchronizedList(new ArrayList<NetworkGame>());
        leaderboard = new Leaderboard("leaderboard.dat");
    }

    //Starts up the server
    public void loop() throws IOException {
        console("Server started");
        leaderboard.read();
        while(true) {
            update();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Updates the server when a person connects
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

        // save the current leaderboard
        leaderboard.write();
    }

    //Starts a game on the server
    public synchronized void startGame(ClientHandler playerOne, ClientHandler playerTwo) {
        if (playerActive(playerOne) || playerActive(playerTwo) || !playerOne.online() || !playerTwo.online()) {
            return;
        }

        NetworkGame newGame = null;
        try {
            newGame = new MultiplayerGame(playerOne, playerTwo, this);
            playerOne.setActiveGame(newGame);
            playerTwo.setActiveGame(newGame);
            games.add(newGame);
            newGame.startGame();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    //Starts a game vs the AI on the server
    public synchronized void startAIGame(ClientHandler player) {
        // TODO: Start a game vs the AI
        if (playerActive(player) || !player.online()) {
            return;
        }

        NetworkGame newGame = new AIGame(this, player);
        player.setActiveGame(newGame);
        games.add(newGame);
        newGame.startGame();
    }

    //Syncronizes the players
    public synchronized List<String> getPlayers() {
        List<String> players = new LinkedList<String>();
        for (ClientHandler client : clients) {
            players.add(client.getUsername());
        }
        return players;
    }

    //Handles the clients
    public synchronized Set<ClientHandler> getClients() {
        return clients;
    }

    //Gets active players
    public synchronized boolean playerActive(ClientHandler player) {
        for (NetworkGame game : games) {
            if (game.playerParticipating(player)) {
                return true;
            }
        }
        return false;
    }

    //Sets up the network game
    public synchronized NetworkGame getGame(ClientHandler player) {
        return player.getActiveGame();
    }

    public synchronized Leaderboard getLeaderboard() {
        return leaderboard;
    }

    //Lists the game requests
    public synchronized List<GameRequest> getGameRequests() {
        return requests;
    }

    //Gets the clients of the players
    public synchronized ClientHandler getClient(String username) {
        for (ClientHandler handler : clients) {
            if (handler.getUsername().equalsIgnoreCase(username)) {
                return handler;
            }
        }
        return null;
    }

    //Creates a console to handle the game
    public void console(String source, String message) {
        System.out.printf("%s -> %s\n", source, message);
    }

    //uses the console to send messages
    public void console(String message) {
        console("server", message);
    }

    //Uses the console to handle usernames
    public void console(ClientHandler source, String message) {
        console(source.getUsername(), message);
    }

    //Uses the console to set up the network game
    public void console(NetworkGame source, String message) {
        console(source.toString(), message);
    }

    //Removes a player at the end of a game
    protected void removeClient(ClientHandler client) {
        clients.remove(client);
    }

}
