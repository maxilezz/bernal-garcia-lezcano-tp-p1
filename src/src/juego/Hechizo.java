package juego;
import java.awt.Color;
import entorno.*;

public class Hechizo {
	
	public double x;
	public double y;
	
	
	public Hechizo() {
		this.x = x;
		this.y = x;
	}
	
	public void dentroPantalla(Entorno cursor) {
		
		
	}
	
	public void lanzarHechizo(Entorno entorno) {
		
		if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
		
		entorno.dibujarCirculo(x, y, x, Color.cyan);
		}
		
		
		
		
	}

}
