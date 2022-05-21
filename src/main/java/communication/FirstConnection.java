/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.GameMaster;

/**
 *
 * @author ivan
 */
public class FirstConnection implements Runnable {

    
    private Socket client;
    private ServerSocket initialServerSocket;

    public FirstConnection() {
        try {
            initialServerSocket = new ServerSocket(GameMaster.getGameMaster().getFirstPort());
        } catch (IOException ex) {
            System.out.println("Initial Server Socket failed to initialize");
        }
    }
    
    @Override
    public void run() {
        while (GameMaster.getGameMaster().getPlayers().getPlayerList().size() <= 6) {
            try {
                client = initialServerSocket.accept();
                System.out.println("New player connected");
                client.setSoLinger(true, 10);//in
                DataInputStream inStream = new DataInputStream(client.getInputStream());
                String name = inStream.readUTF();//out
                DataOutputStream outStream = new DataOutputStream(client.getOutputStream());
                int nextPort = GameMaster.getGameMaster().getFirstPort() 
                        + GameMaster.getGameMaster().getServer().getServerSockets().size() + 1;
                GameMaster.getGameMaster().getServer().addPlayerPort();
                outStream.writeInt(nextPort);
                System.out.println("Port " + nextPort + " was sent to client");
                GameMaster.getGameMaster().getPlayers().addPlayer(name);
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(FirstConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }   
}