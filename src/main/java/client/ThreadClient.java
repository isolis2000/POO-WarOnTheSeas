/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import commandsmanager.BaseCommand;
import gamelogic.Fighter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Message;

/**
 *
 * @author diemo
 */
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
            //Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
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
//            try {
//                System.out.println("waiting for bool");
//                boolean threeNumbersAttack = this.reader.readBoolean();
//                if (threeNumbersAttack) 
//                    writer.writeUTF(ClientManager.getCM().getMainScreen().askForThreeNumbers());
//            } catch (IOException ex) {
//                System.out.println("No es un bool");
//            }
            try {
//                System.out.println("---------------------------------------------------------------");
//                System.out.println(this.reader.readObject());
                readMessage = (BaseCommand)this.reader.readObject();
                ClientManager.getCM().getMainScreen().getPlayer().syncPlayer(readMessage.getPlayerExcecuting());
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
            
//            if (readMessage.getName().toUpperCase().equals("CHAT")){
//                System.out.println("recibido " + readMessage.toString());
//                client.screenRef.showClientMessage("Recibido: " + readMessage.toString());
//            }else if (readMessage.getName().toUpperCase().equals("ATTACK")){
//                client.screenRef.showClientMessage("ATAQUE: " + readMessage.toString());
//            }
        }
    }
}
