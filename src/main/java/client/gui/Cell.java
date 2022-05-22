/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client.gui;

import javax.swing.JLabel;

/**
 *
 * @author ivan
 */
public class Cell extends JLabel{
    
    private String record = "";
    private String color;

    public Cell(String color) {
        this.color = color;
    }
    
    public void addToRecord(String str) {
        record += "\n" + str;
    }

    public String getRecord() {
        return record;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
}
