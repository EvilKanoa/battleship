package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

//creates a class to controle the sending of attack locations
public class AttackPacket extends Packet {

    //Creates vaariables for use in the program
    private int x, y;

    //Sends the attack to the opponent
    public AttackPacket(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public byte getID() {//gets the attack ID
        return Config.PACKET_ATTACK_ID;
    }

    @Override
    //Converts the data to bytes
    public byte[] toData() {
        return new byte[]{getID(), (byte) x, (byte) y};
    }

    //gets the x value
    public int getX() {
        return x;
    }

    //gets the y value
    public int getY() {
        return y;
    }
}
