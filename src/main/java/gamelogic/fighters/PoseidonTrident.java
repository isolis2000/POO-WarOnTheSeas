/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamelogic.fighters;

import client.gui.Cell;
import gamelogic.Fighter;
import java.awt.Color;
import java.util.Random;
import server.ThreadServer;

/**
 *
 * @author ivan
 */
public class PoseidonTrident extends Fighter {

    public PoseidonTrident(String name, String image, int percentage, int power, int resistance, int sanity, Color color) {
        super(name, image, percentage, power, resistance, sanity, color);
    }
/*
    Three lines: selecciona 3 puntos en el mapa. En cada punto destruye lo que esté de 1 a 4
    casillas a la derecha, izquierda, arriba, abajo (aleatorio)
    • Three numbers: envía 3 números distintos entre 0 y 9 al contrincante. Este último debe
    escribir 3 números distintos entre 0 y 9, si atina al menos uno de los 3 del tridente enviado,
    explotará la cantidad de casillas que de la multiplicación de los números del Tridente.
    • Control the Kraken: si es atacado con el Kraken, retornará ese ataque al enemigo que lo atacó
    */
    @Override
    protected boolean specialAttack(String[] args, ThreadServer target) {
        boolean result = 
        switch (args[3].toUpperCase()) {
            case "THREE LINES" -> threeLines(args, target);
            case "THREE NUMBERS" -> threeNumbers(args, target);
            case "CONTROL THE KRAKEN" -> releaseTheKraken(args, target);
            default ->false;
        };
        if (!result)
            System.out.println("specialAttackBombsucks");
        return result;
    }
    
    // 0 attack 1 target 2 fighter 3 attacktype 4 instructions
    private boolean threeLines(String[] args, ThreadServer target) {
        Random random = new Random();       
        int numOfCells = random.nextInt(8) + 1;
        int x = 0; int y = 0;
        String[] directions = {"arriba", "abajo", "izquierda", "derecha"};
        try {
            for (int i = 4; i < args.length; i++) {
                switch (args[i].toLowerCase()) {
                    case "x" -> x = Integer.parseInt(args[i + 1]);
                    case "y" -> y = Integer.parseInt(args[i + 1]);
                    default -> throw new NumberFormatException();
                }
                i++;
            }
            String direction = directions[random.nextInt(directions.length)];
            for (Cell cell : target.getPlayer().getCellsInLine(new int[] {x, y}, numOfCells, direction)) {
                cell.setHp(0);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }    
    }
    
    // 0 attack 1 target 2 fighter 3 attacktype 4 instructions
    private boolean threeNumbers(String[] args, ThreadServer target) {
        Random random = new Random();       
        int numOfCells = random.nextInt(8) + 1;
        int[] numbers = new int[3];
        String[] directions = {"arriba", "abajo", "izquierda", "derecha"};
        try {
            for (int i = 4; i < args.length; i++) {
                numbers[i - 4] = Integer.parseInt(args[i]);
            }
//            for (Cell cell : target.getPlayer().getCellsInLine(new int[] {x, y}, numOfCells, direction)) {
//                cell.setHp(0);
//            }
//            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }    
    }
    
    private boolean controlTheKraken(String[] args, ThreadServer target) {
        return true;
    }
    
    
}
