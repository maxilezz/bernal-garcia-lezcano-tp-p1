package juego;
import java.awt.Color;
import entorno.Entorno;

public class Jugador {	
	public double x; 
	public double y;
	public double ancho = 20;
	public double alto = 30;
	public double velocidad;
	public int vida;

	public Jugador(double x, double y, double velocidad, int vida) {
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.vida = vida;
	}
	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, alto, 0,Color.RED);
	}
	public void moverIzquierda() {
		if (this.x - ancho/2 > 0) {
			x -= velocidad;
		}
	}
	public void moverDerecha() {
		if ( this.x + ancho/2 < 600) {
			x += velocidad;
		}	
	}
    public void moverArriba() {
    	if (this.y - alto/2 > 0) {
	   y -= velocidad;
       }
    }
    public void moverAbajo() {
    	if (this.y + alto/2 < 600) {
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

