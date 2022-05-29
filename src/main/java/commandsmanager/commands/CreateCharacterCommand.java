package commandsmanager.commands;

import client.Client;
import client.ClientManager;
import commandsmanager.BaseCommand;
import commandsmanager.CommandUtils;
import java.io.Serializable;
import java.util.Arrays;
import gamelogic.Player;
import java.util.HashMap;

/**
 *
 * @author ivan
 */
public class CreateCharacterCommand extends BaseCommand implements Serializable {
    
    private transient HashMap<String, Integer> fighterTypes;

    public CreateCharacterCommand(String commandName, String[] args, Player player) {
        super(commandName, args, false, true, player);
    }

    @Override
    public String executeOnServer() {
        fighterTypes = new HashMap<>();
        initHashMap();
        String[] args = getArgs();
        String name = null, image = null;
        int percentage = -1, type = -1, power = -1, resistance = -1, sanity = -1;
        try {
            for (int i = 1; i < args.length; i++) {
                switch (args[i].toUpperCase()) {
                    case "NOMBRE" -> name = args[i + 1];
                    case "IMAGEN" -> image = args[i + 1];
                    case "PORCENTAJE" -> percentage = Integer.parseInt(args[i + 1]);
                    case "TIPO" -> {
                        if (CommandUtils.isInteger(args[i + 1]))
                            type = Integer.parseInt(args[i + 1]);
                        else 
                            type = fighterTypes.get(args[i + 1].toUpperCase());
                    }
                    case "PODER" -> power = Integer.parseInt(args[i + 1]);
                    case "RESISTENCIA" -> resistance = Integer.parseInt(args[i + 1]);
                    case "SANIDAD" -> sanity = Integer.parseInt(args[i + 1]);
                    default -> {
                            System.out.println("no1");
                            return "ERROR";
//                            return error(player.getPlayerName());
                    }
                }
                i++;
            }
            System.out.println("fighters before: " + getPlayerExcecuting().getFighters().toString());
            if (CommandUtils.areValuesOk(new Object[] {name, image, percentage, type, power, resistance, sanity})) {
                if (getPlayerExcecuting().addFighter(name, image, percentage, type, power, resistance, sanity)) {
                    System.out.println("Fighters after add: " + getPlayerExcecuting().getFighters().toString());
                    return CommandUtils.concatArray(args);  
                }
                else {
                    return "ERROR";
                }
            } else {
                System.out.println("no4");
                return "ERROR";
//                return error(player.getPlayerName());
            }
        } catch (Exception ex) {
            System.out.println("fighters size EXCEPTION: " + getPlayerExcecuting().getFighters().size());
            if (getPlayerExcecuting().getFighters().size() == 3)
                getPlayerExcecuting().setFighersDone(true);
            return "ERROR";
//            return error(player.getPlayerName());
        }
    }
    
//    private String error(String playerName) {
//        String ret = playerName + " ingreso un comando de manera"
//                            + "incorrecta, favor ingresarlo de nuevo con el formato adecuado";
//        mistake = true;
//        System.out.println("String ret: " + ret);
//        return ret;
//    }

    @Override
    public String executeOnClient() {
        Player player = getPlayerExcecuting();
        if (player.areFighersDone())
            return "No se pueden crer mas de 3 luchadores";
        else if (player.isReady())
            return "No puede crear luchadores luego de comenzar el juego";
        String ret = player.getPlayerName() + " creo un luchador con los siguientes datos: \n" 
                + player.getLastFighter().toString();
        System.out.println("player getfighter: " + player.getLastFighter());
        ClientManager.getCM().getMainScreen().addFighter(player.getLastFighter());
        return ret;
    }
    
    private void initHashMap() { 
        fighterTypes.put("RELEASE THE KRAKEN", 1);
        fighterTypes.put("POSEIDON TRIDENT", 2);
        fighterTypes.put("FISH TELEPATHY", 3);
        fighterTypes.put("UNDERSEA FIRE", 4);
        fighterTypes.put("THUNDERS UNDER THE SEA", 5);
        fighterTypes.put("WAVES CONTROL", 6);
    }
    
    @Override
    public String toString() {
        return this.getPlayerExcecuting().toString();
    }

}
