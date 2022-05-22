/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.functionality;

import java.util.ArrayList;

/**
 *
 * @author ivan
 */
public class Player {
    
    private Square[] gameArea = new Square[600];
    private boolean ready = false;
    private String playerName;
    private ArrayList<Commander> commanders = new ArrayList<>();

    public Player(String playerName) {
        this.playerName = playerName;
    }
    
    public boolean addCommander(String name, int percentage, int power, int resistance, int sanity) {
        if (commanders.size() < 3) {
            Commander commander = new Commander(name, percentage, power, resistance, sanity);
            commanders.add(commander);
            return true;
        } else 
            return false;
    }
    
}
