/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commandsmanager.commands;

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
        super(commandName, args, true, false, player);
    }

    @Override
    public String executeOnServer() {
        System.out.println("Jugador: " + this.getPlayerExcecuting().getPlayerName() + " esta listo? " + getPlayerExcecuting().areFighersDone());
        if (getPlayerExcecuting().areFighersDone()) {
            getPlayerExcecuting().setReady(true);
        }
        if (ServerFrame.getServer().startGame()) {
            System.out.println("CELLS--------------------------------------------");
            for (Cell[] row : getPlayerExcecuting().getCells())
                for (Cell cell : row)
                    System.out.println(cell.getFighter());
            return "Todos los jugadores estan listos";
        }
        else
            return "Jugador " + getPlayerExcecuting().getPlayerName() + " esta listo para jugar, pero aun faltan jugadores para comenzar el juego";
    }

    @Override
    public String executeOnClient() {
        if (getPlayerExcecuting().isReady()) {
            System.out.println("CELLS--------------------------------------------");
            for (Cell[] row : getPlayerExcecuting().getCells())
                for (Cell cell : row)
                    System.out.println(cell.getFighter());
            return "Listo para jugar";
        }
        else
            return "Requiere como minimo 3 luchadores creados antes de comenzar la partida";
    }
    
}
