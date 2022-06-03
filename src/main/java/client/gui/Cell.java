/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client.gui;

import gamelogic.structures.Swirl;
import gamelogic.structures.Volcano;
import gamelogic.Fighter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JLabel;
import server.ServerFrame;

/**
 *
 * @author ivan
 */
public class Cell extends JLabel implements Serializable {
    
    private ArrayList<String> record = new ArrayList<>();
    private int hp, resistance;
    private int radioactiveWaste = 0;
    private Fighter fighter;
    private final int[] placement;
    private Volcano volcano;
    private Swirl swirl;

    public Cell(String text, int[] placement) {
        super(text);
        this.placement = placement;
        this.hp = 100;
        this.resistance = 0;
        this.volcano = null;
        this.swirl = null;
    }
    
    public void addToRecord(String str) {
        record.add(str);
        String strForLogs = "Evento de jugador " + this.fighter.getPlayerExecuting().getPlayerName()
                + " en casilla " + Arrays.toString(placement) + ": ";
        strForLogs += str;
        ServerFrame.getServer().addToLogs(strForLogs);
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
            if (this.getText().equals(""))
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
    
    public void takeDamage(double damage, String strForRecord) {
        if (this.hp > 0){
            this.hp -= damage - (damage * (resistance/100));
            verifyHp(strForRecord);
        }
    }

    public void setFighter(Fighter owner) {
        this.fighter = owner;
    }

    public int[] getPlacement() {
        return placement;
    }

    public int getRadioactiveWaste() {
        return radioactiveWaste;
    }

    public void setRadioactiveWaste(int radioactiveWaste) {
        this.radioactiveWaste = radioactiveWaste;
    }
    
    public void addRadioactiveWaste() {
        this.radioactiveWaste ++;
    }
    
    public void setVolcano(Volcano volcano, String strForRecord) {
        this.volcano = volcano;
        verifySpecialObjects();
        this.setHp(0, strForRecord);
    }
    
    public void setSwirl(Swirl swirl, String strForRecord) {
        this.swirl = swirl;
        verifySpecialObjects();
        this.setHp(0, strForRecord);
    }
    
    public Volcano getVolcano() {
        return volcano;
    }

    public Swirl getSwirl() {
        return swirl;
    }
    
    private void verifySpecialObjects() {
        if (this.swirl != null && this.volcano != null) {
            this.setText("!");
            addToRecord("Esta casilla ahora posee un volcan y un remolino");            
        }
        else if (this.swirl != null) {
            this.setText("R");
            addToRecord("Esta casilla ahora posee un remolino");
        }
        else if (this.volcano != null) {
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
        return Arrays.toString(this.placement) + " text " + this.getText()
                + " fighter: " + this.fighter;
    }
}
