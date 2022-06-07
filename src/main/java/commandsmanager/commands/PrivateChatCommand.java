package commandsmanager.commands;

import commandsmanager.BaseCommand;
import commandsmanager.CommandUtils;
import gamelogic.Player;
import java.io.Serializable;

public class PrivateChatCommand  extends BaseCommand implements Serializable{

    public PrivateChatCommand(String commandName, String[] args, Player player) {
        super(commandName, args, false, false, player);
    }

    @Override
    public String executeOnServer() {
        return "Para "+ getArgs()[1] + ": "+ CommandUtils.concatArray(getArgs());
    }

    @Override
    public String executeOnClient() {
        String[] newArgs = new String[getArgs().length-2];
        for (int i = 0; i < newArgs.length; i++)
            newArgs[i] = getArgs()[i+2];
        String ret = CommandUtils.concatArray(newArgs);
        return this.getPlayerExcecuting() + ": " + ret;
    }
 

    
}
