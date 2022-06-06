/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client.gui;

import gamelogic.structures.Swirl;
import gamelogic.structures.Volcano;
import gamelogic.Fighter;
import java.io.Serializable;
import java.text.DecimalFormat;
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
    private double hp;
    private int resistance;
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
        String strForLogs = "Evento de jugador " + this.fighter.getPlayer().getName()
                + " en casilla " + Arrays.toString(placement) + ": ";
        strForLogs += str;
        ServerFrame.getServer().addToLogs(strForLogs);
    }

    public ArrayList<String> getRecord() {
        return record;
    }

    public String getRecordStr() {
        String ret = "- ";
        for (String str : record)
            ret += str + "\n\n-";
        return ret;
    }

    public double getHp() {
        return hp;
    }

    public boolean setHp(int hp, String strForRecord) {
        if (this.hp > 0) {
            this.hp = hp;
            return verifyHp(strForRecord);
        } else
            return false;
    }
    
    private boolean verifyHp(String strForRecord) {
        boolean ret = false;
        if (this.hp <= 0)
            ret = true;
        addToRecord(strForRecord);
        return ret;
    }
    
    public void addHp(int hp) {
        if (this.hp > 0) {
            String forRecord = "Esta casilla ha sido curada en " + hp + "%. ";
            forRecord += "Su vida paso de " + hp + "%";
            this.hp += hp;
            if (this.hp > 100)
                this.hp = 100;
            forRecord += " a " + hp + "%.";
            addToRecord(forRecord);
        }
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        String forRecord = "Esta casilla ahora posee una resistencia de " + resistance;
        this.resistance = resistance;
        addToRecord(forRecord);
    }

    public Fighter getFighter() {
        return fighter;
    }
    
    public void paintCell(String dataType) {
        switch (dataType.toLowerCase()) {
            case "ocupadas" -> paintSpecialObjects();
            case "porcentaje" -> paintHP();
            case "muertas" -> paintDead();
        }
    }
    
    private void paintDead() {
        if (hp <= 0)
            this.setText("X");
        else
            this.setText("");
    }
    
    private void paintHP() {
        this.setText(new DecimalFormat("#.##").format(hp) + "%");
    }
    
    private void paintSpecialObjects() {
        if (this.swirl != null && this.volcano != null)
            this.setText("!");
        else if (this.swirl != null)
            this.setText("R");
        else if (this.volcano != null)
            this.setText("V");
        else
            this.setText("");
    }
    
    public boolean takeDamage(double damage, String strForRecord) {
        if (this.hp > 0){
            if (resistance > 0) {
                damage -= (damage * ((float)resistance/100f));
                strForRecord += " Sin embargo, gracias a una resistencia de "
                        + resistance + ", la casilla realmente tomo " + damage 
                        + "% de dano.";
                resistance = 0;
            }
            strForRecord += " Su vida paso de " + hp + "%";
            this.hp -= damage; 
            if (this.hp < 0)
                this.hp = 0;
            strForRecord += " a " + hp + "%.";
            return verifyHp(strForRecord);
        } else
            return false;
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
    
    public boolean setVolcano(Volcano volcano, String strForRecord) {
        this.volcano = volcano;
        verifySpecialObjects();
        return this.setHp(0, strForRecord);
    }
    
    public boolean setSwirl(Swirl swirl, String strForRecord) {
        this.swirl = swirl;
        verifySpecialObjects();
        return this.setHp(0, strForRecord);
    }
    
    public Volcano getVolcano() {
        return volcano;
    }

    public Swirl getSwirl() {
        return swirl;
    }
    
    private void verifySpecialObjects() {
        if (this.swirl != null && this.volcano != null)
            addToRecord("Esta casilla ahora posee un volcan y un remolino");
        else if (this.swirl != null)
            addToRecord("Esta casilla ahora posee un remolino");
        else if (this.volcano != null)
            addToRecord("Esta casilla ahora posee un volcan");
    }
    
    public void updateCell(Cell newCell) {
        this.record = newCell.getRecord();
        this.fighter = newCell.getFighter();
        this.hp = newCell.getHp();
        this.resistance = newCell.getResistance(); 
        this.volcano = newCell.getVolcano();
        this.swirl = newCell.getSwirl();
        this.setText(newCell.getText());
        this.radioactiveWaste = newCell.getRadioactiveWaste();
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
