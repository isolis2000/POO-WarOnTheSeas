/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamelogic.fighters;

import client.gui.Cell;
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
public class ThundersUnderTheSea extends Fighter {

    public ThundersUnderTheSea(String name, String image, int percentage, int power, int resistance, int sanity, Color color, Player playerExecuting) {
        super(name, image, percentage, power, resistance, sanity, color, playerExecuting);
    }
    @Override
    protected boolean specialAttack(String[] args, ThreadServer target) {
        boolean result = 
        switch (args[3].toUpperCase()) {
            case "THUNDER RAIN" -> thunderRain(target);
            case "POSEIDON THUNDERS" -> poseidonThunders(target);
            case "EEL ATTACK" -> eelAttack(target);
            default ->false;
        };
        if (!result)
            System.out.println("specialAttackBombsucks");
        return result;
    }
    /*
    Thunder rain: se creará una lluvia de 100
    rayos, cada rayo daña el 10-20% de la casilla
    donde cae.
    */
    private boolean thunderRain(ThreadServer target) {
        Random random = new Random();
        try {
            String forRecord = "Jugador " + this.playerExecuting.getPlayerName() 
                    + " ataco esta casilla con el ataque Thunder Rain." 
                    + " La casilla tomo ";
            ArrayList<Cell> cellsHit = target.getPlayer().getRandomCells(100);
            for (Cell cell : cellsHit) {
                double damageTaken = getDamageWithPowerUp(random.nextInt(10,21));
                forRecord += Double.toString(damageTaken) + "% de dano.";
                cell.takeDamage(damageTaken, forRecord);
            }
            return true;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    /*
    Poseidon thunders: crea entre 5 y 10 rayos,
    que al hacer contacto con una casilla genera
    una onda de destrucción de entre 2 y 10
    casillas de radio.
    */
    private boolean poseidonThunders(ThreadServer target) {
        Random random = new Random();
        int ammountOfThunders = random.nextInt(5, 11);
        try {
            String forRecord = "Jugador " + this.playerExecuting.getPlayerName() 
                    + " destruyo esta casilla con el ataque Poseidon Thunders";
            ArrayList<Cell> initialHits = target.getPlayer().getRandomCells(ammountOfThunders);
            for (Cell initialCell : initialHits) {
                int radius = random.nextInt(2, 11);
                ArrayList<Cell> colateralCells = target.getPlayer().getCellsInRadius(initialCell.getPlacement(), radius);
                for (Cell cell : colateralCells)
                    cell.setHp(0, forRecord);
            }
            return true;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    /*
    Eel atack: se creará un ataque de anguilas,
    entre 25 y 100 anguilas. Cada anguila dará
    entre 1 y 10 descargas eléctricas a la casilla
    donde ataca. Cada descarga daña un 10% de
    la casilla.
    */
    private boolean eelAttack(ThreadServer target) {
        Random random = new Random();
        int numOfEels = random.nextInt(25, 101);
        try {
            String forRecord = "Jugador " + this.playerExecuting.getPlayerName() 
                    + " ataco esta casilla con el ataque Eel Attack." 
                    + " La casilla recibio ";
            ArrayList<Cell> cellsToAttack = target.getPlayer().getRandomCells(numOfEels);
            for (Cell cell : cellsToAttack) {
                int numOfShocks = random.nextInt(10) + 1;
                double damage = getDamageWithPowerUp(numOfShocks * 10);
                forRecord += Integer.toString(numOfShocks) + " descargas y tomo " 
                        + Double.toString(damage) + "% de dano.";
                cell.takeDamage(damage, forRecord);
            }
            return true;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    
}
