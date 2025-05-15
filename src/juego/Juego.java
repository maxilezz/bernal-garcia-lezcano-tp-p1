package juego;
import java.awt.Color;
import java.util.ArrayList;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	
	private Entorno entorno;

	public ArrayList<Obstaculo> obstaculos;
	
	public Jugador mago;
	
	public Enemigos enemigos;

	public Interfaz menu;
	
	
	Juego()
	{
		
		this.entorno = new Entorno(this, "La Leyenda de Gondolf", 800, 600);
		this.entorno.iniciar();

		this.menu = new Interfaz(710, 0);
		this.mago = new Jugador(200, 550);
		this.enemigos = new Enemigos(-200, -200);
		this.obstaculos = new ArrayList<>();
		obstaculos.add(new Obstaculo(300, 300));
		obstaculos.add(new Obstaculo(500, 300));
	}
	
	public void tick()
	{
		entorno.dibujarRectangulo(400, 300, 800, 600, 0, Color.BLACK);

		menu.dibujar(entorno);

		for (Obstaculo roca : obstaculos) {
			roca.dibujar(entorno);
		}
		mago.dibujar(entorno);
		enemigos.dibujar(entorno);

		if (entorno.estaPresionada('a')) {
			Jugador siguientePosicion = new Jugador(mago.x - mago.velocidad, mago.y);
			boolean hayColision = false;
			for (Obstaculo roca : obstaculos) {
				if (roca.colision(siguientePosicion)) {
					hayColision = true;
					break;
				}
			}
			if (!hayColision) {
				mago.moverIzquierda();
			}
		}
		if (entorno.estaPresionada('d')) {
			Jugador siguientePosicion = new Jugador(mago.x + mago.velocidad, mago.y);
			boolean hayColision = false;
			for (Obstaculo roca : obstaculos) {
				if (roca.colision(siguientePosicion)) {
					hayColision = true;
					break;
				}
			}
			if (!hayColision) {
				mago.moverDerecha();
			}
		}
		if (entorno.estaPresionada('w')) {
			Jugador siguientePosicion = new Jugador(mago.x, mago.y - mago.velocidad);
			boolean hayColision = false;
			for (Obstaculo roca : obstaculos) {
				if (roca.colision(siguientePosicion)) {
					hayColision = true;
					break;
				}
			}
			if (!hayColision) {
				mago.moverArriba();
			}
		}
		if (entorno.estaPresionada('s')) {
			Jugador siguientePosicion = new Jugador(mago.x, mago.y + mago.velocidad);
			boolean hayColision = false;
			for (Obstaculo roca : obstaculos) {
				if (roca.colision(siguientePosicion)) {
					hayColision = true;
					break;
				}
			}
			if (!hayColision) {
				mago.moverAbajo();
			}
		}

		enemigos.moverHaciaJugador(mago);

		
	}

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
