package juego;
import entorno.Entorno;
import java.awt.Color;

public class Item{
	
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private int vida;
	private int mana;
	
	public Item(int x, int y, int ancho, int alto, int vida, int mana) {
		
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.vida = vida;
		this.mana = mana;
	}

	public void dibujarItem(Entorno entorno) {
		entorno.dibujarTriangulo(x, y, 0, 0, alto, Color.WHITE);
	}
}
