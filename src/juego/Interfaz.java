package juego;
import java.awt.Color;
import entorno.*;
import java.awt.Image;

public class Interfaz {
    double x;
    double y;
    double ancho;
    double alto;
    double escala;


    Image imagen;

    public Interfaz(double x, double y, double ancho, double alto, double escala) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.escala = escala;

        this.imagen = Herramientas.cargarImagen("sprites/menu2.png");
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagen, x, y, 0, escala);
    }

    public void dibujarBoton(Entorno entorno, Image imagenHechizo, double posicionX, double posicionY, boolean estaClickeado, boolean estaInactivo, int costoMana) {

        if (estaClickeado) {
            entorno.dibujarRectangulo(posicionX, posicionY, 94, 94, 0, Color.YELLOW);
        }

        entorno.dibujarImagen(imagenHechizo, posicionX, posicionY, 0, 0.35);
        entorno.cambiarFont("Times New Roman", 10, Color.WHITE);
        entorno.escribirTexto(String.valueOf(costoMana), posicionX + 30, posicionY - 30);

        if (estaInactivo) {
            Color grisTranslucido = new Color(0, 0, 0, 100);
            entorno.dibujarRectangulo(posicionX, posicionY, 94, 94, 0, grisTranslucido);
        }
    }

    public void dibujarBarraVida(Entorno entorno, int vidaActual, int vidaMaxima, Image imagenVida) {
        double anchoTotal = 4.8;
        double altoVida = 20;
        double porcentajeVida = (double) vidaActual / vidaMaxima;
        double posY = 50;

        // Barra de vida actual
        entorno.dibujarRectangulo(x - 83 - (anchoTotal * (1 - porcentajeVida)) / 2, posY + 10,
                anchoTotal * porcentajeVida, altoVida, 0, Color.RED);
        // Imagen de vida
        entorno.dibujarImagen(imagenVida, x - 60, posY + 10, 0, 0.15);
        entorno.cambiarFont("Times New Roman", 12, Color.WHITE);
        entorno.escribirTexto(String.valueOf(vidaActual), x - 48, posY + 13);
    }

    public void dibujarBarraMana(Entorno entorno, int manaActual, int manaMaximo, Image imagenMana) {
        double anchoTotal = 25;
        double altoMana = 20;
        double porcentajeMana = (double) manaActual / manaMaximo;
        double posY = 80;

        // Barra de maná actual
        entorno.dibujarRectangulo(x - 80 - (anchoTotal * (1 - porcentajeMana)) / 2, posY +40,
                anchoTotal * porcentajeMana, altoMana, 0, Color.BLUE);
        // Imagen de maná
        entorno.dibujarImagen(imagenMana, x - 60, posY + 40 , 0, 0.15);
        entorno.cambiarFont("Times New Roman", 12, Color.WHITE);
        entorno.escribirTexto(String.valueOf(manaActual), x - 48, posY + 43);
    }
    
}
