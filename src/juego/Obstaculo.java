package juego;
import java.awt.*;

import entorno.*;
public class Obstaculo {
	
	double x,y;
	double ancho = 20;
	double alto = 25;
	
	public Obstaculo(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void dibujar (Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.BLUE );
	}

	public boolean colision(Jugador jugador) {
		return jugador.x + jugador.ancho >= this.x - 1 && jugador.y + jugador.alto >= this.y + 1 &&
				jugador.x <= this.x + this.ancho + 1 && jugador.y <= this.y + this.alto + 3;
		}
	}



