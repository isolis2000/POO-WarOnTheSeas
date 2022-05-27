/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import commandsmanager.BaseCommand;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Message;
import gamelogic.Player;
import java.util.Arrays;

/**
 *
 * @author diemo
 */
public class ThreadServer extends Thread{
    private Socket socket;
    private Server server;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private Player player;
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
                System.exit(0);
            }
            try {
                System.out.println("previous read command");
                readCommand = (BaseCommand)this.reader.readObject();
//                player = readCommand.getPlayerExcecuting();
                System.out.println("CellsAfterW: " + Arrays.toString(player.getCells()));
                readCommand.setPlayerExcecuting(player);
//                player = readCommand.getPlayerExcecuting();
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
            else if (readCommand.isLocalCommand()){
                server.screenRef.showServerMessage(readCommand.executeOnServer());
                player = readCommand.getPlayerExcecuting();
                System.out.println("fighters after executing and equaling: " + player.getFighters().toString());
                server.sendToOne(readCommand, this);
            } else {
                String name = readCommand.getArgs()[1];
                server.screenRef.showServerMessage(readCommand.executeOnServer());
                for (HashMap.Entry<ThreadServer, Player> set : server.getPlayers().entrySet())
                    if (set.getValue().getPlayerName().equals(name))
                        server.sendToOne(readCommand, set.getKey());
            }
        }
        
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public ObjectOutputStream getWriter() {
        return writer;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }

    public ObjectInputStream getReader() {
        return reader;
    }

    public void setReader(ObjectInputStream reader) {
        this.reader = reader;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
    
    
    
}
