package client;

import commandsmanager.BaseCommand;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ThreadClient extends Thread{
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private boolean isRunning = true;

    public ThreadClient() {
        try {
            writer = new ObjectOutputStream(ClientManager.getCM().getClient().socket.getOutputStream());
            reader = new ObjectInputStream(ClientManager.getCM().getClient().socket.getInputStream());
            ClientManager.getCM().setThreadClient(this);
        } catch (IOException ex) {
        }
    }

    public ObjectOutputStream getWriter() {
        return writer;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }

    public ObjectInputStream getReader() {
        return reader;
    }

    public void setReader(ObjectInputStream reader) {
        this.reader = reader;
    }

    @Override
    public void run(){
        while (isRunning) {
        BaseCommand readMessage = null;
            try {
                writer.reset();
            } catch (IOException ex) {
                System.exit(0);
            }
            try {
                readMessage = (BaseCommand)this.reader.readObject();
                ClientManager.getCM().getMainScreen().updateInfoPanels();
                System.out.println("RECIBIDO--------------------------");
                System.out.println("mensaje: " + readMessage);
            } catch (IOException ex) {    
                System.out.println("readmessage");
                continue;
            } catch (ClassNotFoundException ex) {
                System.out.println("message");
                continue;
            }
            ClientManager.getCM().getMainScreen().showClientMessage(readMessage.executeOnClient());
        }
    }
}
