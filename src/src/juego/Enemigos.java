package juego;
import entorno.Entorno;
import java.awt.Color;

public class Enemigos {
	public double x;
	public double y;
	public int alto = 20;
	public int ancho = 10;
	public double velocidad;

	
	public Enemigos(double x, double y, double velocidad) {
		this.x = x;
		this.y = y;	
		this.velocidad = velocidad; 
	}	
	public void moverHaciaJugador(Jugador jugador) {
		double jugadorX = jugador.getX();
		double jugadorY = jugador.getY();
		
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
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarTriangulo(x, y, ancho, alto, 0,Color.YELLOW );
	}
		
		
	public boolean colisionMagoMurcielago(Jugador jugador) {
			return jugador.x + jugador.ancho >= this.x - 1 && 
					jugador.y + jugador.alto >= this.y + 1 &&
					
					jugador.x <= this.x + this.ancho + 1 &&
					jugador.y <= this.y + this.alto + 1;

	}
}
