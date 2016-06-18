package ca.kanoa.battleship.network;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.network.packet.KeepAlivePacket;
import ca.kanoa.battleship.network.packet.ListPlayersPacket;
import ca.kanoa.battleship.network.packet.Packet;
import ca.kanoa.battleship.network.packet.UsernamePacket;
import ca.kanoa.battleship.util.Timer;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {

    private BaseServer server;
    private Socket socket;
    private String username;
    private PacketHandler packetHandler;
    private boolean connected;

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
        }
    }

    private void loop() {
        // update components
        packetHandler.update();

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
                    break;
                case Config.PACKET_LIST_PLAYERS:
                    server.console(this, "list players");
                    List<String> players = server.getPlayers();
                    players.remove(username);
                    packetHandler.sendPacket(new ListPlayersPacket(players));
                    break;
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }
}
