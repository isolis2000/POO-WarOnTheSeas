/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import communication.FirstConnection;
import communication.Server;
import functionality.Players;

/**
 *
 * @author ivan
 */
public class GameMaster {
    
    private final static GameMaster gameMaster = new GameMaster();
    private Server server = new Server();
    private int firstPort = 35557;
    private FirstConnection firstConnection = new FirstConnection();
    private boolean gameInitialized = false;
    private Players players = new Players();

    public static GameMaster getGameMaster() {
        return gameMaster;
    }

    public Server getServer() {
        return server;
    }

    public int getFirstPort() {
        return firstPort;
    }

    public FirstConnection getFirstConnection() {
        return firstConnection;
    }

    public boolean isGameInitialized() {
        return gameInitialized;
    }

    public void setGameInitialized(boolean gameInitialized) {
        this.gameInitialized = gameInitialized;
    }

    public Players getPlayers() {
        return players;
    }
    
}
