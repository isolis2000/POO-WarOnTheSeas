/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.functionality;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author ivan
 */
public class Player implements Serializable {
    
    private Square[] gameArea = new Square[600];
    private boolean ready = false;
    private String playerName;
    private ArrayList<Fighter> fighters = new ArrayList<>();

    public Player(String playerName) {
        this.playerName = playerName;
    }
    
    public boolean addFighter(String name, String image, int percentage, int type, int power, int resistance, int sanity) {
        if (fighters.size() < 3) {
            Fighter commander = new Fighter(name, image, type, percentage, power, resistance, sanity);
            fighters.add(commander);
            return true;
        } else 
            return false;
    }

    public String getPlayerName() {
        return playerName;
    }

    public ArrayList<Fighter> getFighters() {
        return fighters;
    }
}
