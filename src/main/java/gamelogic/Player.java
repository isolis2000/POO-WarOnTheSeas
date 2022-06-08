package gamelogic;

import client.gui.Cell;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import server.ServerFrame;
import server.ThreadServer;

public class Player implements Serializable {
    
    private boolean ready = false, fightersDone = false, turn = false, dead, winner;
    private final String name;
    private ArrayList<Fighter> fighters = new ArrayList<>();
    private static final Color[] colors = {Color.pink, Color.green, Color.cyan};
    private Cell[][] cells = new Cell[20][30];
    private ArrayList<String> availableStats = new ArrayList<>();
    private int cellsLeft = 600;
    private double hp = 100;
    
    public Player(String playerName) {
        this.name = playerName;
        int[] availableNumbers = {50, 75, 100};
        for (int num = 0; num < availableNumbers.length; num ++)
            for (int i = 0; i < 3; i ++)
                availableStats.add(Integer.toString(availableNumbers[num]));
    }
    
    public void removeCell() {
        cellsLeft --;
        hp = 100.0 * ((float)cellsLeft/600f);
    }

    public int getCellsLeft() {
        return cellsLeft;
    }

    public ArrayList<String> getAvailableStats() {
        return availableStats;
    }

    public double getHp() {
        return hp;
    }
    
    private boolean removeStatsFromAvailable(String[] stats) {
        ArrayList<String> localCopy = new ArrayList<>();
        localCopy.addAll(availableStats);
        for (String str : stats)
            if (localCopy.contains(str))
                localCopy.remove(str);
            else 
                return false;
        availableStats = localCopy;
        return true;
    }
    
    public int[] getFighterCells(String fighterName) {
        int cellsDestroyed = 0, totalCells = 0;
        for (Cell[] row : cells)
            for (Cell cell : row)
                if (cell.getFighter() != null 
                        && cell.getFighter().getName().equals(fighterName)) {
                    totalCells ++;
                    if (cell.getHp() <= 0)
                        cellsDestroyed ++;
                }
        int cellsLeftFromTotal = totalCells - cellsDestroyed;
        return new int[] {cellsLeftFromTotal, totalCells};
    }
    
    public boolean addFighter(String name, String image, int percentage, int type, int power, int resistance, int sanity) {
        Color color = colors[fighters.size()];
        String powerStr = Integer.toString(power);
        String resistanceStr = Integer.toString(resistance);
        String sanityStr = Integer.toString(sanity);
        if (fighters.size() < 3 && removeStatsFromAvailable(new String[] {powerStr, resistanceStr, sanityStr})) {
            Fighter commander = FighterFactory.getFighter(name, image, percentage, power, resistance, sanity, color, type, this);
            fighters.add(commander);
            addFighterToCells(commander);
            return true;
        } else {
            return false;
        }
    }
    
    private void addFighterToCells(Fighter fighter) {
        Random random = new Random();
        int numOfCellsToPaint = (int)(600*(fighter.getPercentage()/100.0f));
        ArrayList<Cell> availableCells = getCellsWithoutFighter();
        int numOfAvailableCells = availableCells.size();
        if (numOfCellsToPaint > numOfAvailableCells)
            numOfCellsToPaint = numOfAvailableCells;
        if (numOfCellsToPaint == numOfAvailableCells) {
            for (Cell cell : availableCells)
                cell.setFighter(fighter);
        } else {
            int numOfCellsPainted = 0;
            while (numOfCellsPainted < numOfCellsToPaint) {
                int cellNum = random.nextInt(numOfAvailableCells);
                availableCells.get(cellNum).setFighter(fighter);
                availableCells.remove(cellNum);
                numOfCellsPainted ++;
                numOfAvailableCells --;
            }
        }
    }
    
    public void paintCells(String dataType) {
        for (Cell[] row : cells)
            for (Cell cell : row)
                cell.paintCell(dataType);
    } 
    
    public String getState() {
        return "Jugador " + name + " tiene " + hp + "% de vida y le han destruido "
                + (600 - cellsLeft) + " casillas.";
    }
    
