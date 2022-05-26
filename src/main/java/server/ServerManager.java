/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

/**
 *
 * @author ivan
 */
public class ServerManager {
    
    private final static ServerManager serverManager = new ServerManager();
    private Server server;

    public static ServerManager getSM() {
        return serverManager;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
    
}
