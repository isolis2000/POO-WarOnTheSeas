package commandsmanager.commands;

import client.ClientManager;
import commandsmanager.BaseCommand;
import gamelogic.Player;
import java.io.Serializable;
import server.ServerFrame;

public class StartGameCommand extends BaseCommand implements Serializable {

    public StartGameCommand(String commandName, String[] args, Player player) {
        super(commandName, args, true, false, player);//broadcast, localcommand
    }

    @Override
    public String executeOnServer() {
        if (!ServerFrame.getServer().isGameStarted()) {
            if (ServerFrame.getServer().startGame()) {
                this.gameStarted = true;
                return "Todos los jugadores estan listos";
            }
            else
                return "Jugador " + getPlayerExcecuting().getName() + " esta listo para jugar, pero aun faltan jugadores para comenzar el juego";
        } else
            return "Juego ya comenzo";
    }

    @Override
    public String executeOnClient() {
        if (!this.gameStarted) {
            if (getPlayerExcecuting().isReady()) {
                ClientManager.getCM().getMainScreen().updateInfoPanels();
                return "Listo para jugar";
            }
            else
                return "Requiere como minimo 3 luchadores creados antes de comenzar la partida";
        } else {
            ClientManager.getCM().getMainScreen().getPlayer().syncPlayer(playerExcecuting);
            ClientManager.getCM().getMainScreen().updateInfoPanels();
            return "Juego ya comenzo";
        }
    }
    
}
