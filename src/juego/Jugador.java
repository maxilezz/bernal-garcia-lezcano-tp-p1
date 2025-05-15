package juego;
import java.awt.Color;

import entorno.Entorno;

public class Jugador {
	
	public double x; 
	public double y;
	public double ancho = 20;
	public double alto = 30;
	public double velocidad = 2;

	public Jugador(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, alto, 0,Color.RED);
	}

	public void moverIzquierda() {
		if (x - ancho / 2 > 0) {
			x -= velocidad;
		}
	}

	public void moverDerecha() {
		if(x - ancho / 2 < 600) {
			x += velocidad;
		}
	}
    public void moverArriba() {
	   if (y - alto / 2 > 0) {
		   y -= velocidad;
	   }
   }
    public void moverAbajo() {
	  if (y + alto / 2 < 600) {
		  y += velocidad;
	  }
    }
}
