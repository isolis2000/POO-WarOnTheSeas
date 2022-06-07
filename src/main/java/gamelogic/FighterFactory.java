package gamelogic;

import gamelogic.fighters.FishTelepathy;
import gamelogic.fighters.PoseidonTrident;
import gamelogic.fighters.ReleaseTheKraken;
import gamelogic.fighters.ThundersUnderTheSea;
import gamelogic.fighters.UnderseaFire;
import gamelogic.fighters.WavesControl;
import java.awt.Color;

public class FighterFactory {
    
    public static Fighter getFighter(String name, String image, int percentage, int power, int resistance, int sanity, Color color, int type, Player playerExecuting){
        return switch (type) {
            case 1 -> new ReleaseTheKraken(name, image, percentage, power, resistance, sanity, color, playerExecuting);
            case 2 -> new PoseidonTrident(name, image, percentage, power, resistance, sanity, color, playerExecuting);
            case 3 -> new FishTelepathy(name, image, percentage, power, resistance, sanity, color, playerExecuting);
            case 4 -> new UnderseaFire(name, image, percentage, power, resistance, sanity, color, playerExecuting);
            case 5 -> new ThundersUnderTheSea(name, image, percentage, power, resistance, sanity, color, playerExecuting);
            case 6 -> new WavesControl(name, image, percentage, power, resistance, sanity, color, playerExecuting);
            default -> null;
        };
    }
    
}
