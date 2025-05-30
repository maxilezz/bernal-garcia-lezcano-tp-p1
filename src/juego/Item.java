package juego;
import entorno.Entorno;
import java.awt.Color;
import java.awt.Image;

public class Item{
	
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private int recarga;
	Image imagenVida;
	Image imagenMana;
	
	public Item(double x, double y, double ancho, double alto, int recarga) {
		
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.recarga = recarga;
	}


	public void dibujarItemVida(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.WHITE);
	}
	
	public void dibujarItemMana(Entorno entorno){
		entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.WHITE);
	}
}
