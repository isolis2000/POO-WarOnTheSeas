package gamelogic;

import client.gui.Cell;
import java.awt.Color;
import java.io.Serializable;
import server.ThreadServer;

public abstract class Fighter implements Serializable {
    
    private String name, image, type;
    protected int percentage, power, resistance, sanity, powerup;
    private Color color;
    protected Player player;

    public Fighter(String name, String image, String type, int percentage, int power, int resistance, int sanity, Color color, Player player) {
        this.name = name;
        this.image = image;
        this.type = type;
        this.percentage = percentage;
        this.power = power;
        this.resistance = resistance;
        this.sanity = sanity;
        this.color = color;
        this.player = player;
        this.powerup = 0;
    }
    
    // 0 attack 1 target 2 fighter 3 attacktype 4 instructions
    public boolean attack(String[] args, ThreadServer target) {
        return switch (args[3].toLowerCase()) {
            case "sanidad" -> sanity(target);
            case "resistencia" -> resistance(target);
            case "fuerza" -> powerUp(target);
            default -> specialAttack(args, target);
        };
    }
    
    // 0 attaque 1 objetivo 2 luchador 3 habilidad
    private boolean sanity(ThreadServer target) {
        for (Cell[] row : target.getPlayer().getCells())
            for (Cell cell : row)
                if (cell.getFighter().getName().equals(this.name))
                    cell.addHp(this.sanity);
        return true;
    }
    
    private boolean resistance(ThreadServer target) {
        System.out.println("fighter name: " + name);
        for (Cell[] row : target.getPlayer().getCells())
            for (Cell cell : row) {
                System.out.println("name inside: " + cell.getFighter().getName());
                if (cell.getFighter().getName().equals(this.name)) {
                    cell.setResistance(resistance);
                    System.out.println("resistance inside: " + resistance);
                    System.out.println("cell: " + cell.getResistance());
                }
            }
        return true;
    }
    
    private boolean powerUp(ThreadServer target) {
        this.powerup ++;
        return true;
    }
    
    protected double getDamageWithPowerUp(int initialDamage) {
        double damage = initialDamage + ((initialDamage * (power * powerup))/100);
        powerup = 0;
        return damage;
    }
    
    protected abstract boolean specialAttack(String[] args, ThreadServer target);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return player;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public int getSanity() {
        return sanity;
    }

    public void setSanity(int sanity) {
        this.sanity = sanity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }
    
}
