/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commandsmanager.commands;

import client.ClientManager;
import commandsmanager.BaseCommand;
import gamelogic.Player;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ServerFrame;

/**
 *
 * @author ivan
 */
public class SurrenderCommand extends BaseCommand implements Serializable {
    

    public SurrenderCommand(String commandName, String[] args, Player player) {
        super(commandName, args, true, false, player);
    }

    @Override
    public String executeOnServer() {
        if (strToShare == null) {
            ServerFrame.getServer().changeTurn();
            strToShare = "Jugador " + playerExcecuting.getName() + " se ha rendido. ";
            if (ServerFrame.getServer().playerSurrender(playerExcecuting))
                strToShare += ServerFrame.getServer().getWinner() + " ha ganado!";
        }
        return strToShare;        
    }

    @Override
    public String executeOnClient() {
//        ClientManager.getCM().getMainScreen().showPopup(strToShare);
//        ClientManager.getCM().getMainScreen().getPlayer().setTurn(false);
        ClientManager.getCM().getMainScreen().getPlayer().syncPlayer(getPlayerExcecuting());
        ClientManager.getCM().getMainScreen().updateInfoPanels();
        return strToShare;
    }
    
}
