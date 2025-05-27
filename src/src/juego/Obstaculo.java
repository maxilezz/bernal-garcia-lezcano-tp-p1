package juego;

import java.awt.Color;

import entorno.Entorno;

public class Obstaculo {
	
	double x,y;
	double ancho = 30;
	double alto = 30;
	
	
	public Obstaculo (double x, double y, double ancho, double alto) {
		this.x=x;
		this.y=y;
		this.ancho = ancho;
		this.alto = alto;
	}

	
	public void dibujar (Entorno entorno) {
		entorno.dibujarRectangulo(x, y, alto, ancho, 0, Color.CYAN);
	}
	
	public boolean colision(Jugador jugador) {
		return jugador.x + jugador.ancho >= this.x && 
				jugador.y + jugador.alto >= this.y &&
				
				jugador.x <= this.x + this.ancho &&
				jugador.y <= this.y + this.alto;
		}
}


