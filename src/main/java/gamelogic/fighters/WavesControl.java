package gamelogic.fighters;

import client.gui.Cell;
import gamelogic.structures.Swirl;
import gamelogic.Fighter;
import gamelogic.Player;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import server.ThreadServer;

public class WavesControl extends Fighter {

    public WavesControl(String name, String image, int percentage, int power, int resistance, int sanity, Color color, Player playerExecuting) {
        super(name, image, "Waves Control", percentage, power, resistance, sanity, color, playerExecuting);
    }
    
    @Override
    protected boolean specialAttack(String[] args, ThreadServer target) {
        return switch (args[3].toUpperCase()) {
            case "SWIRL RISING" -> swirlRising(args, target);
            case "SEND HUMAN GARBAGE" -> sendHumanGarbage(args, target);
            case "RADIOACTIVE RUSH" -> radioactiveRush(target);
            default -> false;
        };
    }
    
    /*
    • Swirl raising: se crea un remolino en una casilla,
    con una expansión entre 2 y 10 casillas de radio.
    Destruye todas las casillas que ocupa y queda vivo.
    */
    private boolean swirlRising(String[] args, ThreadServer target) {
        Random random = new Random();       
        int radius = random.nextInt(2, 11);
        int x = 0; int y = 0;
        try {
            for (int i = 4; i < args.length; i++) {
                switch (args[i].toLowerCase()) {
                    case "x" -> x = Integer.parseInt(args[i + 1]);
                    case "y" -> y = Integer.parseInt(args[i + 1]);
                    default -> throw new NumberFormatException();
                }
                i++;
            }
            int[] origin = {x, y};
            String forRecord = "Jugador " + this.player.getName() 
                    + " destruyo esta casilla con el ataque Swirl Rising.";
            ArrayList<Cell> cellsToAttack = target.getPlayer().getCellsInRadius(origin, radius);
            Swirl swirl = new Swirl(radius, origin);
            for (Cell cell : cellsToAttack) {
                if (cell.setSwirl(swirl, forRecord))
                    target.getPlayer().removeCell();
            }
            return true;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return false;
        }    
    }
    
    /*
    • Send human garbage: selecciona un remolino
    existente y envía 10 veces el radio del remolino de
    toneladas de basura a diversas casillas del mapa.
    Cada tonelada de basura daña 25% de la casilla
    donde se deposita. La basura queda en la casilla si
    es radioactiva, cada tonelada tiene un 50% de
    probabilidad de ser radioactiva.
    */
    private boolean sendHumanGarbage(String[] args, ThreadServer target) {
        Random random = new Random();
        int x = 0; int y = 0;
        try {
            for (int i = 4; i < args.length; i++) {
                switch (args[i].toLowerCase()) {
                    case "x" -> x = Integer.parseInt(args[i + 1]);
                    case "y" -> y = Integer.parseInt(args[i + 1]);
                    default -> throw new NumberFormatException();
                }
                i++;
            }
            Swirl swirl = target.getPlayer().getCells()[x][y].getSwirl();
            int radius = swirl.getRadius();
            double damage = getDamageWithPowerUp(25);
            String initialRecord = "Jugador " + this.player.getName() 
                    + " ataco esta casilla con el ataque Send Human Garbage." 
                    + " La casilla tomo " + damage + "% de dano.";
            ArrayList<Cell> cellsToAttack = target.getPlayer().getRandomCells(radius * 10);
            for (Cell cell : cellsToAttack) {
                String forRecord = initialRecord;
                if (random.nextBoolean()) {
                    forRecord += " Adicionalmente, como la basura es radioactiva, "
                            + "esta permanece en esta casilla";
                    cell.addRadioactiveWaste();
                }
                if (cell.takeDamage(damage, forRecord))
                    target.getPlayer().removeCell();
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }    
    }
    
    /*
    • Radioactive rush: activa toda la basura radioactiva
    por entre 1-10 segundos. Cada tonelada de basura
    radioactiva afecta entre 10% por segundo la vida
    de la casilla.
    */
    private boolean radioactiveRush(ThreadServer target) {
        Random random = new Random();
        try {
            String initialRecord = "Jugador " + this.player.getName() 
                    + " ataco esta casilla con el ataque Radioactive Rush.";
            ArrayList<Cell> cellsToAttack = target.getPlayer().getRadioactiveCells();
            for (Cell cell : cellsToAttack) {
                int time = random.nextInt(10) + 1;
                int tonsOfRadWaste = cell.getRadioactiveWaste();
                String extraInfoRecord = initialRecord + "Esta casilla posee " 
                        + tonsOfRadWaste + " toneladas de basura radioactiva."
                        + " El ataque durara " + time + " segundos.";
                cell.addToRecord(extraInfoRecord);
                double damage = getDamageWithPowerUp(tonsOfRadWaste * 10);
                for (int i = 0; i < time; i++) {
                    String forRecord = "Esta casilla tomo " + damage + "% de dano"
                            + " por el efecto de la basura radioactiva.";
                    if (cell.takeDamage(damage, forRecord))
                        target.getPlayer().removeCell();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }  
    }
    
    
}
