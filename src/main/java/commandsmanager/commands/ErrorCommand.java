/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandsmanager.commands;

import commandsmanager.BaseCommand;
import gamelogic.Player;
import java.io.Serializable;

/**
 *
 * @author diemo
 */
public class ErrorCommand  extends BaseCommand implements Serializable{
    
    

    // Hola como estan
    @Override
    public String executeOnServer() {
        return "Error: commando desconocido";
    }

    @Override
    public String executeOnClient() {
       return "Error: commando desconocido";
    }

    public ErrorCommand(String commandName, String[] args, Player player) {
        super(commandName, args, false, true, player);
    }

    
    
}
