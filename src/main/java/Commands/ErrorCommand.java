/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import java.io.Serializable;

/**
 *
 * @author diemo
 */
public class ErrorCommand  extends BaseCommand implements Serializable{
    
    

    // Hola como estan
    @Override
    public String executeOnServer() {
        return "Error: commando desconocido";
    }

    @Override
    public String executeOnClient() {
       return "Error: commando desconocido";
    }

    public ErrorCommand(String commandName, String[] args) {
        super(commandName, args, false);
    }

    
    
}
