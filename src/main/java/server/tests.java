/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.util.HashSet;
import java.util.Random;

/**
 *
 * @author ivan
 */
public class tests {
    
    public static void main(String[] args) {
        HashSet<Integer> set = new HashSet<>();
        Random r = new Random();
        for (int i = 0; i < 234; i++)
            set.add(r.nextInt(10)+1);
        System.out.println(set.toString());
        System.out.println(set.size());
        
    }
    
}
