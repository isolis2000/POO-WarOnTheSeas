/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamelogic.fighters;

import client.gui.Cell;
import gamelogic.structures.Volcano;
import gamelogic.Fighter;
import gamelogic.Player;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import server.ThreadServer;

/**
 *
 * @author ivan
 */
public class UnderseaFire extends Fighter {

    public UnderseaFire(String name, String image, int percentage, int power, int resistance, int sanity, Color color, Player playerExecuting) {
        super(name, image, percentage, power, resistance, sanity, color, playerExecuting);
    }
    
    @Override
    protected boolean specialAttack(String[] args, ThreadServer target) {
        boolean result = 
        switch (args[3].toUpperCase()) {
            case "VOLCANO RISING" -> volcanoRising(args, target);
            case "VOLCANO EXPLOSION" -> volcanoExplosion(args, target);
            case "TERMAL RUSH" -> termalRush(args, target);
            default ->false;
        };
        if (!result)
            System.out.println("specialAttackBombsucks");
        return result;
    }
    
    /*
    • Volcano raising: se genera un volcán en una casilla y este crecerá
    entre un radio de 1 a 10 casillas. Destruye las casillas donde creció.
    El volcán queda en esas casillas para futuros ataques.
    */
    private boolean volcanoRising(String[] args, ThreadServer target) {
        Random random = new Random();       
        int radius = random.nextInt(10) + 1;
        int x = 0; int y = 0;
        try {
            for (int i = 4; i < args.length; i++) {
                switch (args[i].toLowerCase()) {
                    case "x" -> x = Integer.parseInt(args[i + 1]);
                    case "y" -> y = Integer.parseInt(args[i + 1]);
                    default -> throw new NumberFormatException();
                }
                i++;
            }
            String forRecord = "Jugador " + this.playerExecuting.getPlayerName() 
                    + " destruyo esta casilla con el ataque Volcano Rising.";
            int[] origin = {x, y};
            ArrayList<Cell> cellsToAttack = target.getPlayer().getCellsInRadius(origin, radius);
            Volcano volcano = new Volcano(radius, origin);
            for (Cell cell : cellsToAttack) {
                cell.setVolcano(volcano, forRecord);
            }
            return true;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return false;
        }    
    }
    /*
    • Volcano explosion: debe seleccionar un volcán que creó
    previamente. El volcán hará erupción, enviado piedras equivalente
    a 10 veces el tamaño en celdas del volcán. Cada piedra daña un
    20% de la casilla donde cae.
    */
    private boolean volcanoExplosion(String[] args, ThreadServer target) {
        int x = 0; int y = 0;
        try {
            for (int i = 4; i < args.length; i++) {
                switch (args[i].toLowerCase()) {
                    case "x" -> x = Integer.parseInt(args[i + 1]);
                    case "y" -> y = Integer.parseInt(args[i + 1]);
                    default -> throw new NumberFormatException();
                }
                i++;
            }
            Volcano volcano = target.getPlayer().getCells()[x][y].getVolcano();
            int radius = volcano.getRadius();
            double damage = getDamageWithPowerUp(20);
            String forRecord = "Jugador " + this.playerExecuting.getPlayerName() 
                    + " ataco esta casilla con el ataque Volcano Explosion." 
                    + " La casilla tomo " + damage + "% de dano.";
            ArrayList<Cell> cellsToAttack = target.getPlayer().getRandomCells(radius * 10);
            for (Cell cell : cellsToAttack) {
                cell.takeDamage(damage, forRecord);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } 
    }
    /*
    • Termal rush: se genera un sobrecalentamiento del agua alrededor
    de un radio de 5 casillas del volcán. El calentamiento será entre 5 y
    6 segundos, cada segundo equivale a porcentaje del radio del
    volcán de daño, es decir, si el volcán tiene 5 de daño, cada segundo
    que pasa dañará las casillas en 5%
    */
    private boolean termalRush(String[] args, ThreadServer target) {
        Random random = new Random();
        int x = 0; int y = 0;
        try {
            for (int i = 4; i < args.length; i++) {
                switch (args[i].toLowerCase()) {
                    case "x" -> x = Integer.parseInt(args[i + 1]);
                    case "y" -> y = Integer.parseInt(args[i + 1]);
                    default -> throw new NumberFormatException();
                }
                i++;
            }
            Volcano volcano = target.getPlayer().getCells()[x][y].getVolcano();
            int radius = volcano.getRadius();
            int[] origin = volcano.getOrigin();
            String initialRecord = "Jugador " + this.playerExecuting.getPlayerName() 
                    + " ataco esta casilla con el ataque Radioactive Rush. El "
                    + "radio de este volcan es de " + radius;
            ArrayList<Cell> cellsToAttack = target.getPlayer().getCellsInRadius(origin, radius + 5);
            double damage = getDamageWithPowerUp(radius);
            for (Cell cell : cellsToAttack) {
                int time = random.nextInt(5, 7);
                initialRecord += " El ataque durara " + time + " segundos.";
                cell.addToRecord(initialRecord);
                for (int i = 0; i < time; i++) {
                    String forRecord = "Esta casilla tomo " + damage + "% de dano"
                            + " por el efecto del calentamiento del agua.";
                    cell.takeDamage(damage, forRecord);
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } 
    }
    
    
}
