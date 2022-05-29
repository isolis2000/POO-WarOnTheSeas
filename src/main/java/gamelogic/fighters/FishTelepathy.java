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
public class FishTelepathy extends Fighter {
    /*
    Cardumen: Crea entre 100 y 300 peces pequeños que atacan
    aleatoriamente casillas del contrincante. Cada pez daña un 33% de la
    casilla.
    • Shark attack: los tiburones atacan las 4 esquinas del mapa. Desde
    cada una de las esquinas un radio de entre 1 y 10 casillas.
    • Pulp: se generan entre 20 y 50 pulpos, cada uno saca 8 tentáculos en
    casillas aleatorias. Si al menos 4 tentáculos de cualesquiera pulpos
    tocan la misma casilla, se destruye. Es decir, cada tentáculo daña un
    25% la casilla que toca.
    */
    public FishTelepathy(String name, String image, int percentage, int power, int resistance, int sanity, Color color, Player playerExecuting) {
        super(name, image, percentage, power, resistance, sanity, color, playerExecuting);
    }

    @Override
    protected boolean specialAttack(String[] args, ThreadServer target) {
        boolean result = 
        switch (args[3].toUpperCase()) {
            case "CARDUMEN" -> cardumen(target);
            case "SHARK ATTACK" -> sharkAttack(target);
            case "PULP" -> pulp(target);
            default ->false;
        };
        if (!result)
            System.out.println("specialAttackBombsucks");
        return result;
    }
    
    private boolean cardumen(ThreadServer target) {
        Random random = new Random();       
        int numOfCells = random.nextInt(99, 300) + 1;
        try {
            String forRecord = "Jugador " + this.playerExecuting.getPlayerName() 
                    + " ataco esta casilla con el ataque Cardumen." 
                    + " La casilla tomo 33% de dano.";
            ArrayList<Cell> cellsToDestroy = target.getPlayer().getRandomCells(numOfCells);
            for (Cell cell : cellsToDestroy)
                cell.takeDamage(33, forRecord);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    /*
    • Shark attack: los tiburones atacan las 4 esquinas del mapa. Desde
    cada una de las esquinas un radio de entre 1 y 10 casillas.
    */
    private boolean sharkAttack(ThreadServer target) {
        try {
            ArrayList<Cell> sharkCells = target.getPlayer().getSharkCells();
            String forRecord = "Jugador " + this.playerExecuting.getPlayerName() 
                    + " ataco esta casilla con el ataque Shark Attack." 
                    + " La casilla tomo 50% de dano.";
            for (Cell cell : sharkCells) {
                cell.takeDamage(50, forRecord);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } 
    }
    /*
    • Pulp: se generan entre 20 y 50 pulpos, cada uno saca 8 tentáculos en
    casillas aleatorias. Si al menos 4 tentáculos de cualesquiera pulpos
    tocan la misma casilla, se destruye. Es decir, cada tentáculo daña un
    25% la casilla que toca.
    */
    private boolean pulp(ThreadServer target) {
        Random random = new Random();
        int octopusAmmount = random.nextInt(20,51);
        ArrayList<Cell> cellsToAttack = new ArrayList<>();
        try {
            for (int i = 0; i < octopusAmmount; i++)
                cellsToAttack.addAll(target.getPlayer().getRandomCells(8));
            String forRecord = "Jugador " + this.playerExecuting.getPlayerName() 
                    + " ataco esta casilla con el ataque Pulp." 
                    + " La casilla tomo 25% de dano.";
            for (Cell cell : cellsToAttack) {
                cell.takeDamage(25, forRecord);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } 
    }
    
}
