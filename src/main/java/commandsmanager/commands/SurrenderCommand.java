package commandsmanager.commands;

import client.ClientManager;
import commandsmanager.BaseCommand;
import gamelogic.Player;
import java.io.Serializable;
import server.ServerFrame;

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
        ClientManager.getCM().getMainScreen().getPlayer().syncPlayer(getPlayerExcecuting());
        ClientManager.getCM().getMainScreen().updateInfoPanels();
        return strToShare;
    }
    
}
