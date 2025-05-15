package juego;
import java.awt.Color;
import entorno.Entorno;
import java.awt.Image;

public class Interfaz {
    double x;
    double y;
    double ancho = 180;
    double alto = 1200;
    Image imagen;


    public Interfaz(double x, double y) {
        this.x = x;
        this.y = y;

    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.YELLOW);
    }
}
