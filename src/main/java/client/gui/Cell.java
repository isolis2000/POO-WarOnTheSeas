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
    private int volcano = 0, whirlpool = 0; //0 = no structure, 1 = center, 2 = radius
    private Fighter fighter;
    private final int[] placement;

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

    public int getVolcano() {
        return volcano;
    }

    public void setVolcano(int volcano) {
        if (this.volcano != 1)
            this.volcano = volcano;
        verifySpecialObjects();
    }

    public int getWhirlpool() {
        return whirlpool;
    }

    public void setWhirlpool(int whirlpool) {
        if (this.whirlpool != 1)
            this.whirlpool = whirlpool;
        verifySpecialObjects();
    }
    
    private void verifySpecialObjects() {
        if (this.whirlpool != 0 && this.volcano != 0) {
            this.setText("!");
            addToRecord("Esta casilla ahora posee un volcan y un remolino");            
        }
        else if (this.whirlpool != 0) {
            this.setText("R");
            addToRecord("Esta casilla ahora posee un remolino");
        }
        else if (this.volcano != 0) {
            this.setText("V");
            addToRecord("Esta casilla ahora posee un volcan");
        }
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
