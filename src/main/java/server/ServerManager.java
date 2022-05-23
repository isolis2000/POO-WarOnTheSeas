/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import server.functionality.Players;

/**
 *
 * @author ivan
 */
public class ServerManager {
    
    private final static ServerManager serverManager = new ServerManager();
    private int firstPort = 35557;
    private boolean gameInitialized = false;
    private Players players = new Players();

    public static ServerManager getSM() {
        return serverManager;
    }

    public int getFirstPort() {
        return firstPort;
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
