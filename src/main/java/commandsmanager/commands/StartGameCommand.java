/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commandsmanager.commands;

import commandsmanager.BaseCommand;
import java.io.Serializable;
import server.ServerManager;

/**
 *
 * @author ivan
 */
public class StartGameCommand extends BaseCommand implements Serializable {

    public StartGameCommand(String commandName, String[] args) {
        super(commandName, args, false, true);
    }

    @Override
    public String executeOnServer() {
        if (getPlayerExcecuting().areFighersDone()) {
            getPlayerExcecuting().setReady(true);
        }
        if (ServerManager.getSM().getServer().startGame())
            return "Todos los jugadores estan listos";
        else
            return "Jugador " + getPlayerExcecuting().getPlayerName() + " esta listo para jugar, pero aun faltan jugadores para comenzar el juego";
    }

    @Override
    public String executeOnClient() {
        if (getPlayerExcecuting().isReady())
            return "Listo para jugar";
        else
            return "Requiere como minimo 3 luchadores creados antes de comenzar la partida";
    }
    
}
