package server;

import commandsmanager.BaseCommand;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import gamelogic.Player;

public class ThreadServer extends Thread{
    private Socket socket;
    private Server server;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private Player player;
    private boolean isRunning = true;
    private boolean playerLost = false;

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
        BaseCommand readCommand = null;
        while (isRunning) {
            try {
                writer.reset();
            } catch (IOException ex) {
                System.exit(0);
            }
            try {
                readCommand = (BaseCommand)this.reader.readObject();
                player = readCommand.getPlayerExcecuting();
            } catch (IOException ex) { 
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());}
            
            if (readCommand.isBroadcast()){
                server.broadcast(readCommand);
            }
            else if (readCommand.isLocalCommand()){
                server.screenRef.showServerMessage(readCommand.executeOnServer());
                server.sendToOne(readCommand, this);
            } else {
                String name = readCommand.getArgs()[1];
                server.screenRef.showServerMessage(readCommand.executeOnServer());
                for (HashMap.Entry<ThreadServer, Player> set : server.getPlayers().entrySet())
                    if (set.getValue().getName().equals(name))
                        server.sendToOne(readCommand, set.getKey());
            }
        }
        
    }

    public boolean isPlayerLost() {
        return playerLost;
    }

    public void setPlayerLost(boolean playerLost) {
        this.playerLost = playerLost;
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
