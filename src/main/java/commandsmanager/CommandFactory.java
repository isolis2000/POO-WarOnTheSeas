/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandsmanager;

import commandsmanager.commands.PrivateChatCommand;
import commandsmanager.commands.CreateCharacterCommand;
import commandsmanager.commands.ErrorCommand;
import commandsmanager.commands.StartGameCommand;
import commandsmanager.commands.ChatCommand;


public class CommandFactory { 
    
    
    public static BaseCommand getCommand(String name, String[] args){
        
        return switch (name.toUpperCase()) {
            case "CHAT" -> new ChatCommand(name, args);
            case "CHAT PRIVADO" -> new PrivateChatCommand(name, args);
            case "CREAR PERSONAJE" -> new CreateCharacterCommand(name, args);
            case "INICIAR PARTIDA" -> new StartGameCommand(name, args);
            default -> new ErrorCommand("error", args);
        };
    }
}

