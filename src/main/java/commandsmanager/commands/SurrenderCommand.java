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
        String ret = "Jugador " + playerExcecuting.getName() + " se ha rendido. ";
        if (ServerFrame.getServer().playerSurrender(playerExcecuting))
            ret += ServerFrame.getServer().getWinner() + " ha ganado!";
        return ret;        
    }

    @Override
    public String executeOnClient() {
        ClientManager.getCM().getMainScreen().showPopup("Usted ha perdido!");
        try {
            ClientManager.getCM().getThreadClient().getWriter().close();
        } catch (IOException ex) {System.out.println("ex");}
        return "Usted se ha rendido.";
    }
    
}
