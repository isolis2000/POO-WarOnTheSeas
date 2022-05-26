/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamelogic;

import java.awt.Color;
import java.io.Serializable;
import server.ThreadServer;

/**
 *
 * @author ivan
 */
public class Fighter implements Serializable {
    
    private String name, image;
    private int percentage, power, resistance, sanity;
    private Color color;

    public Fighter(String name, String image, int percentage, int power, int resistance, int sanity, Color color) {
        this.name = name;
        this.image = image;
        this.percentage = percentage;
        this.power = power;
        this.resistance = resistance;
        this.sanity = sanity;
        this.color = color;
    }
    
    public void attack(String attackCommand, ThreadServer enemy) {
        switch (attackCommand) {
            case "sanidad" -> 
        }
    }

//    @Override
//    public String toString() {
//        String ret = "Nombre: " + name;
////        ret += "\nImagen: " + image;
//        ret += "\nPorcentaje: " + percentage;
//        ret += "\nTipo: " + type;
//        ret += "\nPoder: " + power;
//        ret += "\nResistencia: " + resistance;
//        ret += "\nSanidad: " + sanity;
//        return ret;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public int getSanity() {
        return sanity;
    }

    public void setSanity(int sanity) {
        this.sanity = sanity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
}
