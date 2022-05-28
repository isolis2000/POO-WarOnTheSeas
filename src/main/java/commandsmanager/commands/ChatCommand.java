/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandsmanager.commands;

import commandsmanager.BaseCommand;
import commandsmanager.CommandUtils;
import commandsmanager.CommandUtils;
import gamelogic.Player;
import java.io.Serializable;

/**
 *
 * @author diemo
 */
public class ChatCommand extends BaseCommand implements Serializable{

    public ChatCommand(String commandName, String[] args, Player player) {
        super(commandName, args, true, false, player);
    }

    // Hola como estan
    @Override
    public String executeOnServer() {
        return "Enviado: " + CommandUtils.concatArray(getArgs());
    }

    @Override
    public String executeOnClient() {
        System.out.println("entro");
        String[] newArgs = new String[getArgs().length-1];
        for (int i = 0; i < newArgs.length; i++)
            newArgs[i] = getArgs()[i+1];
        return this.getPlayerExcecuting().getPlayerName() + ": " + CommandUtils.concatArray(newArgs);
    }
    
    
    
}
