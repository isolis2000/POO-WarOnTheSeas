/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandsmanager;

/**
 *
 * @author diemo
 */
public class CommandUtils {
    
    public static String[] convertToArray(String str){
        return str.split("-");
    }
    
    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    private static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
    
    public static String concatArray(String[] args) {
        String str = "";
        for (int i = 0; i < args.length; i++) {
            str += args[i] + "-";
        }
        return str;
    }
    
    public static void notifyErrorInCommand() {
        System.out.println("Mamó con ese comando compa");
    }
    
    public static boolean areValuesOk(Object[] arr) {
        try {
            for (Object o : arr) {
                if (o instanceof Integer && (int)o == -1) {
//                    System.out.println("valuesok1");
                    return false;
                }
                else if (o == null) {
//                    System.out.println("valuesok2");
                    return false;
                }
            }
            return true;            
        } catch (Exception ex) {
            
//                    System.out.println("valuesok1");
            return false;
        }
    }
}
