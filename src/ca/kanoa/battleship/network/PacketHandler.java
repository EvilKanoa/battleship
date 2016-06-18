package ca.kanoa.battleship.network;

import ca.kanoa.battleship.network.packet.KeepAlivePacket;
import ca.kanoa.battleship.network.packet.Packet;
import ca.kanoa.battleship.util.Timer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PacketHandler {

    protected final DataInputStream input;
    protected final DataOutputStream output;
    private final List<Packet> incomingPackets;
    private final List<Packet> packetQueue;
    private final Timer timeout;
    private boolean connected;

    public PacketHandler(Socket socket, long timeout) throws IOException {
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.incomingPackets = Collections.synchronizedList(new LinkedList<Packet>());
        this.packetQueue = Collections.synchronizedList(new LinkedList<Packet>());
        this.timeout = new Timer(timeout);
        this.connected = true;
    }

    /**
     * Has to be called constantly. Manages and controls incoming and outgoing packets.
     */
    public void update() {
        // check if still connected
        if (!connected) {
            return;
        } else if (timeout.check()) {
            timeout.reset();
            try {
                output.write(Packet.buildPackage((new KeepAlivePacket()).toData()));
            } catch (SocketException e) {
                // assume the connection is dead
                connected = false;
                return;
            } catch (IOException e) {
                // something bad happened, will retry next time
                e.printStackTrace();
            }
        }

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

    /**
     * Fetches all currently available packets. Can only be called once for each set of packets.
     * @return All available packets in an array
     */
    public synchronized Packet[] getAvailablePackets() {
        Packet[] newPackets = incomingPackets.toArray(new Packet[0]);
        incomingPackets.clear();
        return newPackets;
    }

    /**
     * Fetches the oldest packet on the stack and removes it
     * @return A single packet from the bottom of the stack
     */
    public synchronized Packet get() {
        Packet latest = incomingPackets.get(0);
        incomingPackets.remove(0);
        return latest;
    }

    /**
     * Checks how many packets are currently available
     * @return The number of available packets
     */
    public synchronized int available() {
        return incomingPackets.size();
    }

    /**
     * Checks if the current client is still connected
     * @return Whether the client is connected or not
     */
    public synchronized boolean connected() {
        return connected;
    }

    /**
     * Queues up a packet to be sent as soon as it can be
     * <br>
     * Make sure the packet's first byte is its length. This can be accomplished with Packet#package(byte[])
     * @param packet The packet to be sent
     */
    public synchronized void sendPacket(Packet packet) {
        this.packetQueue.add(packet);
    }

}
