package juego;
import java.awt.*;
import java.util.ArrayList;

import entorno.Entorno;
import entorno.Herramientas;

public class Jugador {	
	public double x; 
	public double y;
	public double ancho = 20;
	public double alto = 30;
	public double velocidad;
	public int vida;
	public int mana;
	public ArrayList<Hechizo> hechizos;

	Image imagenDerecha;
	Image imagenIzquierda;
	Image imagenActual;

	public Jugador(double x, double y, double velocidad, int vida, int mana, ArrayList<Hechizo> hechizos) {
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.vida = vida;
		this.mana = mana;
		this.imagenDerecha = Herramientas.cargarImagen("sprites/gondolf_right.png");
		this.imagenIzquierda = Herramientas.cargarImagen("sprites/gondolf_left.png");
		this.imagenActual = imagenDerecha;
		this.hechizos = hechizos;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagenActual, x, y, 0, 0.35);
	}

	public void moverIzquierda() {
		if (this.x - ancho/2 > 15) {
			x -= velocidad;
			imagenActual = imagenIzquierda;
		}
	}

	public void moverDerecha() {
		if ( this.x + ancho/2 < 585) {
			x += velocidad;
			imagenActual = imagenDerecha;
		}
	}

    public void moverArriba() {
    	if (this.y - alto/2 > 20) {
	   		y -= velocidad;
       }
    }

    public void moverAbajo() {
    	if (this.y + alto/2 < 585) {
	   		y += velocidad;
   		}
    }

    public double getX() {
		return x;
	}
    
    public double getY() {
		return y;
	}
    

}

