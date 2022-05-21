/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Commands.BaseCommand;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
    //
    public Server(ServerFrame screenRef){
        this.screenRef = screenRef;
        this.runServer();
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
                ThreadServer newThread = new ThreadServer(newSocket, this);  
                newThread.start();
                connections.add(newThread);
                this.screenRef.showServerMessage("Nuevo thread creado");
                
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
