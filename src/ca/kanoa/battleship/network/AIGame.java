package ca.kanoa.battleship.network;

import ca.kanoa.battleship.game.AI;
import ca.kanoa.battleship.game.Ship;
import ca.kanoa.battleship.game.ShipType;
import ca.kanoa.battleship.network.packet.*;
import ca.kanoa.battleship.util.Timer;

public class AIGame implements NetworkGame {

    private ClientHandler player;
    private AI ai;
    private NetworkGameStage stage;
    private BaseServer server;
    private Timer attackTimer;
    private boolean attacking;
    private boolean[] playerShipsSunk, aiShipsSunk;
    private int turns;

    public AIGame(BaseServer server) {
        this.stage = NetworkGameStage.NO_ONE_READY;
        this.server = server;
        this.attackTimer = new Timer(6000);
        this.attacking = false;
        this.turns = 0;
        this.playerShipsSunk = new boolean[]{false, false, false, false, false};
        this.aiShipsSunk = new boolean[]{false, false, false, false, false};
    }

    @Override
    public boolean playerParticipating(ClientHandler player) {
        return player == this.player;
    }

    @Override
    public void startGame() {
        player.getPacketHandler().sendPacket(new StartGamePacket("The AI"));
    }

    @Override
    public void readyUp() {
        if (stage.getID() > 1) {
            return;
        }
        stage = NetworkGameStage.IN_GAME;
        server.console(this, "initiating game");
        player.getPacketHandler().sendPacket(new PlayerOnePacket());
    }

    @Override
    public void sunkenShip(ClientHandler responder, ShipSunkPacket packet) {
        playerShipsSunk[packet.getSunkShip().getType().getShipID() - 1] = true;
        ai.sunkenShip(packet.getSunkShip());

        if (howManySunk(playerShipsSunk) >= 5) {
            server.console(this, "game won by the AI");
            // TODO: Send a leaderboard packet and log to leaderboard
        }
    }

    @Override
    public void attackResponse(ClientHandler responder, ResultPacket packet) { }

    @Override
    public void attack(ClientHandler attacker, AttackPacket packet) {
        turns++;
        Ship sunken = ai.attack(packet.getX(), packet.getY());
        player.getPacketHandler().sendPacket(new ResultPacket(packet.getX(), packet.getY(), sunken != null));
        if (sunken != null) {
            aiShipsSunk[sunken.getType().getShipID() - 1] = true;
            player.getPacketHandler().sendPacket(new ShipSunkPacket(sunken));

            if (howManySunk(aiShipsSunk) >= 5) {
                server.console(this, "game won by " + player.getUsername());
                // TODO: Send a leaderboard packet and log
            }
        }
        attacking = true;
        attackTimer.reset();
    }

    //determines how many ships have been sunk
    private int howManySunk(boolean[] sunk) {
        int i = 0;
        for (boolean b : sunk) {
            if (b) {
                i++;
            }
        }
        return i;
    }

    @Override
    public ClientHandler getPlayerOne() {
        return player;
    }

    @Override
    public ClientHandler getPlayerTwo() {
        return player;
    }

    @Override
    public void update() {
        if (attacking && attackTimer.check()) {
            attacking = false;
            int[] attack = ai.getAttack();
            player.getPacketHandler().sendPacket(new AttackPacket(attack[0], attack[1]));
        }
    }

    public String toString() {
        return player.getUsername() + " vs. the AI";
    }

    public boolean isPlayerShipSunk(ShipType type) {
        return playerShipsSunk[type.getShipID() - 1];
    }

}