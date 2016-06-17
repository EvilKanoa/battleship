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
    public int getLength() {
        return -1;
    }

    @Override
    public byte[] toData() {
        byte[] data = new byte[2 + username.length()];
        data[0] = getID();
        byte[] chars = username.getBytes();
        for (int i = 0; i < chars.length; i++) {
            data[i + 1] = chars[i];
        }
        data[1 + username.length()] = Config.END_PACKET_BYTE;
        return data;
    }

    public String getUsername() {
        return username;
    }
}
