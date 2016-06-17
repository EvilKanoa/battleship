package ca.kanoa.battleship.network;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.network.packet.UsernamePacket;

import java.io.IOException;
import java.net.Socket;

public class BaseClient extends Thread {

    private String serverAddress;
    private Socket socket;
    private PacketHandler packetHandler;

    public BaseClient(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public boolean connect(String username) {
        try {
            socket = new Socket(serverAddress, Config.NETWORK_PORT);
            packetHandler = new PacketHandler(socket);
            packetHandler.sendPacket(new UsernamePacket(username));
            super.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void update() {
        packetHandler.update();
    }

    @Override
    public void run() {
        while (true) {
            update();
        }
    }
}
