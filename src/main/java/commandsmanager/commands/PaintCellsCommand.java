package commandsmanager.commands;

import client.ClientManager;
import commandsmanager.BaseCommand;
import gamelogic.Player;
import java.io.Serializable;

public class PaintCellsCommand extends BaseCommand implements Serializable {

    public PaintCellsCommand(String commandName, String[] args, Player player) {
        super(commandName, args, false, true, player);
    }

    @Override
    public String executeOnServer() {
        String dataType = args[1];
        playerExcecuting.paintCells(dataType);
        return "Se pintaron las casillas del jugador " + playerExcecuting.getName();
    }

    @Override
    public String executeOnClient() {
        ClientManager.getCM().getMainScreen().getPlayer().syncPlayer(getPlayerExcecuting());
        return "Se pintaron las casillas";
    }
    
}