    private ArrayList<Cell> getCellsWithoutFighter() {
        ArrayList<Cell> cellsWithoutFighter = new ArrayList<>();
        for (Cell[] row : cells)
            for (Cell cell : row)
                if (cell.getFighter() == null)
                    cellsWithoutFighter.add(cell);
        return cellsWithoutFighter;
    }
    
    public void syncPlayer(Player newPlayer) {
        this.ready = newPlayer.isReady();
        this.fightersDone = newPlayer.areFighersDone();
        this.turn = newPlayer.isTurn();
        this.fighters = newPlayer.getFighters();
        this.hp = newPlayer.getHp();
        this.cellsLeft = newPlayer.getCellsLeft();
        this.availableStats = newPlayer.getAvailableStats();
        this.dead = newPlayer.isDead();
        this.winner = newPlayer.isWinner();
        for (int row = 0; row < cells.length; row++)
            for (int cell = 0; cell < cells[row].length; cell++)
                this.cells[row][cell].updateCell(newPlayer.getCells()[row][cell]);
    }
    
    public void printCells() {
        for (Cell[] row : cells)
            for (Cell cell : row)
                System.out.println(cell);
    }
    
    public Fighter getLastFighter() {
        return fighters.get(fighters.size()-1);
    }

    public String getName() {
        return name;
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
        return fightersDone;
    }

    public void setFighersDone(boolean fighersDone) {
        this.fightersDone = fighersDone;
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

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
    
    public void surrender() {
        for (Cell[] row : cells)
            for (Cell cell : row){
                cell.setHp(0, "Jugador se ha rendido, por lo que esta celda ha sido destruida");
                removeCell();
            }
        this.dead = true;
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
            if (fighterToUse.attack(args, target)) {
                String ret = name + " ataco a " + target.getPlayer().getName()
                        + " con el luchador " + fighterName + " utilizando el ataque "
                        + args[3];
                if (target.getPlayer().getHp() <= 0) {
                    target.getPlayer().setDead(true);
                    if (ServerFrame.getServer().isWinner())
                        this.winner = true;
                }
                return ret;
            }
            else
                return "ERROR1";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR2";
        }
    }
    
    public ArrayList<Cell> getCellsInRadius(int[] origin, int radius) {
        ArrayList<Cell> cellsRet = new ArrayList<>();
        HashSet<Cell> set = new HashSet<>();
        int x = origin[0]-1;
        int y = origin[1]-1;
        if (!((x >= 0) && (x < 20) && (y >= 0) && (y < 30)))
            return cellsRet;
        set.add(cells[x][y]);
        for (int i = x - radius; i <= x + radius; i++)
            for (int j = y - radius; j <= y + radius; j++)
                if (((i >= 0) && (i < 20) && (j >= 0) && (j < 30))) {
                    set.add(cells[i][j]);
                }
        cellsRet.addAll(set);
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
    
    public ArrayList<Cell> getRandomCells(int numOfCells) {
        Random random = new Random();
        ArrayList<Cell> randomCells = new ArrayList<>();
        for (int i = 0; i < numOfCells; i++) {
            int x = random.nextInt(20);
            int y = random.nextInt(30);
            randomCells.add(cells[x][y]);
        }
        return randomCells;
    }
    
    public ArrayList<Cell> getSharkCells() {
        Random random = new Random();
        ArrayList<Cell> sharkCells = new ArrayList<>();
        int maxX = cells.length;
        int maxY = cells[0].length;
        sharkCells.addAll(getCellsInRadius(new int[] {maxX, 1}, random.nextInt(10) + 1));
        sharkCells.addAll(getCellsInRadius(new int[] {maxX, maxY}, random.nextInt(10) + 1));
        sharkCells.addAll(getCellsInRadius(new int[] {1, 1}, random.nextInt(10) + 1));
        sharkCells.addAll(getCellsInRadius(new int[] {1, maxY}, random.nextInt(10) + 1));
        return sharkCells;
    }
    
    public ArrayList<Cell> getRadioactiveCells() {
        ArrayList<Cell> radioactiveCells = new ArrayList<>();
        for (Cell[] row : cells)
            for (Cell cell : row)
                if (cell.getRadioactiveWaste() > 0)
                    radioactiveCells.add(cell);
        return radioactiveCells;
    }
    
}
