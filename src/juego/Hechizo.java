package juego;
import java.awt.*;

import entorno.*;

public class Hechizo {

	public String nombre;
	public double x;
	public double y;
	private double posicionX;
	private double posicionY;
	private boolean activo;
	private long tiempoInicio;
	private double diametro;
	private long duracion;
	private int costoMana;

	Image imagenHechizo;

	public Hechizo(String nombre, int x, int y, double diametro, long duracion, int costoMana,  Image imagenHechizo) {
		this.nombre = nombre;
		this.x = x;
		this.y = y;
		this.diametro = diametro;
		this.imagenHechizo = imagenHechizo;
		this.duracion = duracion;
		this.costoMana = costoMana;
	}

	public boolean colisionConMurcielago(Enemigos murcielago) {
		double radio = this.diametro / 2;
		return murcielago.x + murcielago.ancho >= this.x - radio &&
				murcielago.y + murcielago.alto >= this.y - radio &&
				murcielago.x <= this.x + radio &&
				murcielago.y <= this.y + radio;
	}

	public void dibujarAgua(Entorno entorno, double x, double y) {
		Image spellAgua = Herramientas.cargarImagen("sprites/spellagua.png");
		entorno.dibujarImagen(spellAgua,x, y, 0, 0.25);

	}

	public void dibujarFuego(Entorno entorno, double x, double y) {
		Image spellFuego = Herramientas.cargarImagen("sprites/spellfuego.png");
		entorno.dibujarImagen(spellFuego, x, y, 0, 0.50);
	}


	public void dibujarHexa(Entorno entorno, double x, double y) {
		Image hexa = Herramientas.cargarImagen("sprites/spellhexa.png");
		entorno.dibujarImagen(hexa, x, y, 0, 0.85);
	}

	public void lanzar(double x, double y) {
		this.posicionX = x;
		this.posicionY = y;
		this.activo = true;
		this.tiempoInicio = System.currentTimeMillis();
	}


	public boolean estaActivo() {
		if (!activo) return false;
		if (System.currentTimeMillis() - tiempoInicio > this.duracion) {
			activo = false;
			return false;
		}
		return true;
	}

	public void actualizar(Entorno entorno) {
		if (nombre.equals("HechizoBase")) {
			dibujarAgua(entorno, posicionX, posicionY);
		} else if (nombre.equals("HechizoIncendiario")) {
			dibujarFuego(entorno, posicionX, posicionY);
		} else if (nombre.equals("SueloSanto")) {
			dibujarHexa(entorno, posicionX, posicionY);
		}
	}

	public void dibujarAreaEfecto(Entorno entorno) {
		double MouseX = entorno.mouseX();
		double MouseY = entorno.mouseY();
		Image hechizoArea = Herramientas.cargarImagen("sprites/mira.png");
		if (nombre.equals("HechizoBase")) {
			entorno.dibujarImagen(hechizoArea, MouseX, MouseY, 0, 0.30);
		} else if (nombre.equals("HechizoIncendiario")) {
			entorno.dibujarImagen(hechizoArea, MouseX, MouseY, 0, 0.45);
		} else if (nombre.equals("SueloSanto")) {
			entorno.dibujarImagen(hechizoArea, MouseX, MouseY, 0, 0.75);
		} else if (nombre.equals("EnemigosPesados")) {
			entorno.dibujarImagen(hechizoArea, MouseX, MouseY, 0, 0.50);
		}
	}

	public String getNombre() {
		return nombre;
	}

	public double getDiametro() {
		return diametro;
	}

	public double getPosicionX() {
		return posicionX;
	}

	public double getPosicionY() {
		return posicionY;
	}

	public int getCostoMana() {
		return costoMana;
	}



}
