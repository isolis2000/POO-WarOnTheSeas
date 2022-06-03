/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamelogic.structures;

import java.io.Serializable;

/**
 *
 * @author ivan
 */
public class Structure implements Serializable {
    
    protected int radius;
    protected int[] origin;

    public Structure(int radius, int[] origin) {
        this.radius = radius;
        this.origin = origin;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int[] getOrigin() {
        return origin;
    }

    public void setOrigin(int[] origin) {
        this.origin = origin;
    }
    
}
