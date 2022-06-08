package commandsmanager.commands;

import client.ClientManager;
import commandsmanager.BaseCommand;
import gamelogic.Player;
import java.io.Serializable;
import server.ServerFrame;
import server.ThreadServer;

public class AttackCommand extends BaseCommand implements Serializable {
    
    private String excecutionResult = "";

    public AttackCommand(String commandName, String[] args, Player player) {
        super(commandName, args, true, false, player);
    }

    // 0 attack 1 target 2 fighter 3 attacktype 4 instructions
    @Override
    public String executeOnServer() {
        String targetName = this.getArgs()[1];
        ThreadServer ts =  ServerFrame.getServer().getConnectionsByName().get(targetName);
        excecutionResult = getPlayerExcecuting().attackWithFighter(ts, this.getArgs());
        ServerFrame.getServer().changeTurn();
        return excecutionResult;
    }

    @Override
    public String executeOnClient() {
        boolean wasDead = ClientManager.getCM().getMainScreen().getPlayer().isDead();
        ClientManager.getCM().getMainScreen().getPlayer().syncPlayer(getPlayerExcecuting());
        ClientManager.getCM().getMainScreen().updateInfoPanels();
        ClientManager.getCM().getMainScreen().showLastAttack(excecutionResult);
        if (!wasDead && playerExcecuting.isDead())
            ClientManager.getCM().getMainScreen().showPopup("Usted ha perdido");
        else if (playerExcecuting.isWinner()){
            String str = "Jugador " + playerExcecuting.getName() + " es el ganador!"
                    + " Cuando cierre esta venta terminara el juego.";
            ClientManager.getCM().getMainScreen().showPopup(str);
            System.exit(0);
        }
        return excecutionResult;
    }
    
}
