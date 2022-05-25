package commands;

import client.Client;
import java.io.Serializable;
import java.util.Arrays;
import gamelogic.Player;

/**
 *
 * @author ivan
 */
public class CreateCharacterCommand extends BaseCommand {

    public CreateCharacterCommand(String commandName, String[] args) {
        super(commandName, args, false);
    }

    @Override
    public String executeOnServer() {
        System.out.println("EXECUTEONSERVER!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        String[] args = getArgs();
        String name = null, image = null;
        int percentage = -1, type = -1, power = -1, resistance = -1, sanity = -1;
        try {
            for (int i = 1; i < args.length; i++) {
                switch (args[i]) {
                    case "nombre" -> name = args[i + 1];
                    case "imagen" -> image = args[i + 1];
                    case "porcentaje" -> percentage = Integer.parseInt(args[i + 1]);
                    case "tipo" -> type = Integer.parseInt(args[i + 1]);
                    case "poder" -> power = Integer.parseInt(args[i + 1]);
                    case "resistencia" -> resistance = Integer.parseInt(args[i + 1]);
                    case "sanidad" -> sanity = Integer.parseInt(args[i + 1]);
                    default -> {
                            System.out.println("no1");
                            return "ERROR";
//                            return error(player.getPlayerName());
                    }
                }
                i++;
            }
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
            System.out.println("no3");
            getPlayerExcecuting().setReady(true);
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
        if (player.isReady())
            return "No se pueden crer mas de 3 luchadores";
        String ret = player.getPlayerName() + " creo un luchador con los siguientes datos: \n" 
                + player.getLastFighter().toString();
        System.out.println("player getfighter: " + player.getLastFighter());
        Client.getMainScreen().addFighter(player.getLastFighter());
        return ret;
    }
    
    @Override
    public String toString() {
        return this.getPlayerExcecuting().toString();
    }

}
