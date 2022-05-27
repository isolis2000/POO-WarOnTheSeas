package client;

import client.Client;
import client.ThreadClient;
import client.gui.MainScreen;
import gamelogic.Player;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author ivan
 */
public class ClientManager {
    private static final ClientManager clientManager = new ClientManager();
    private Client client;
    private ThreadClient threadClient;
    private final int firstPort = 35557;
    private MainScreen mainScreen;
    private String playerName;
    private int port;
//    private Player player;

    public Client getClient() {
        return client;
    }

    public void setClient() {
        client = new Client();
    }

//    public Player getPlayer() {
//        return player;
//    }
//
//    public void setPlayer(Player player) {
//        this.player = player;
//    }

    public static ClientManager getCM() {
        return clientManager;
    }

    public int getFirstPort() {
        return firstPort;
    }

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public void setMainScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ThreadClient getThreadClient() {
        return threadClient;
    }

    public void setThreadClient(ThreadClient threadClient) {
        this.threadClient = threadClient;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
