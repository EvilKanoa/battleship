package ca.kanoa.battleship.network;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.network.packet.UsernamePacket;
import ca.kanoa.battleship.util.Timer;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class BaseClient extends Thread {

    private String serverAddress;
    private Socket socket;
    private PacketHandler packetHandler;
    private boolean connected = false;
    private String username;

    public BaseClient(String serverAddress) {
        this.serverAddress = serverAddress;
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
    }

    @Override
    public void run() {
        while (connected) {
            update();
        }
    }

    public String getUsername() {
        return username;
    }
}
