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
import java.util.Arrays;
import server.Server;
import server.ServerFrame;
import server.ThreadServer;

/**
 *
 * @author diemo
 */
public class AttackCommand extends BaseCommand implements Serializable {
    
    private String excecutionResult = "";

    public AttackCommand(String commandName, String[] args, Player player) {
        super(commandName, args, true, false, player);
    }

    // 0 attack 1 target 2 fighter 3 attacktype 4 instructions
    @Override
    public String executeOnServer() {
        String targetName = this.getArgs()[1];
        ThreadServer ts =  ServerFrame.getServer().getConnectionsByName().get(targetName);
//        ts.getPlayer().syncPlayer(playerExcecuting);
        excecutionResult = getPlayerExcecuting().attackWithFighter(ts, this.getArgs());
        ServerFrame.getServer().changeTurn();
//        String playerName = playerExcecuting.getPlayerName();
//        Player playerToSync = ServerFrame.getServer().getConnectionsByName().get(playerName).getPlayer();
//        playerExcecuting.syncPlayer(playerToSync);
//        ServerFrame.getServer().syncPlayerToThread(this.getPlayerExcecuting());
//        System.out.println("hp: " + getPlayerExcecuting().getHp());
//        System.out.println("left: " + getPlayerExcecuting().getCellsLeft());
        return excecutionResult;
    }

    @Override
    public String executeOnClient() {
        ClientManager.getCM().getMainScreen().getPlayer().syncPlayer(getPlayerExcecuting());
        ClientManager.getCM().getMainScreen().updateInfoPanels();
        ClientManager.getCM().getMainScreen().showLastAttack(excecutionResult);
        return excecutionResult;
    }
    
}
