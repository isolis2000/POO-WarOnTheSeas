/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamelogic.fighters;

import client.gui.Cell;
import gamelogic.Fighter;
import java.awt.Color;
import java.util.ArrayList;
import server.ThreadServer;

/**
 *
 * @author ivan
 */
public class ReleaseTheKraken extends Fighter {

    public ReleaseTheKraken(String name, String image, int percentage, int power, int resistance, int sanity, Color color) {
        super(name, image, percentage, power, resistance, sanity, color);
    }
    
    // 0 attack 1 target 2 fighter 3 attacktype 4 instructions
    @Override
    protected boolean specialAttack(String[] args, ThreadServer target) {
        boolean result = 
        switch (args[3].toUpperCase()) {
            case "TENTACULOS" -> tentacles(args, target);
            case "KRAKEN BREATH" -> krakenBreath(args, target);
            case "RELEASE THE KRAKEN" -> releaseTheKraken(args, target);
            default ->false;
        };
        if (!result)
            System.out.println("specialAttackBombsucks");
        return result;
    }   
    
    private boolean tentacles(String[] args, ThreadServer target) {
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        try {
            for (int i = 4; i < args.length; i++) {
                System.out.println("fuck");
                switch (args[i].toLowerCase()) {
                    case "x" -> x.add(Integer.parseInt(args[i + 1]));
                    case "y" -> y.add(Integer.parseInt(args[i + 1]));
                    default -> throw new NumberFormatException();
                }
                i++;
            }
            System.out.println("entered here");
            for (int i = 0; i < 3; i++) {
                System.out.println("not fuck");
                for (Cell cell : target.getPlayer().getCellsInRadius(new int[] {x.get(i), y.get(i)}, 1)) {
                    cell.setHp(0);
                    cell.setText("X");
                    cell.setForeground(Color.black);
                }
            }
            return true;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private boolean krakenBreath(String[] args, ThreadServer target) {
        return true;
    }
    
    private boolean releaseTheKraken(String[] args, ThreadServer target) {
        return true;
    }
    
}
