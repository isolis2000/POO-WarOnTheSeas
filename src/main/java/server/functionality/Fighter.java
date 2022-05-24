/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.functionality;

import java.io.Serializable;

/**
 *
 * @author ivan
 */
public class Fighter implements Serializable {
    
    private String name, image;
    private int percentage, type, power, resistance, sanity;

    public Fighter(String name, String image, int percentage, int type, int power, int resistance, int sanity) {
        this.name = name;
        this.image = image;
        this.percentage = percentage;
        this.type = type;
        this.power = power;
        this.resistance = resistance;
        this.sanity = sanity;
    }

    @Override
    public String toString() {
        String ret = "Nombre: " + name;
//        ret += "\nImagen: " + image;
        ret += "\nPorcentaje: " + percentage;
        ret += "\nTipo: " + type;
        ret += "\nPoder: " + power;
        ret += "\nResistencia: " + resistance;
        ret += "\nSanidad: " + sanity;
        return ret;
    }
    
}
