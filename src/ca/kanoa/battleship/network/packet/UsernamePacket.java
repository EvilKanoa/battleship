package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

//Creates a packet to send others your username
public class UsernamePacket extends Packet {

    //Creates a variable for use in the program
    private String username;

    //Creates the packet for the username
    public UsernamePacket(String username) {
        this.username = username.trim();
    }

    @Override
    public byte getID() { //gets the packet ID
        return Config.PACKET_USERNAME_ID;
    }

    @Override
    public byte[] toData() { //convets the username from Bytes to data
        byte[] chars = username.getBytes();
        byte[] data = new byte[1 + chars.length];
        data[0] = getID();
        for (int i = 0; i < chars.length; i++) {
            data[i + 1] = chars[i];
        }
        return data;
    }

    public String getUsername() { //returns the username to the user
        return username;
    }
}
