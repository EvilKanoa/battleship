package ca.kanoa.battleship.network.packet;

import ca.kanoa.battleship.Config;

//creates a class to contole the winning of the game
public class GameWonPacket extends Packet {

    //creates vaariables for use in the program
    private int winner;

    //creates the game packet for the winner
    public GameWonPacket(int winner) {
        this.winner = winner;
    }

    @Override
    public byte getID() { //gets the ID of the packet
        return Config.PACKET_GAME_WON;
    }

    @Override
    public byte[] toData() { //converts the packet from bytes into data
        return new byte[]{getID(), (byte) winner};
    }

    public int getWinner() {//gets the winner of the game
        return winner;
    }

}
