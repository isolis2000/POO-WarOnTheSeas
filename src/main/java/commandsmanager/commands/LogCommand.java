package commandsmanager.commands;

import client.ClientManager;
import commandsmanager.BaseCommand;
import gamelogic.Player;
import java.io.Serializable;
import server.ServerFrame;

public class LogCommand extends BaseCommand implements Serializable {

    public LogCommand(String commandName, String[] args, Player player) {
        super(commandName, args, false, true, player);
    }

    @Override
    public String executeOnServer() {
        this.setStrToShare(ServerFrame.getServer().getLogsString());
        return "Se envio el registro al jugador " + this.getPlayerExcecuting().getName();
    }

    @Override
    public String executeOnClient() {
        ClientManager.getCM().getMainScreen().showScrollablePopup(this.getStrToShare());
        return "El registro se abrira en un popup";
    }
    
}
