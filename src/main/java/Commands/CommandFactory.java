/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;


public class CommandFactory { 
    
    
    public static BaseCommand getCommand(String name, String[] args){
        
        return switch (name.toUpperCase()) {
            case "CHAT" -> new ChatCommand(name, args);
            case "CHATPRIVADO" -> new PrivateChatCommand(name, args);
            default -> new ErrorCommand("error", args);
        };   
        
        
        
        
    }
    
    
    
}

