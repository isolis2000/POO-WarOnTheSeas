/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import commands.BaseCommand;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Message;

/**
 *
 * @author diemo
 */
public class Server extends Thread{
    private final int PORT = 35500;
    private final int MAX_CONNECTIONS = 6;
    private ServerSocket serverSoccket;
    private boolean isRunnig = true;
    private boolean isWaiting = false;
    private ArrayList<ThreadServer> connections;
    public ServerFrame screenRef;
    private HashMap<ThreadServer, String> playerNames = new HashMap<>();
    //
    public Server(ServerFrame screenRef){
        this.screenRef = screenRef;
        this.runServer();
    }

    public HashMap<ThreadServer, String> getPlayerNames() {
        return playerNames;
    }
    
    //mensaje para todos
    public void broadcast (BaseCommand command){
        
        for (ThreadServer connection : connections) {
            try {
                connection.writer.writeObject(command);
                //this.screenRef.showServerMessage(command.toString());
            } catch (IOException ex) {
                
            }
        }
        
    }
    
    public void sendToOne(BaseCommand command, ThreadServer ts) {
        try {
            ts.writer.writeObject(command);
        } catch (IOException ex) {
        }
    }
    
    public String toString(){
        String str = "Datos del servidor:\n";
        str += "Puerto: " + PORT + " \n";
        str += "Conexiones actuales: " + connections.size() +" de " + MAX_CONNECTIONS;
        return str;
    }
    
    private void runServer(){
        try {
            this.serverSoccket = new ServerSocket(PORT);
            this.connections = new ArrayList<ThreadServer>();
        } catch (IOException ex) {       
        }
    }
    
    public void run(){
        this.screenRef.showServerMessage("Server running!");
        this.screenRef.showServerMessage("Waiting for connections!");
        while (isRunnig){
            try {
                Socket newSocket = serverSoccket.accept();
                this.screenRef.showServerMessage("Nuevo cliente conectado"); 
                DataInputStream inStream = new DataInputStream(newSocket.getInputStream());
                System.out.println("1");
                String name = inStream.readUTF();
                System.out.println("name: " + name);
                ThreadServer newThread = new ThreadServer(newSocket, this); 
                System.out.println("3");
                playerNames.put(newThread, name);
                System.out.println("4");
                for (String set : playerNames.values())
                    System.out.println("player: " + set);
                newThread.start();
                System.out.println("5");
                connections.add(newThread);
                System.out.println("6");
                this.screenRef.showServerMessage("Nuevo thread creado, nombre del jugador: " + name);
                
                if (connections.size() >= MAX_CONNECTIONS){
                    isWaiting = true;
                }
                
                while(isWaiting){
                    sleep(1000);
                }
                
            } catch (IOException ex) {
                
            } catch (InterruptedException ex) {
                
            }
            
        
        }
    }
    
}
