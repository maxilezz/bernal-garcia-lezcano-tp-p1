package juego;

import java.awt.*;

import entorno.Entorno;
import entorno.Herramientas;

public class Obstaculo {

	double x, y;
	double ancho = 30;
	double alto = 30;
	Image imagen;


	public Obstaculo(double x, double y, double ancho, double alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;

		this.imagen = Herramientas.cargarImagen("sprites/roca.png");
	}


	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, 0, 0.5);
	}

	public boolean colision(Jugador jugador, double nuevaX, double nuevaY) {
		return nuevaX + jugador.ancho > this.x - this.ancho / 2 &&
				nuevaX - jugador.ancho < this.x + this.ancho / 2 &&
				nuevaY + jugador.alto > this.y - this.alto &&
				nuevaY - jugador.alto / 2 < this.y + this.alto / 2;
	}

	public boolean spawnEnSiMismos(Obstaculo otraRoca) {
		return otraRoca.x + otraRoca.ancho > this.x - this.ancho &&
				otraRoca.x - otraRoca.ancho < this.x + this.ancho &&
				otraRoca.y + otraRoca.alto > this.y - this.alto &&
				otraRoca.y - otraRoca.alto < this.y + this.alto;
	}

}


