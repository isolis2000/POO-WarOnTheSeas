/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import client.gui.MainScreen;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diemo
 */
public class Client {
    private final int PORT = 35500;
    private final String SERVER_IP = "localhost";
    public Socket socket;
    public ObjectOutputStream writer;
    private DataOutputStream outStream;
    private boolean isConnected = false;
    private ThreadClient theadClient;
    
    public void connect(String name){
        try {
            if (!isConnected){
                this.socket = new Socket(SERVER_IP, PORT);
//                this.writer = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("1");
                this.outStream = new DataOutputStream(socket.getOutputStream());
                System.out.println("2");
                GameMaster.getGM().getMainScreen().showClientMessage(name);
                System.out.println("3");
                outStream.writeUTF(name);
                System.out.println("4");
                this.theadClient = new ThreadClient();
                System.out.println("5");
                this.theadClient.start();
                System.out.println("6");
                this.isConnected = true;
                System.out.println("7");
            }
            //threadClient
        } catch (IOException ex) {
            
        }
    }
    
    
}
