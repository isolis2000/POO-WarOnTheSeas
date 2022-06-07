package commandsmanager.commands;

import commandsmanager.BaseCommand;
import commandsmanager.CommandUtils;
import gamelogic.Player;
import java.io.Serializable;

public class ChatCommand extends BaseCommand implements Serializable{

    public ChatCommand(String commandName, String[] args, Player player) {
        super(commandName, args, true, false, player);
    }
    @Override
    public String executeOnServer() {
        return "Enviado: " + CommandUtils.concatArray(getArgs());
    }

    @Override
    public String executeOnClient() {
        String[] newArgs = new String[getArgs().length-1];
        for (int i = 0; i < newArgs.length; i++)
            newArgs[i] = getArgs()[i+1];
        return this.getPlayerExcecuting().getName() + ": " + CommandUtils.concatArray(newArgs);
    }
    
    
    
}
