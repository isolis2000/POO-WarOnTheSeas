/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client.gui;

import gamelogic.Fighter;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author ivan
 */
public class Cell extends JLabel implements Serializable {
    
    private ArrayList<String> record = new ArrayList<>();
    private int hp, resistance;
    private Fighter fighter;

    public Cell(String text) {
        super(text);
    }
    
    public void addToRecord(String str) {
        record.add(str);
    }

    public ArrayList<String> getRecord() {
        return record;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
    
    public void addHp(int hp) {
        this.hp += hp;
        if (this.hp > 100)
            this.hp = 100;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public Fighter getFighter() {
        return fighter;
    }
    
    public void takeDamage(int damage) {
        this.hp -= (damage * (resistance/100));
    }

    public void setFighter(Fighter owner) {
        this.fighter = owner;
    }
    
}
