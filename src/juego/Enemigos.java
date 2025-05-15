package juego;
import entorno.Entorno;
import java.awt.Color;

public class Enemigos {
	
	public double x;
	public double y;
	public int alto = 20;
	public int ancho = 10;
	public double velocidad = 0.5;
	public int enemy[];
	
	public Enemigos(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarTriangulo(x, y, ancho, alto, 0, Color.YELLOW);
	}
	
	public void moverHaciaJugador(Jugador jugador) {
		
		double jugadorX = jugador.x;
		double jugadorY = jugador.y;
		
		
		if (x < jugadorX && x + ancho / 2 < 800) {
			x += velocidad;
		}
		
		if (x > jugadorX && x - ancho / 2 > 0) {
			x -= velocidad;
		}
		
		if (y < jugadorY && y + alto / 2 < 600) {
			y += velocidad;
		}
		
		if (y > jugadorY && y - alto / 2 > 0) {
			y -= velocidad;
		}
	}
	
	
	
	
}
