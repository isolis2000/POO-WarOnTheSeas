package client;

import client.Client;
import client.ThreadClient;
import client.gui.MainScreen;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author ivan
 */
public class GameMaster {
    private static final GameMaster gameMaster = new GameMaster();
    private Client client;
    private ThreadClient threadClient;
    private final int firstPort = 35557;
    private MainScreen mainScreen;
    private String playerName;
    private int port;

    public Client getClient() {
        return client;
    }

    public void setClient() {
        client = new Client();
    }

    public static GameMaster getGM() {
        return gameMaster;
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
