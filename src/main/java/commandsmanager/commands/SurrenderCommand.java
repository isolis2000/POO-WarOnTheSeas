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
            ServerFrame.getServer().playerSurrender(playerExcecuting);
        }
        return strToShare;        
    }

    @Override
    public String executeOnClient() {
        boolean wasDead = ClientManager.getCM().getMainScreen().getPlayer().isDead();
        ClientManager.getCM().getMainScreen().getPlayer().syncPlayer(getPlayerExcecuting());
        ClientManager.getCM().getMainScreen().updateInfoPanels();
        if (!wasDead && playerExcecuting.isDead())
            ClientManager.getCM().getMainScreen().showPopup("Usted ha perdido");
        else if (playerExcecuting.isWinner()) {
            String str = "Jugador " + playerExcecuting.getName() + " es el ganador!"
                    + " Cuando cierre esta venta terminara el juego.";
            ClientManager.getCM().getMainScreen().showPopup(str);
            System.exit(0);
        }
        return strToShare;
    }
    
}
