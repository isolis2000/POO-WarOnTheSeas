package commands;

import java.io.Serializable;
import java.util.Arrays;
import server.functionality.Player;

/**
 *
 * @author ivan
 */
public class CreateCharacterCommand extends BaseCommand implements Serializable {

    private boolean mistake = false;

    public CreateCharacterCommand(String commandName, String[] args) {
        super(commandName, args, true);
    }

    @Override
    public String executeOnServer() {
        Player player = this.getPlayerExcecuting();
        String[] args = getArgs();
        System.out.println("Args: " + Arrays.toString(args));
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
            Object[] arr = new Object[] {name, image, percentage, type, power, resistance, sanity};
            System.out.println("valuesok: " + CommandUtils.areValuesOk(arr));
            System.out.println("values: ");
            for (Object o : arr)
                System.out.println(o);
            if (CommandUtils.areValuesOk(new Object[] {name, image, percentage, type, power, resistance, sanity})) {
                player.addFighter(name, image, percentage, type, power, resistance, sanity);
                System.out.println("no2");
                System.out.println(player.getFighters().get(0));
                return "a";                
            } else {
                            System.out.println("no4");
                            return "ERROR";
//                return error(player.getPlayerName());
            }
        } catch (Exception ex) {
                            System.out.println("no3");
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
        String ret = player.getPlayerName() + " creo un luchador con los siguientes datos: \n" 
                + player.getFighters().get(0).toString();
        return ret;
    }

}
