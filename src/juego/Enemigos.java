package juego;
import entorno.Entorno;
import entorno.Herramientas;

import java.awt.*;

public class Enemigos {
	public double x;
	public double y;
	public int alto = 20;
	public int ancho = 10;
	public double velocidad;

	Image imagenDerecha;
	Image imagenIzquierda;
	Image imagenActual;


	public Enemigos(double x, double y, double velocidad) {
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.imagenDerecha = Herramientas.cargarImagen("sprites/bat_up_right.png");
		this.imagenIzquierda = Herramientas.cargarImagen("sprites/bat_up_left.png");
		this.imagenActual = imagenDerecha;
	}

	public void moverHaciaJugador(Jugador jugador) {
		double jugadorX = jugador.getX();
		double jugadorY = jugador.getY();

		if (x < jugadorX && x + ancho / 2 < 800) {
			x += velocidad;
			imagenActual = imagenDerecha;
		}

		if (x > jugadorX && x - ancho / 2 > 0) {
			x -= velocidad;
			imagenActual = imagenIzquierda;
		}
		
		if (y < jugadorY && y + alto / 2 < 600) {
			y += velocidad;
		}

		if (y > jugadorY && y - alto / 2 > 0) {
			y -= velocidad;
		}			
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagenActual, x, y, 0, 0.35);
	}
		
	public boolean colisionMagoMurcielago(Jugador jugador) {
			return jugador.x + jugador.ancho >= this.x - 1 && 
					jugador.y + jugador.alto >= this.y + 1 &&
					jugador.x <= this.x + this.ancho + 1 &&
					jugador.y <= this.y + this.alto + 1;

	}

	public boolean colisionHechizoMurcielago(Hechizo hechizo) {
		double radioHechizo = hechizo.getDiametro() / 2.0;
		return hechizo.getPosicionX() + radioHechizo >= this.x - this.ancho/2 &&
				hechizo.getPosicionY() + radioHechizo >= this.y - this.alto/2 &&
				hechizo.getPosicionX() - radioHechizo <= this.x + this.ancho/2 &&
				hechizo.getPosicionY() - radioHechizo <= this.y + this.alto/2;
	}

	public void calcularRepulsion(Enemigos otroEnemigo) {
		double dx = otroEnemigo.x - this.x;
		double dy = otroEnemigo.y - this.y;
		double distancia = Math.sqrt(dx * dx + dy * dy);
		double distanciaMinima = this.ancho + otroEnemigo.ancho * 2;

		if (distancia < distanciaMinima) {
			double fuerzaRepulsion = 50.0; // Aumentada de 30 a 50
			double factorSuavizado = 0.2; // Aumentado de 0.1 a 0.2
			double porcentajeSuperpuesto = (distanciaMinima - distancia) / distanciaMinima;

			double ajusteX = (dx / distancia) * fuerzaRepulsion * porcentajeSuperpuesto * factorSuavizado;
			double ajusteY = (dy / distancia) * fuerzaRepulsion * porcentajeSuperpuesto * factorSuavizado;

			this.x -= ajusteX;
			this.y -= ajusteY;
			otroEnemigo.x += ajusteX;
			otroEnemigo.y += ajusteY;
		}
	}
}
