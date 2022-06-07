package commandsmanager;

public interface iCommand {
    public String getCommandName();
    public String executeOnServer();
    public String executeOnClient();
    public boolean isBroadcast();
}
