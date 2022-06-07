package commandsmanager.commands;

import commandsmanager.BaseCommand;
import gamelogic.Player;
import java.io.Serializable;

public class ErrorCommand  extends BaseCommand implements Serializable{
    
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
