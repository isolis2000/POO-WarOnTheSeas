/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandsmanager;

import java.io.Serializable;
import gamelogic.Player;

/**
 *
 * @author diemo
 */
public abstract class BaseCommand implements iCommand, Serializable{
    private String[] args;
    private boolean broadcast, localCommand;
    private String commandName;
    private Player playerExcecuting;

    public BaseCommand(String commandName, String[] args, boolean broadcast, boolean localCommand) {
        this.args = args;
        this.broadcast = broadcast;
        this.localCommand = localCommand;
        this.commandName = commandName;
    }

    public boolean isLocalCommand() {
        return localCommand;
    }

    public Player getPlayerExcecuting() {
        return playerExcecuting;
    }

    public void setPlayerExcecuting(Player playerExcecuting) {
        this.playerExcecuting = playerExcecuting;
    }
    
    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getCommandName(){
        return this.commandName;
    }
    
    public boolean isBroadcast(){
        return broadcast;
    }
    
    
    public abstract String executeOnServer();
    public abstract String executeOnClient(); 
//    
//    @Override
//    public String toString() {
//        String ret = commandName;
//        ret += playerExcecuting;
//        return ret;
//    }    
    
}
