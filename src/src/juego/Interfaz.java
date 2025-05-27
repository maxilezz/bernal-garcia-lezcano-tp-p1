package juego;
import java.awt.Color;
import entorno.*;
import java.awt.Image;

public class Interfaz {
    double x;
    double y;
    double ancho;
    double alto;
    Image imagen;


    public Interfaz(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;

    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.GREEN);
 
    }
    
    public void dibujarBotones(Entorno entorno) {
    	entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.BLUE);
    }
    
    
    
}
