/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commandsmanager.commands;

import client.ClientManager;
import commandsmanager.BaseCommand;
import gamelogic.Player;
import java.io.Serializable;
import server.ServerFrame;

/**
 *
 * @author ivan
 */
public class LogCommand extends BaseCommand implements Serializable {

    public LogCommand(String commandName, String[] args, Player player) {
        super(commandName, args, false, true, player);
    }

    @Override
    public String executeOnServer() {
        System.out.println("logs on server: " + ServerFrame.getServer().getLogsString());
        this.setStrToShare(ServerFrame.getServer().getLogsString());
        return "Se envio el registro al jugador " + this.getPlayerExcecuting().getPlayerName();
    }

    @Override
    public String executeOnClient() {
        ClientManager.getCM().getMainScreen().showPopup(this.getStrToShare());
        return "El registro se abrira en un popup";
    }
    
}
