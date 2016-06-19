package ca.kanoa.battleship.network;

import ca.kanoa.battleship.Battleship;
import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.game.GameStatus;
import ca.kanoa.battleship.game.Ship;
import ca.kanoa.battleship.network.packet.*;
import ca.kanoa.battleship.util.Timer;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BaseClient extends Thread {

    private String serverAddress;
    private Battleship battleship;
    private Socket socket;
    private PacketHandler packetHandler;
    private boolean connected = false;
    private boolean ingame = false;
    private String username;
    private AttackPacket activeAttack;
    private Timer attackResend;

    private List<String> onlinePlayers;
    private List<String> requests;

    public BaseClient(String serverAddress, Battleship owner) {
        this.serverAddress = serverAddress;
        this.onlinePlayers = new ArrayList<String>();
        this.requests = new LinkedList<String>();
        this.battleship = owner;
        this.activeAttack = null;
        this.attackResend = new Timer(500);
    }

    public boolean connect(String username) {
        try {
            socket = new Socket(serverAddress, Config.NETWORK_PORT);
            packetHandler = new PacketHandler(socket, Config.NETWORK_TIMEOUT);
            packetHandler.sendPacket(new UsernamePacket(username));
            this.username = username;
            this.connected = true;
            super.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void update() {
        packetHandler.update();

        // check if the server is still connected
        if (!packetHandler.connected()) {
            JOptionPane.showMessageDialog (null, "The server has disconnected", "Server Error",
                    JOptionPane.ERROR_MESSAGE);
            connected = false;
            System.exit(2);
            return;
        }

        // deal with incoming packets
        while (packetHandler.available() > 0) {
            Packet packet = packetHandler.get();
            switch (packet.getID()) {
                case Config.PACKET_LIST_PLAYERS_ID:
                    onlinePlayers = ((ListPlayersPacket) packet).getPlayers();
                    break;
                case Config.PACKET_GAME_REQUEST_ID:
                    String requestedOpponent = ((GameRequestPacket) packet).getRequestedOpponent();
                    requests.add(requestedOpponent);
                    break;
                case Config.PACKET_START_GAME_ID:
                    ingame = true;
                    String opponent = ((StartGamePacket) packet).getOpponent();
                    battleship.gameState.setOpponent(opponent);
                    battleship.enterState(Config.SCREEN_GAME);
                    break;
                case Config.PACKET_PLAYER_ONE_ID:
                    battleship.gameState.getGame().setMyPlayer(1);
                    break;
                case Config.PACKET_PLAYER_TWO_ID:
                    battleship.gameState.getGame().setMyPlayer(2);
                    break;
                case Config.PACKET_SHIP_SUNK_ID:
                    ShipSunkPacket sunkPacket = (ShipSunkPacket) packet;
                    battleship.gameState.sunkShip(sunkPacket.getSunkShip());
                    break;
                case Config.PACKET_ATTACK:
                    AttackPacket attk = (AttackPacket) packet;
                    boolean hit = battleship.gameState.attack(attk.getX(), attk.getY());
                    packetHandler.sendPacket(new ResultPacket(attk.getX(), attk.getY(), hit));

                    Ship sunken = battleship.gameState.getGame().getMyMap().checkSunkenShip(attk.getX(), attk.getY());
                    if (sunken != null) {
                        packetHandler.sendPacket(new ShipSunkPacket(sunken));
                    }
                    break;
                case Config.PACKET_RESULT:
                    activeAttack = null;
                    ResultPacket res = (ResultPacket) packet;
                    battleship.gameState.attackResult(res.getX(), res.getY(), res.isHit());
                    break;
                case Config.PACKET_GAME_WON:
                    GameWonPacket winner = (GameWonPacket) packet;
                    battleship.gameState.setWinner(winner.getWinner());
                    battleship.gameState.getGame().setStatus(GameStatus.GAME_OVER);
                    break;
            }
        }

        // remove offline requests
        for (Iterator<String> iterator = requests.iterator(); iterator.hasNext();) {
            if (!onlinePlayers.contains(iterator.next())) {
                iterator.remove();
            }
        }

        // resend attack if no reply
        if (activeAttack != null && attackResend.check()) {
            packetHandler.sendPacket(activeAttack);
            attackResend.reset();
        }
    }

    @Override
    public void run() {
        while (connected) {
            update();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getOnlinePlayers() {
        return onlinePlayers;
    }

    public List<String> getGameRequests() {
        return requests;
    }

    public void refreshPlayers() {
        packetHandler.sendPacket(new ListPlayersPacket());
    }

    public void requestGame(String opponent) {
        packetHandler.sendPacket(new GameRequestPacket(opponent));
    }

    public void attack(int x, int y) {
        activeAttack = new AttackPacket(x, y);
        attackResend.reset();
        packetHandler.sendPacket(activeAttack);
    }

    public void shipSunk(Ship ship) {
        packetHandler.sendPacket(new ShipSunkPacket(ship));
    }

    public void readyUp() {
        packetHandler.sendPacket(new ReadyPacket());
    }

    public String getUsername() {
        return username;
    }
}
