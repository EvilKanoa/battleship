package ca.kanoa.battleship.network;

import ca.kanoa.battleship.network.packet.AttackPacket;
import ca.kanoa.battleship.network.packet.ResultPacket;
import ca.kanoa.battleship.network.packet.ShipSunkPacket;

public class AIGame implements NetworkGame {
    @Override
    public boolean playerParticipating(ClientHandler player) {
        return false;
    }

    @Override
    public void startGame() {

    }

    @Override
    public void readyUp() {

    }

    @Override
    public void sunkenShip(ClientHandler responder, ShipSunkPacket packet) {

    }

    @Override
    public void attackResponse(ClientHandler responder, ResultPacket packet) {

    }

    @Override
    public void attack(ClientHandler attacker, AttackPacket packet) {

    }

    @Override
    public ClientHandler getPlayerOne() {
        return null;
    }

    @Override
    public ClientHandler getPlayerTwo() {
        return null;
    }
}
