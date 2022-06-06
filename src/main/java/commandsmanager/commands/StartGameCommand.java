/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commandsmanager.commands;

import client.ClientManager;
import client.gui.Cell;
import commandsmanager.BaseCommand;
import gamelogic.Player;
import java.io.Serializable;
import server.ServerFrame;

/**
 *
 * @author ivan
 */
public class StartGameCommand extends BaseCommand implements Serializable {

    public StartGameCommand(String commandName, String[] args, Player player) {
        super(commandName, args, true, false, player);//broadcast, localcommand
    }

    @Override
    public String executeOnServer() {
//        System.out.println("Jugador: " + this.getPlayerExcecuting().getPlayerName() + " esta listo? " + getPlayerExcecuting().areFighersDone());
        if (!ServerFrame.getServer().isGameStarted()) {
            if (getPlayerExcecuting().areFighersDone()) {
                getPlayerExcecuting().setReady(true);
            }
            if (ServerFrame.getServer().startGame()) {
                this.gameStarted = true;
//                String playerName = playerExcecuting.getPlayerName();
//                this.playerExcecuting = ServerFrame.getServer().getPlayers().get(ServerFrame.getServer().getConnectionsByName().get(playerName));
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
            ClientManager.getCM().getMainScreen().updateInfoPanels();
            return "Juego ya comenzo";
        }
    }
    
}
