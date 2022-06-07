package commandsmanager;

import commandsmanager.commands.AttackCommand;
import commandsmanager.commands.CellInfoCommand;
import commandsmanager.commands.PrivateChatCommand;
import commandsmanager.commands.CreateCharacterCommand;
import commandsmanager.commands.ErrorCommand;
import commandsmanager.commands.StartGameCommand;
import commandsmanager.commands.ChatCommand;
import commandsmanager.commands.GetEnemyStateCommand;
import commandsmanager.commands.LogCommand;
import commandsmanager.commands.PaintCellsCommand;
import commandsmanager.commands.SkipTurnCommand;
import commandsmanager.commands.SurrenderCommand;
import gamelogic.Player;


public class CommandFactory { 
    
    
    public static BaseCommand getCommand(String name, String[] args, Player player){
        return switch (name.toUpperCase()) {
            case "CHAT" -> new ChatCommand(name, args, player);
            case "CHAT PRIVADO" -> new PrivateChatCommand(name, args, player);
            case "CREAR PERSONAJE" -> new CreateCharacterCommand(name, args, player);
            case "INICIAR PARTIDA" -> new StartGameCommand(name, args, player);
            case "ATAQUE" -> new AttackCommand(name, args, player);
            case "LOG" -> new LogCommand(name, args, player);
            case "CONSULTAR CELDA" -> new CellInfoCommand(name, args, player);
            case "RENDIRSE" -> new SurrenderCommand(name, args, player);
            case "CONSULTAR ENEMIGO" -> new GetEnemyStateCommand(name, args, player);
            case "PINTAR" -> new PaintCellsCommand(name, args, player);
            case "SALTAR TURNO" -> new SkipTurnCommand(name, args, player);
            default -> new ErrorCommand("error", args, player);
        };
    }
}

