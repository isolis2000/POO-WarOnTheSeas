/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.pojos;

import java.io.Serializable;

// Esta clase se puede enviar/leer por un socket
// debe ser serializable al igual que la clase de cualquier objeto
// como atributo de ella
class POJO implements Serializable {

    String command;
    
}