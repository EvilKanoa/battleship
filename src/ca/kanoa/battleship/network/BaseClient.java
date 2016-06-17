package ca.kanoa.battleship.network;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.network.packet.Packet;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BaseClient extends Thread {

    private String serverAddress;
    private Socket socket;
    private List<Packet> packets;

    public BaseClient(String serverAddress) {
        this.serverAddress = serverAddress;
        packets = new ArrayList<Packet>();
    }

    public BaseClient(String serverAddress, String username) {
        this(serverAddress);
        // TODO: Send username packet
    }

    public boolean connect() {
        try {
            this.socket = new Socket(serverAddress, Config.NETWORK_PORT);
            super.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized Packet[] getRecievedPackets() {
        Packet[] array = packets.toArray(new Packet[0]);
        packets.clear();
        return array;
    }

    public synchronized boolean sendPacket(Packet packet) {
        // TODO: Add network communication
        return false;
    }

    private void update() {

    }

    @Override
    public void run() {
        while (true) {
            update();
        }
    }
}
