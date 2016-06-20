package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

//Creates a class to create the start game packet
public class StartGamePacket extends Packet {

    //creates variables for use in the program
    private String opponent;

    //creates the start game packet
    public StartGamePacket(String opponent) {
        this.opponent = opponent;
    }

    @Override
    public byte getID() { //gets the packet ID
        return Config.PACKET_START_GAME_ID;
    }

    @Override
    public byte[] toData() {//converts the packet from bytes to data
        byte[] chars = opponent.getBytes();
        byte[] data = new byte[chars.length + 1];
        data[0] = getID();
        System.arraycopy(chars, 0, data, 1, chars.length);
        return data;
    }

    public String getOpponent() { //gets the opponent for the user
        return opponent;
    }

}
