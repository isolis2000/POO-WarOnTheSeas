package commandsmanager.commands;

import client.ClientManager;
import commandsmanager.BaseCommand;
import gamelogic.Player;
import java.io.Serializable;
import server.ServerFrame;

public class SkipTurnCommand extends BaseCommand implements Serializable {
    
    
    public SkipTurnCommand(String commandName, String[] args, Player player) {
        super(commandName, args, true, false, player);
    }
    
    @Override
    public String executeOnServer() {
        if (strToShare == null) {
            ServerFrame.getServer().changeTurn();
            strToShare = "Jugador " + playerExcecuting.getName() + " ha saltado su turno.";
        }
        return strToShare;
    }

    @Override
    public String executeOnClient() {
        ClientManager.getCM().getMainScreen().getPlayer().syncPlayer(getPlayerExcecuting());
        ClientManager.getCM().getMainScreen().updateInfoPanels();
        return strToShare;
    }
    
}
