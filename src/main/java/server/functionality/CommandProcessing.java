/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.functionality;

/**
 *
 * @author ivan
 */
public class CommandProcessing {

    private String[] availableCommands = {"crear personaje", "iniciar partida", 
        "ataque", "saltar turno", "chat", "chat privado", "rendirse", 
        "consultar celda", "log", "log resumen", "consultar enemigo", 
        "mostrar celdas ocupadas", "mostrar porcentajes de celdas", "pintar vivas"};

    private int checkCommand(String command) {
        for (int i = 0; i < availableCommands.length; i++)
            if (availableCommands[i].equals(command))
                return i;
        return -1;
    }
    
    public String executeCommand(String command) {
        String str = "";
        int commandNum = checkCommand(command);
        if (commandNum >= 0) {
            switch (commandNum) {
                case 0 -> str = "comando para crear luchador";
                case 1 -> str = "comando para iniciar partida recibdo";
                case 2 -> str = "comando para ataque";
            }
            return str;
        }else
            return "Comando invalido";
    }

}
