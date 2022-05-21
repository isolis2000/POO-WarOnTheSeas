/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package functionality;

import java.util.ArrayList;

/**
 *
 * @author ivan
 */
public class Players {
    
    private ArrayList<Player> playerList = new ArrayList<>();
    
    public void addPlayer(String name) {
        playerList.add(new Player(name));
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
    
}
