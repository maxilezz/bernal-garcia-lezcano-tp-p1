package juego;
import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Color;

public class Coleccionable {
	
	public double x;
	public double y;
	public double ancho;
	public double alto;
	
	public Coleccionable() {
		
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarTriangulo(x, y, 0, 0, alto, Color.WHITE);
	}
}
