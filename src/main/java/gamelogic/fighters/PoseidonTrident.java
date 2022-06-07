package gamelogic.fighters;

import client.gui.Cell;
import gamelogic.Fighter;
import gamelogic.Player;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import server.ThreadServer;

public class PoseidonTrident extends Fighter {

    public PoseidonTrident(String name, String image, int percentage, int power, int resistance, int sanity, Color color, Player playerExecuting) {
        super(name, image, "Poseidon Trident", percentage, power, resistance, sanity, color, playerExecuting);
    }
    
    @Override
    protected boolean specialAttack(String[] args, ThreadServer target) {
        return switch (args[3].toUpperCase()) {
            case "THREE LINES" -> threeLines(args, target);
            case "THREE NUMBERS" -> threeNumbers(args, target);
            case "CONTROL THE KRAKEN" -> controlTheKraken(args, target);
            default ->false;
        };
    }
    
    // 0 attack 1 target 2 fighter 3 attacktype 4 instructions
    private boolean threeLines(String[] args, ThreadServer target) {
        Random random = new Random();       
        int numOfCells = random.nextInt(8) + 1;
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        String[] directions = {"arriba", "abajo", "izquierda", "derecha"};
        try {
            for (int i = 4; i < args.length; i++) {
                switch (args[i].toLowerCase()) {
                    case "x" -> x.add(Integer.parseInt(args[i + 1]));
                    case "y" -> y.add(Integer.parseInt(args[i + 1]));
                    default -> throw new NumberFormatException();
                }
                i++;
            }
            for (int i = 0; i < 3; i++) {
                String direction = directions[random.nextInt(directions.length)];
                ArrayList<Cell> cellsToDestroy = target.getPlayer().getCellsInLine(new int[] {x.get(i), y.get(i)}, numOfCells, direction);
                String forRecord = "Jugador " + this.player.getName() + "destruyo esta casilla con el ataque threeLines";
                for (Cell cell : cellsToDestroy) {
                    if (cell.setHp(0, forRecord))
                        target.getPlayer().removeCell();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }    
    }
    
    // 0 attack 1 target 2 fighter 3 attacktype 4 instructions
    private boolean threeNumbers(String[] args, ThreadServer target) {
            return true;   
    }
    
    private boolean isInArray(int[] arr, int num) {
        for (int i : arr)
            if (i == num)
                return true;
        return false;
    }
    
    private boolean controlTheKraken(String[] args, ThreadServer target) {
        return true;
    }
    
}
