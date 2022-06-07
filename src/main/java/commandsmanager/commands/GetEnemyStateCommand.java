package commandsmanager.commands;

import client.ClientManager;
import commandsmanager.BaseCommand;
import gamelogic.Player;
import java.io.Serializable;
import server.ServerFrame;

public class GetEnemyStateCommand extends BaseCommand implements Serializable {

    public GetEnemyStateCommand(String commandName, String[] args, Player player) {
        super(commandName, args, false, true, player);
    }
    
    @Override
    public String executeOnServer() {
        String targetName = this.getArgs()[1];
        Player targetP =  ServerFrame.getServer().getPlayerByName(targetName);
        strToShare = targetP.getState();
        return "Se le envio el estado del jugador " + targetName + " al jugador " + playerExcecuting.getName();
    }

    @Override
    public String executeOnClient() {
        ClientManager.getCM().getMainScreen().showPopup(strToShare);
        return "La informacion se podra ver en el popup";
    }
    
}
