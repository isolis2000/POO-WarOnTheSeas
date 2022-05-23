/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client.gui;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author ivan
 */
public class Cell extends JLabel{
    
    private String record = "";
    
    public void addToRecord(String str) {
        record += "\n" + str;
    }

    public String getRecord() {
        return record;
    }
    
}
