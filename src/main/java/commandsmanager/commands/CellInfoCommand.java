package commandsmanager.commands;

import client.ClientManager;
import commandsmanager.BaseCommand;
import gamelogic.Player;
import java.io.Serializable;

public class CellInfoCommand extends BaseCommand implements Serializable {

    public CellInfoCommand(String commandName, String[] args, Player player) {
        super(commandName, args, false, true, player);
    }

    @Override
    public String executeOnServer() {
        int x = -1, y = -1;
        try {
            for (int i = 1; i < args.length; i++) {
                switch (args[i].toLowerCase()) {
                    case "x" -> x = Integer.parseInt(args[i + 1]) - 1;
                    case "y" -> y = Integer.parseInt(args[i + 1]) - 1;
                    default -> { 
                            throw new NumberFormatException();
                    }
                }
                i++;
            }
            this.strToShare = playerExcecuting.getCells()[x][y].getRecordStr();
            return "Se le envio la informacion de la celda al jugador: " 
                    + playerExcecuting.getName();
        } catch (NumberFormatException ex) {
            return "ERROR";
        }
    }

    @Override
    public String executeOnClient() {
        ClientManager.getCM().getMainScreen().showScrollablePopup(strToShare);
        return "Se imprimio la informacion de la celda";
    }
    
}
