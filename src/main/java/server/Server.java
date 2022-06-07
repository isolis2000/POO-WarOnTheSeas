package server;

import commandsmanager.BaseCommand;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import gamelogic.Player;
import java.io.ObjectInputStream;
import java.util.Random;

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
    private ArrayList<String> attackCommands = new ArrayList<>();
    
    public Server(ServerFrame screenRef){
        this.screenRef = screenRef;
        this.logs = new ArrayList<>();
        initAttackCommands();
        this.runServer();
    }
    
    private void initAttackCommands() {
        attackCommands.add("ATAQUE");
        attackCommands.add("RENDIRSE");
        attackCommands.add("SALTAR TURNO");
    }

    public HashMap<ThreadServer, Player> getPlayers() {
        return players;
    }
    
    public void addToLogs(String str) {
        logs.add(str);
    }

    public ArrayList<String> getLogs() {
        return logs;
    }
    
    public String getLogsString() {
        String log = "- ";
        for (String str : logs)
            log += str + "\n\n-";
        return log;
    }
    
    public boolean playerSurrender(Player player) {
        for (ThreadServer ts : connections)
            if (ts.getPlayer().getName().equals(player.getName())) {
                ts.setPlayerLost(true);
            }
        return connections.size() == 1;
    }
    
    public String getWinner() {
        return connections.get(0).getPlayer().getName();
    }
    
    public Player getPlayerByName(String name) {
        ThreadServer ts = connectionsByName.get(name);
        return ts.getPlayer();
    }
    
    public void syncPlayerToThread(Player player) {
        ThreadServer ts = connectionsByName.get(player.getName());
        ts.getPlayer().syncPlayer(player);
    }
    
    private void attackBroadcast (BaseCommand command){
        String firstExecName = "";
        for (ThreadServer connection : connections) {
            if (connection.getPlayer().isTurn()) {
                firstExecName = connection.getPlayer().getName();
                try {
                    connection.getWriter().reset();
                    command.getPlayerExcecuting().syncPlayer(connection.getPlayer());
                    this.screenRef.showServerMessage(command.executeOnServer());
                    command.getPlayerExcecuting().setTurn(connection.getPlayer().isTurn());
                    System.out.println("To SEND ------------------ 1 ");
                    System.out.println("turn: " + connection.getPlayer().isTurn());
                    System.out.println(command.toString());
                    System.out.println("connection: " + connection.getPlayer());
                    connection.getWriter().writeObject(command);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
        for (ThreadServer connection : connections) {
            if (!connection.getPlayer().getName().equals(firstExecName)) {
                try {
                    connection.getWriter().reset();
                    command.setPlayerExcecuting(connection.getPlayer());
                    System.out.println("To SEND ------------------ 2 ");
                    System.out.println("turn: " + connection.getPlayer().isTurn());
                    System.out.println(command.toString());
                    System.out.println("connection: " + connection.getPlayer());
                    connection.getWriter().writeObject(command);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public void broadcast(BaseCommand command) {
        if (attackCommands.contains(command.getCommandName().toUpperCase())) {
            attackBroadcast(command);
        } else {
            for (ThreadServer connection : connections) {
                try {
                    connection.getWriter().reset();
                    command.setPlayerExcecuting(connection.getPlayer());
                    this.screenRef.showServerMessage(command.executeOnServer());
                    command.getPlayerExcecuting().setTurn(connection.getPlayer().isTurn());
                    System.out.println("To SEND ------------------ 1 ");
                    System.out.println("turn: " + connection.getPlayer().isTurn());
                    System.out.println(command.toString());
                    System.out.println("connection: " + connection.getPlayer());
                    connection.getWriter().writeObject(command);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            
        }
    }
    
    public void sendToOne(BaseCommand command, ThreadServer ts) {
        try {
            System.out.println("To SEND FOR ONE ---------------------------");
            System.out.println(command.toString());
            command.getPlayerExcecuting().syncPlayer(getPlayerByName(ts.getPlayer().getName()));
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
        if (!gameStarted) {
            System.out.println("entro");
            Random random = new Random();
            if (!areAllPlayersReady())
                return false;
            connections.get(random.nextInt(connections.size())).getPlayer().setTurn(true);
            for (ThreadServer connection : connections)
                if (connection.getPlayer().isTurn())
                    System.out.println("Player: " + connection.getPlayer().getName() 
                            + " with turn: " + connection.getPlayer().isTurn());
                else
                    System.out.println("todos f");
            gameStarted = true;
        }
        return gameStarted;
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
                    ThreadServer current = connections.get(i);
                    ThreadServer nextTs = getNextPlayer(current);
                    if (!nextTs.equals(connections.get(i))) {
                        current.getPlayer().setTurn(false);
                        nextTs.getPlayer().setTurn(true);
                        break;
                    }
                }
            }
        }
    }
    
    private ThreadServer getNextPlayer(ThreadServer current) {
        ArrayList<ThreadServer> localCopy = new ArrayList<>();
        localCopy.addAll(connections);
        for (ThreadServer ts : connections)
            if (!ts.equals(current) && ts.isPlayerLost())
                localCopy.remove(ts);
        int size = localCopy.size();
        if (size <= 1)
            return current;
        else {
            for (int i = 0; i < size; i++)
                if (localCopy.get(i).equals(current)) {
                    if (i + 1 == size)
                        return localCopy.get(0);
                    else
                        return localCopy.get(i + 1);
                }
        }
        return null;
    }
    
    public void run(){
        this.screenRef.showServerMessage("Server running!");
        this.screenRef.showServerMessage("Waiting for connections!");
        while (isRunnig){
            try {
                Socket newSocket = serverSoccket.accept();
                this.screenRef.showServerMessage("Nuevo cliente conectado"); 
                ObjectInputStream inStream = new ObjectInputStream(newSocket.getInputStream());
                Player player = (Player)inStream.readObject();
                ThreadServer newThread = new ThreadServer(newSocket, this, player); 
                newThread.start();
                connections.add(newThread);
                connectionsByName.put(player.getName(), newThread);
                this.screenRef.showServerMessage("Nuevo thread creado, nombre del jugador: " + player.getName());
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
