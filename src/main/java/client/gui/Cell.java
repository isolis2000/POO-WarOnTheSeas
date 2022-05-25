/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client.gui;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author ivan
 */
public class Cell extends JLabel{
    
    private ArrayList<String> record = new ArrayList<>();
    
    public void addToRecord(String str) {
        record.add(str);
    }

    public ArrayList<String> getRecord() {
        return record;
    }
    
}
