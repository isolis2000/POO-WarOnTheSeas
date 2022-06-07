package client;

import gamelogic.Player;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private final int PORT = 35501;
    private final String SERVER_IP = "localhost";
    public Socket socket;
    private ObjectOutputStream outStream;
    private boolean isConnected = false;
    private ThreadClient threadClient;
    
    public void connect(Player player){
        try {
            if (!isConnected){
                this.socket = new Socket(SERVER_IP, PORT);
                this.outStream = new ObjectOutputStream(socket.getOutputStream());
                ClientManager.getCM().getMainScreen().showClientMessage(player.getName());
                outStream.writeObject(player);
                this.threadClient = new ThreadClient();
                this.threadClient.start();
                this.isConnected = true;
            }
        } catch (IOException ex) {
            
        }
    }    
    
}
