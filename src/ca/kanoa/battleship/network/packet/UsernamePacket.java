package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

public class UsernamePacket extends Packet {

    private String username;

    public UsernamePacket(String username) {
        this.username = username;
    }

    @Override
    public byte getID() {
        return Config.PACKET_USERNAME_ID;
    }

    @Override
    public byte[] toData() {
        byte[] chars = username.getBytes();
        byte[] data = new byte[1 + chars.length];
        data[0] = getID();
        for (int i = 0; i < chars.length; i++) {
            data[i + 1] = chars[i];
        }
        return data;
    }

    public String getUsername() {
        return username;
    }
}
