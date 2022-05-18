/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

// es el paquete de clases que maneja los objetos
// Socket
import java.net.*;
// Este paquete se encarga del manejo de los
// DataStream y ObjectStream
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

// La clase server acepta una única conexion (tiene
// un) solo accept() y envia datos primitivos y un
//objeto a ese cliente que se conecte
public class Server {

    //Es el socket de tipo Servidor que permite levantar
    // el servicio
    private final ArrayList<ServerSocket> serverSockets = new ArrayList<>();

    //es una referencia al cliente que se conecta al server,
    // la referencia se obtiene a partir de la instruccion
    // serverSocket.accept();
    Socket cliente;

    public Server() {
        try {
            initPorts(3);
            System.out.println ("Esperando cliente ... ");
            
            // Se acepata una conexión con un cliente. Esta llamada se queda
            // bloqueada hasta que se arranque un cliente en el IP del server
            // y el mismo puerto.
            // Este metodo accept() retorna el socket del cliente, el socket 
            // que se le hizo new en la clase Cliente
            cliente = serverSockets.get(1).accept();
            // El metodo getInetAddress() retorna la IP del cliente
            // El metodo getPort() retorna el Puerto del cliente
            System.out.println ("Conectado con cliente de " + 
                    cliente.getInetAddress()+":"+
                    cliente.getPort());
            
            // Esta llamada sólo
            // es necesaria si cerramos el socket inmediatamente después de
            // enviar los datos (como en este caso).
            // setSoLinger() a true hace que el cierre del socket espere a que
            // el cliente lea los datos, hasta un máximo de 10 segundos de espera.
            // Si no ponemos esto, el socket se cierra inmediatamente y si el 
            // cliente no ha tenido tiempo de leerlos, los datos se pierden.
            cliente.setSoLinger (true, 10);
                        
            // Se prepara un flujo de salida de datos simples con el socket.
            DataOutputStream streamDeSalida = new DataOutputStream 
                    (cliente.getOutputStream());
            
            
              // Se envia un entero y una cadena de caracteres.
            streamDeSalida.writeInt (22);
            System.out.println ("Enviado 22");
//            // Para escribir String se utiliza writeUTF, no existe un
//            // writeString()
            streamDeSalida.writeUTF ("Hola");
            System.out.println ("Enviado Hola");
//            
            streamDeSalida.writeBoolean(true);
            System.out.println ("Enviado true");
//
//////            // Se prepara un flujo de salida para objetos y un objeto para enviar*/
            POJO dato = new POJO(66,"Otra cosa");
//////
            ObjectOutputStream bufferObjetos =
                new ObjectOutputStream (cliente.getOutputStream());
//////
//////
//////            // Se envía el objeto
            bufferObjetos.writeObject(dato);
           System.out.println ("Enviado " + dato.toString());
//////
////            //REspuesta del cliente
            DataInputStream lectura = new DataInputStream(cliente.getInputStream());
////            
            System.out.println("Recibido: "+lectura.readUTF());
////            


            
            
            // Se cierra el socket con el cliente.
            // La llamada anterior a setSoLinger() hará
            // que estos cierres esperen a que el cliente retire los datos.
            cliente.close();
            
            // Se cierra el socket encargado de aceptar clientes. Ya no
            // queremos más.
//            serverSocket.close();
        } catch (IOException ex) {
            System.out.println("EXCEP:"+ex.getMessage());
        }

    }

//    public static void main (String [] args)
//    {
//        // Se instancia la clase principal para que haga todo lo que tiene que
//        // hacer el ejemplo
//        new Server().trythis();
//    }

    private void initPorts(int numOfPorts) {
        int firstPort = 35557;
        try {
            for (int i = 0; i < numOfPorts; i++) {
                serverSockets.add(new ServerSocket(firstPort++));
                System.out.println("ServerSocket" + i + " initialized with port: " 
                        + serverSockets.get(i).getLocalPort());
            }
        } catch (IOException ex) {
            System.out.println("ni picha");
        }
    }
}
