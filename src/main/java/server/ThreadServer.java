/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import commands.BaseCommand;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Message;
import gamelogic.Player;

/**
 *
 * @author diemo
 */
public class ThreadServer extends Thread{
    Socket socket;
    Server server;
    ObjectOutputStream writer;
    ObjectInputStream reader;
    Player player;
    private boolean isRunning = true;

    public ThreadServer(Socket socket, Server server, Player player) {
        this.socket = socket;
        this.server = server;
        this.player = player;
        this._init_();
    }
    
    private void _init_(){
        try {
            this.writer = new ObjectOutputStream(socket.getOutputStream());
            this.reader = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {         
        }
    }
    
    public void run(){
        System.out.println("no");
        BaseCommand readCommand = null;
        while (isRunning) {
            System.out.println("yes");
            try {
                writer.reset();
            } catch (IOException ex) {
                Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                System.out.println("previous read command");
                readCommand = (BaseCommand)this.reader.readObject();
                readCommand.setPlayerExcecuting(player);
//                System.out.println("map: ");
//                System.out.println(server.getPlayerNames().get(this));
                //System.out.println("read command");
            } catch (IOException ex) { 
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());}
            
            if (readCommand.isBroadcast()){
                server.broadcast(readCommand);
            }
            else if (readCommand.getCommandName().equals("CREARPERSONAJE")){
                server.screenRef.showServerMessage(readCommand.executeOnServer());
                server.sendToOne(readCommand, this);
            } else {
                String name = readCommand.getArgs()[1];
                for (HashMap.Entry<ThreadServer, Player> set : server.getPlayers().entrySet())
                    if (set.getValue().getPlayerName().equals(name))
                        server.sendToOne(readCommand, set.getKey());
                server.screenRef.showServerMessage(readCommand.executeOnServer());
            }
        }
        
    }
    
    
    
}
