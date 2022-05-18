/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package communication;

import java.io.Serializable;

// Esta clase se puede enviar/leer por un socket
// debe ser serializable al igual que la clase de cualquier objeto
// como atributo de ella
class POJO implements Serializable
{

   int c;
   String d;

    public POJO(int c, String d) {
        this.c = c;
        this.d = d;
    }  
}

class Punto implements Serializable{
    int x, y;
    POJO x1;

    public Punto (int x, int y)
    {
        this.x = x;
        this.y = y;

    }


}
