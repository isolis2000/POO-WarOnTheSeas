package commandsmanager;

import java.io.Serializable;
import gamelogic.Player;

public abstract class BaseCommand implements iCommand, Serializable{
    protected String[] args;
    private boolean broadcast, localCommand;
    private String commandName;
    protected Player playerExcecuting;
    protected String strToShare;
    protected boolean success = true, gameStarted;

    public BaseCommand(String commandName, String[] args, boolean broadcast, boolean localCommand, Player player) {
        this.args = args;
        this.broadcast = broadcast;
        this.localCommand = localCommand;
        this.commandName = commandName;
        this.playerExcecuting = player;
    }

    public boolean isLocalCommand() {
        return localCommand;
    }

    public Player getPlayerExcecuting() {
        return playerExcecuting;
    }

    public void setPlayerExcecuting(Player playerExcecuting) {
        this.playerExcecuting = playerExcecuting;
    }

    public String getStrToShare() {
        return strToShare;
    }

    public void setStrToShare(String strToShare) {
        this.strToShare = strToShare;
    }
    
    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getCommandName(){
        return this.commandName;
    }
    
    public boolean isBroadcast(){
        return broadcast;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
    
    public String getArgsStr() {
        return CommandUtils.concatArray(args);
    }
    
    
    public abstract String executeOnServer();
    public abstract String executeOnClient(); 
    
    @Override
    public String toString() {
        String ret = commandName;
        ret += ", player: " + playerExcecuting;
        ret += " turn: " + playerExcecuting.isTurn();
        return ret;
    }    
    
}
