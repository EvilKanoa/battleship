package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

//creates a packet to send the results to the player
public class ResultPacket extends Packet {

    //creates variables touse in the program
    private int x, y;
    private boolean hit;

    //makes the packet for augmentation later in the code
    public ResultPacket(int x, int y, boolean hit) {
        this.x = x;
        this.y = y;
        this.hit = hit;
    }

    @Override
    public byte getID() {//gets the ID of the packet
        return Config.PACKET_RESULT_ID;
    }

    @Override
    public byte[] toData() { //converts the information from bytes into data
        return new byte[]{getID(), (byte) x, (byte) y, (byte) (hit ? 1 : 0)};
    }

    //gats the x co-ordinate
    public int getX() {
        return x;
    }

    //gets the y co-ordinant
    public int getY() {
        return y;
    }

    //chacks if the point is a hit
    public boolean isHit() {
        return hit;
    }

}
