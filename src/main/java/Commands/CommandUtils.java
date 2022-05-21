/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author diemo
 */
public class CommandUtils {
    
    
    
    public static String[] convertToArray(String str){
        return str.split(" ");
    }
    
    public static String concatArray(String[] args) {
        String str = "";
        for (int i = 0; i < args.length; i++) {
            str += args[i] + " ";
        }
        return str;
    }
}
