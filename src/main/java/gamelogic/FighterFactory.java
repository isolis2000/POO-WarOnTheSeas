package gamelogic;

import gamelogic.fighters.ReleaseTheKraken;
import java.awt.Color;

/**
 *
 * @author ivan
 */
public class FighterFactory {
    
    public static Fighter getCommand(String name, String image, int percentage, int power, int resistance, int sanity, Color color, int type){
        
        return switch (type) {
            case 1 -> new ReleaseTheKraken(name, image, percentage, power, resistance, sanity, color);
            default -> null;
        };
    }
    
}
