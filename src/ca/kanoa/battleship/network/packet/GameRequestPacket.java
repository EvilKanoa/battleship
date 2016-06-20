package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

//Creates a class for the game request
public class GameRequestPacket extends Packet {

    //creats a variable for use in the code
    private String requestedOpponent;

    //Sets the game request
    public GameRequestPacket(String requestedOpponent) {
        this.requestedOpponent = requestedOpponent;
    }

    @Override
    public byte getID() {//gets the ID of the game request
        return Config.PACKET_GAME_REQUEST_ID;
    }

    @Override
    //Convets the data in to bytes
    public byte[] toData() {
        byte[] chars = requestedOpponent.getBytes();
        byte[] data = new byte[chars.length + 1];
        data[0] = getID();
        System.arraycopy(chars, 0, data, 1, chars.length);
        return data;
    }

    //gerts the requested opponent
    public String getRequestedOpponent() {
        return requestedOpponent;
    }

}
