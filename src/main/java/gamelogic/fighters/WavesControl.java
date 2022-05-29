/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamelogic.fighters;

import gamelogic.Fighter;
import gamelogic.Player;
import java.awt.Color;
import server.ThreadServer;

/**
 *
 * @author ivan
 */
public class WavesControl extends Fighter {

    public WavesControl(String name, String image, int percentage, int power, int resistance, int sanity, Color color, Player playerExecuting) {
        super(name, image, percentage, power, resistance, sanity, color, playerExecuting);
    }

    @Override
    protected boolean specialAttack(String[] args, ThreadServer target) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
