/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client.gui;

import gamelogic.Fighter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final int[] placement;

    public Cell(String text, int[] placement) {
        super(text);
        this.placement = placement;
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
        verifyHp();
    }
    
    private void verifyHp() {
        if (this.hp <= 0) {
            this.hp = 0;
            this.setText("X");
        }
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
        verifyHp();
    }

    public void setFighter(Fighter owner) {
        this.fighter = owner;
    }

    public int[] getPlacement() {
        return placement;
    }
    
    public void updateCell(Cell newCell) {
        this.record = newCell.getRecord();
        this.fighter = newCell.getFighter();
        this.hp = newCell.getHp();
        this.resistance = newCell.getResistance();  
        this.setText(newCell.getText());
        if (this.fighter != null)
            this.setBackground(this.fighter.getColor());
        this.paintImmediately(this.getBounds());
    }
    
    @Override
    public String toString() {
        return Arrays.toString(this.placement) + " text " + this.getText();
    }
}
