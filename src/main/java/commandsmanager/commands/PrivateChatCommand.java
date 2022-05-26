/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandsmanager.commands;

import commandsmanager.BaseCommand;
import commandsmanager.CommandUtils;
import commandsmanager.CommandUtils;
import java.io.Serializable;

/**
 *
 * @author diemo
 */
public class PrivateChatCommand  extends BaseCommand implements Serializable{

    public PrivateChatCommand(String commandName, String[] args) {
        super(commandName, args, false, false);
    }

    // Diego Hola como estas?
    @Override
    public String executeOnServer() {
        return "Para "+ getArgs()[1] + ": "+ CommandUtils.concatArray(getArgs());
    }

    @Override
    public String executeOnClient() {
        System.out.println("entra aqui");
        String[] newArgs = new String[getArgs().length-2];
        for (int i = 0; i < newArgs.length; i++)
            newArgs[i] = getArgs()[i+2];
        String ret = CommandUtils.concatArray(newArgs);
        System.out.println("ret: " + ret);
        return this.getPlayerExcecuting() + ": " + ret;
    }
 

    
}
