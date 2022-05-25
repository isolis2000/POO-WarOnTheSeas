/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamelogic;

import client.Client;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author ivan
 */
public class Player implements Serializable {
    
    private boolean ready = false;
    private final String playerName;
    private ArrayList<Fighter> fighters = new ArrayList<>();
    private Fighter lastFighter;
    private static final Color[] colors = {Color.pink, Color.green, Color.cyan};
    
    public Player(String playerName) {
        this.playerName = playerName;
    }
    
    public boolean addFighter(String name, String image, int percentage, int type, int power, int resistance, int sanity) {
        Color color = colors[fighters.size()];
        if (fighters.size() < 3) {
            Fighter commander = new Fighter(name, image, percentage, type, power, resistance, sanity, color);
            fighters.add(commander);
            setLastFighter();
            System.out.println("Added new fighter, new size: " + fighters.size());
            return true;
        } else {
            return false;
        }
    }
    
    public Fighter getLastFighter() {
        return lastFighter;
    }

    public void setLastFighter() {
        System.out.println("Fighters size: " + fighters.size());
        lastFighter = fighters.get(fighters.size()-1);
    }

    public String getPlayerName() {
        return playerName;
    }

    public ArrayList<Fighter> getFighters() {
        return fighters;
    }

    public Color[] getColors() {
        return colors;
    }

    public void setFighters(ArrayList<Fighter> fighters) {
        this.fighters = fighters;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
    
    @Override
    public String toString() {
        return playerName + "\nfighters: \n" + fighters.toString() + "\n----------------------------\nlisto? = " + ready;
    }
}
