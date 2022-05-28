/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamelogic;

import client.Client;
import client.gui.Cell;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import server.ThreadServer;

/**
 *
 * @author ivan
 */
public class Player implements Serializable {
    
    private boolean ready = false, fighersDone = false, turn = false;
    private final String playerName;
    private ArrayList<Fighter> fighters = new ArrayList<>();
    private Fighter lastFighter;
    private static final Color[] colors = {Color.pink, Color.green, Color.cyan};
    private Cell[][] cells = new Cell[20][30];
    
    public Player(String playerName) {
        this.playerName = playerName;
    }
    
    public boolean addFighter(String name, String image, int percentage, int type, int power, int resistance, int sanity) {
        Color color = colors[fighters.size()];
        if (fighters.size() < 3) {
            Fighter commander = FighterFactory.getCommand(name, image, percentage, power, resistance, sanity, color, type);
            fighters.add(commander);
            setLastFighter();
            System.out.println("Added new fighter, new size: " + fighters.size());
            return true;
        } else {
            return false;
        }
    }
    
    public Fighter getLastFighter() {
        return lastFighter;
    }

    public void setLastFighter() {
        System.out.println("Fighters size: " + fighters.size());
        lastFighter = fighters.get(fighters.size()-1);
    }

    public String getPlayerName() {
        return playerName;
    }

    public ArrayList<Fighter> getFighters() {
        return fighters;
    }

    public Color[] getColors() {
        return colors;
    }

    public void setFighters(ArrayList<Fighter> fighters) {
        this.fighters = fighters;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean areFighersDone() {
        return fighersDone;
    }

    public void setFighersDone(boolean fighersDone) {
        this.fighersDone = fighersDone;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }
    
    // 0 attack 1 target 2 fighter 3 attacktype 4 instructions
    public String attackWithFighter(ThreadServer target, String[] args) {
        Fighter fighterToUse = null;
        try {
            String fighterName = args[2];
            for (Fighter fighter : fighters) {
                if (fighter.getName().equals(fighterName))
                    fighterToUse = fighter;
            }
            if (fighterToUse.attack(args, target))
                return playerName + " ataco a " + target.getPlayer().getPlayerName()
                        + " con el luchador " + fighterName + " utilizando el ataque "
                        + args[3];
            else
                return "ERROR1";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR2";
        }
    }
    
    public ArrayList<Cell> getCellsInRadius(int[] origin, int radius) {
        ArrayList<Cell> cellsRet = new ArrayList<>();
        int x = origin[0]-1;
        int y = origin[1]-1;
        boolean bounds1 = false, bounds2 = false, bounds3 = false, bounds4 = false;
        if (!((x >= 0) && (x < 20) && (y >= 0) && (y < 30)))
            return cellsRet;
        cellsRet.add(cells[x][y]);
        for (int i = 1; i <= radius; i++) {
            if ((x + i) < 20) {
                bounds1 = true;
                cellsRet.add(cells[x+i][y]);
            }
            if ((x - i) >= 0) {
                bounds2 = true;
                cellsRet.add(cells[x-i][y]);
            }
            if ((y + i) < 30) {
                bounds3 = true;
                cellsRet.add(cells[x][y+i]);
            }
            if ((y - i) >= 0) {
                bounds4 = true;
                cellsRet.add(cells[x][y-i]);
            }
            if (bounds1 && bounds3)
                cellsRet.add(cells[x+i][y+i]);
            if (bounds2 && bounds4)
                cellsRet.add(cells[x-i][y-i]);
            if (bounds2 && bounds3)
                cellsRet.add(cells[x-i][y+i]);
            if (bounds1 && bounds4)
                cellsRet.add(cells[x+i][y-i]);
        }
        return cellsRet;
    }
    
    public ArrayList<Cell> getCellsInLine(int[] origin, int numOfCells, String direction) {
        ArrayList<Cell> cellsRet = new ArrayList<>();
        int x = origin[0]-1;
        int y = origin[1]-1;
        if (!((x >= 0) && (x < 20) && (y >= 0) && (y < 30)))
            return cellsRet;
        cellsRet.add(cells[x][y]);
        switch (direction) {
            case "arriba" -> {
                for (int i = 0; i < numOfCells; i++) {
                    if ((x - i) < 0)
                        break;
                    cellsRet.add(cells[x-i][y]);
                }
            }
            case "abajo" ->  {
                for (int i = 0; i < numOfCells; i++) {
                    if ((x + i) >= 20)
                        break;
                    cellsRet.add(cells[x+i][y]);
                }
            }
            case "izquierda" -> {
                for (int i = 0; i < numOfCells; i++) {
                    if ((y - i) < 0)
                        break;
                    cellsRet.add(cells[x][y-i]);
                }
            }
            case "derecha" -> {
                for (int i = 0; i < numOfCells; i++) {
                    if ((y + i) >= 30)
                        break;
                    cellsRet.add(cells[x][y+i]);                
                }
            }
        }
        return cellsRet;
    }
    
    @Override
    public String toString() {
        return playerName + "\nfighters: \n" + fighters.toString() + "\n----------------------------\nlisto? = " + ready;
    }
}
