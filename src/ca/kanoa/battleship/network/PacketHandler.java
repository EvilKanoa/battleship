package ca.kanoa.battleship.network;

import ca.kanoa.battleship.network.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PacketHandler {

    protected final DataInputStream input;
    protected final DataOutputStream output;
    private final List<Packet> incomingPackets;
    private final List<Packet> packetQueue;

    public PacketHandler(Socket socket) throws IOException {
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.incomingPackets = Collections.synchronizedList(new LinkedList<Packet>());
        this.packetQueue = Collections.synchronizedList(new LinkedList<Packet>());
    }

    public void update() {
        // send all outgoing packets
        for (Packet packet : packetQueue) {
            try {
                output.write(Packet.buildPackage(packet.toData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        packetQueue.clear();

        // read any incoming packets
        try {
            while (input.available() > 0) {
                int length = input.readByte();
                byte[] data = new byte[length];
                input.readFully(data, 0, length);
                incomingPackets.add(Packet.read(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Packet[] getAvailablePackets() {
        Packet[] newPackets = incomingPackets.toArray(new Packet[0]);
        incomingPackets.clear();
        return newPackets;
    }

    public void sendPacket(Packet packet) {
        this.packetQueue.add(packet);
    }

}
