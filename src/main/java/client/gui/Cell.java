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
    private boolean volcano = false, whirlpool = false;

    public Cell(String text, int[] placement) {
        super(text);
        this.placement = placement;
        this.hp = 100;
        this.resistance = 0;
    }
    
    private void addToRecord(String str) {
        record.add(str);
    }

    public ArrayList<String> getRecord() {
        return record;
    }

    public String getRecordStr() {
        String ret = "";
        for (String s : record)
            ret += "\n" + s;
        return ret;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp, String strForRecord) {
        this.hp = hp;
        verifyHp(strForRecord);
    }
    
    private void verifyHp(String strForRecord) {
        if (this.hp <= 0) {
            this.hp = 0;
            this.setText("X");
        }
        addToRecord(strForRecord);
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
    
    public void takeDamage(int damage, String strForRecord) {
        this.hp -= damage - (damage * (resistance/100));
        verifyHp(strForRecord);
    }

    public void setFighter(Fighter owner) {
        this.fighter = owner;
    }

    public int[] getPlacement() {
        return placement;
    }

    public boolean isVolcano() {
        return volcano;
    }

    public void setVolcano(boolean volcano) {
        this.volcano = volcano;
    }

    public boolean isWhirlpool() {
        return whirlpool;
    }

    public void setWhirlpool(boolean whirlpool) {
        this.whirlpool = whirlpool;
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
