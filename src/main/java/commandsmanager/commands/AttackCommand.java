/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandsmanager.commands;

import client.ClientManager;
import commandsmanager.BaseCommand;
import commandsmanager.CommandUtils;
import commandsmanager.CommandUtils;
import gamelogic.Player;
import java.io.Serializable;
import server.Server;
import server.ServerFrame;
import server.ThreadServer;

/**
 *
 * @author diemo
 */
public class AttackCommand extends BaseCommand implements Serializable{
    
    private String excecutionResult = "";

    public AttackCommand(String commandName, String[] args, Player player) {
        super(commandName, args, true, false, player);
    }

    // 0 attack 1 target 2 fighter 3 attacktype 4 instructions
    @Override
    public String executeOnServer() {
        String targetName = this.getArgs()[1];
        ThreadServer ts =  ServerFrame.getServer().getConnectionsByName().get(targetName);
        excecutionResult = getPlayerExcecuting().attackWithFighter(ts, this.getArgs());
//        ServerFrame.getServer().syncPlayerToThread(this.getPlayerExcecuting());
        return excecutionResult;
    }

    @Override
    public String executeOnClient() {
        ClientManager.getCM().getMainScreen().updateCells(getPlayerExcecuting().getCells());
        return "Jugador ataco";
    }
    
    
    
}
