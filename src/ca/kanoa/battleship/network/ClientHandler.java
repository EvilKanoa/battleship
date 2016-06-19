package ca.kanoa.battleship.network;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.network.packet.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {

    private BaseServer server;
    private Socket socket;
    private String username;

    private PacketHandler packetHandler;
    private boolean connected;
    private NetworkGame activeGame;

    public ClientHandler(Socket socket, BaseServer server) throws IOException {
        this.server = server;
        this.socket = socket;
        this.packetHandler = new PacketHandler(socket, Config.NETWORK_TIMEOUT);
        this.connected = true;
        this.username = socket.getInetAddress().toString();
        super.start();
    }

    @Override
    public void run() {
        while (connected) {
            loop();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void loop() {
        // check if the client is still connected
        if (!packetHandler.connected()) {
            server.console(this, "client disconnected");
            server.removeClient(this);
            connected = false;
            return;
        }

        // deal with incoming packets
        while (packetHandler.available() > 0) {
            Packet packet = packetHandler.get();
            switch (packet.getID()) {
                case Config.PACKET_USERNAME_ID:
                    username = ((UsernamePacket) packet).getUsername();
                    server.console(this, "New username for me: " + username);
                    return;
                case Config.PACKET_LIST_PLAYERS_ID:
                    List<String> players = server.getPlayers();
                    players.remove(username);
                    getPacketHandler().sendPacket(new ListPlayersPacket(players));
                    return;
                case Config.PACKET_GAME_REQUEST_ID:
                    String opponent = ((GameRequestPacket) packet).getRequestedOpponent();
                    GameRequest myRequest = new GameRequest(getUsername(), opponent);
                    server.console(this, "requested a game with " + opponent);

                    if (server.getClient(opponent) == null || !server.getClient(opponent).online()) {
                        server.console(this, "game request denied");
                    }

                    for (GameRequest request : server.getGameRequests()) {
                        if (request.match(myRequest)) {
                            server.console(this, "starting a game with " + opponent);
                            server.getGameRequests().remove(request);
                            server.startGame(this, server.getClient(opponent));
                            return;
                        }
                    }
                    if (!server.getGameRequests().contains(myRequest) && server.getClient(opponent) != null) {
                        server.getGameRequests().add(myRequest);
                        server.getClient(opponent).getPacketHandler().sendPacket(new GameRequestPacket(getUsername()));
                    }
                    return;
                case Config.PACKET_READY_ID:
                    getActiveGame().readyUp();
                    return;
                case Config.PACKET_SHIP_SUNK_ID:
                    getActiveGame().sunkenShip(this, (ShipSunkPacket) packet);
                    return;
                case Config.PACKET_ATTACK:
                    getActiveGame().attack(this, (AttackPacket) packet);
                    return;
                case Config.PACKET_RESULT:
                    getActiveGame().attackResponse(this, (ResultPacket) packet);
                    return;
            }
        }

        // update components
        packetHandler.update();
    }

    public synchronized boolean online() {
        return server.getClients().contains(this);
    }

    public synchronized PacketHandler getPacketHandler() {
        return packetHandler;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }

    public NetworkGame getActiveGame() {
        return activeGame;
    }

    public void setActiveGame(NetworkGame activeGame) {
        this.activeGame = activeGame;
    }
}
