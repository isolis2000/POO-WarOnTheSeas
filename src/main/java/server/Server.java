/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import commandsmanager.BaseCommand;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Message;
import gamelogic.Player;
import java.io.ObjectInputStream;
import java.util.Collections;

/**
 *
 * @author diemo
 */
public class Server extends Thread{
    private final int PORT = 35501;
    private final int MAX_CONNECTIONS = 6;
    private ServerSocket serverSoccket;
    private boolean isRunnig = true;
    private boolean isWaiting = false;
    private ArrayList<ThreadServer> connections;
    public ServerFrame screenRef;
    private HashMap<ThreadServer, Player> players = new HashMap<>();
    private HashMap<String, ThreadServer> connectionsByName = new HashMap<>();
    private boolean gameStarted = false;
    private ArrayList<String> logs;
//    private HashMap<String, Player> players = new HashMap<>();
    //
    public Server(ServerFrame screenRef){
        this.screenRef = screenRef;
        this.logs = new ArrayList<>();
        this.runServer();
    }

    public HashMap<ThreadServer, Player> getPlayers() {
        return players;
    }
    
    public void addToLogs(String str) {
        logs.add(str);
        System.out.println("new logs: ");
        System.out.println();
    }

    public ArrayList<String> getLogs() {
        return logs;
    }
    
    public String getLogsString() {
        String log = "";
        for (String str : logs)
            log += "\n" + str;
        return log;
    }
    
    public void syncPlayerToThread(Player player) {
        ThreadServer ts = connectionsByName.get(player.getPlayerName());
        players.replace(ts, player);
    }
    
    //mensaje para todos
    public void broadcast (BaseCommand command){
        for (ThreadServer connection : connections) {
            try {
//                System.out.println("broadcastasdlfkn" + command.toString());
//                System.out.println("connections size: " + connections.size());
//                System.out.println("connectionasdfasd: " + connection);
//                System.out.println("Connection: " + connection.connection.getPlayer().getPlayerName());
//                System.out.println("Object: " + Arrays.toString(command.getArgs()));
//                System.out.println("Object1: " + command);
//                System.out.println("writer: " + getWriter());
                this.screenRef.showServerMessage(command.executeOnServer());
//                this.screenRef.showServerMessage(Integer.toString(command.getPlayerExcecuting().getFighters().size()));
                System.out.println("To SEND ------------------");
                System.out.println(command.toString());
                System.out.println("connection: " + connection.getPlayer());
                command.setPlayerExcecuting(connection.getPlayer());
                connection.getWriter().writeObject(command);
                connection.getWriter().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    public void sendToOne(BaseCommand command, ThreadServer ts) {
        try {
            System.out.println("Hello");
            System.out.println(command.toString());
            ts.getWriter().writeObject(command);
        } catch (IOException ex) {
        }
    }
    
    public String toString(){
        String str = "Datos del servidor:\n";
        str += "Puerto: " + PORT + " \n";
        str += "Conexiones actuales: " + connections.size() +" de " + MAX_CONNECTIONS;
        return str;
    }
    
    public boolean startGame() {
        if (!areAllPlayersReady())
            return false;
        Collections.shuffle(connections);
        connections.get(0).getPlayer().setTurn(true);
        gameStarted = true;
        return true;
    }
    
    private boolean areAllPlayersReady() {
        for (ThreadServer connection : connections)
            if (!connection.getPlayer().isReady())
                return false;
        return true;
    }
    
    private void runServer(){
        try {
            this.serverSoccket = new ServerSocket(PORT);
            this.connections = new ArrayList<ThreadServer>();
        } catch (IOException ex) {       
        }
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public HashMap<String, ThreadServer> getConnectionsByName() {
        return connectionsByName;
    }
    
    public void changeTurn() {
        if (connections.size() != 1) {
            for (int i = 0; i < connections.size(); i++) {
                if (connections.get(i).getPlayer().isTurn()) {
                    connections.get(i).getPlayer().setTurn(false);
                    if (i + 1 == connections.size())
                        connections.get(0).getPlayer().setTurn(true);
                    else
                        connections.get(i + 1).getPlayer().setTurn(true);
                    return;
                }
            }
        }
    }
    
    public void run(){
        this.screenRef.showServerMessage("Server running!");
        this.screenRef.showServerMessage("Waiting for connections!");
        while (isRunnig){
            try {
                Socket newSocket = serverSoccket.accept();
                this.screenRef.showServerMessage("Nuevo cliente conectado"); 
                ObjectInputStream inStream = new ObjectInputStream(newSocket.getInputStream());
//                System.out.println("1");
                Player player = (Player)inStream.readObject();
//                System.out.println("name: " + name);
                ThreadServer newThread = new ThreadServer(newSocket, this, player); 
//                System.out.println("3");
                players.put(newThread, player);
//                System.out.println("4");
//                for (String set : playerNames.values())
//                    System.out.println("player: " + set);
                newThread.start();
//                System.out.println("5");
                connections.add(newThread);
                connectionsByName.put(player.getPlayerName(), newThread);
                System.out.println("Thread in: " + newThread.getPlayer().getPlayerName());
//                System.out.println("6");
                this.screenRef.showServerMessage("Nuevo thread creado, nombre del jugador: " + player.getPlayerName());
                
                if (connections.size() >= MAX_CONNECTIONS){
                    isWaiting = true;
                }
                
                while(isWaiting){
                    sleep(1000);
                }
                
            } catch (IOException ex) {
                
            } catch (InterruptedException ex) {
                
            } catch (ClassNotFoundException ex) {
                
            }
            
        
        }
    }
    
}
