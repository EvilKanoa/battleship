package ca.kanoa.battleship.network;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.network.packet.ListPlayersPacket;
import ca.kanoa.battleship.network.packet.Packet;
import ca.kanoa.battleship.network.packet.UsernamePacket;
import ca.kanoa.battleship.util.Timer;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BaseClient extends Thread {

    private String serverAddress;
    private Socket socket;
    private PacketHandler packetHandler;
    private boolean connected = false;
    private String username;

    private List<String> onlinePlayers;

    public BaseClient(String serverAddress) {
        this.serverAddress = serverAddress;
        this.onlinePlayers = new ArrayList<String>();
    }

    public boolean connect(String username) {
        try {
            socket = new Socket(serverAddress, Config.NETWORK_PORT);
            packetHandler = new PacketHandler(socket, Config.NETWORK_TIMEOUT);
            packetHandler.sendPacket(new UsernamePacket(username));
            this.username = username;
            this.connected = true;
            super.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void update() {
        packetHandler.update();

        // check if the server  is still connected
        if (!packetHandler.connected()) {
            JOptionPane.showMessageDialog (null, "The server has disconnected", "Server Error",
                    JOptionPane.ERROR_MESSAGE);
            connected = false;
            System.exit(2);
            return;
        }

        // deal with incoming packets
        while (packetHandler.available() > 0) {
            Packet packet = packetHandler.get();
            switch (packet.getID()) {
                case Config.PACKET_LIST_PLAYERS:
                    onlinePlayers = ((ListPlayersPacket) packet).getPlayers();
                    break;
            }
        }
    }

    @Override
    public void run() {
        while (connected) {
            update();
        }
    }

    public List<String> getOnlinePlayers() {
        return onlinePlayers;
    }

    public void refreshPlayers() {
        packetHandler.sendPacket(new ListPlayersPacket());
    }

    public String getUsername() {
        return username;
    }
}
