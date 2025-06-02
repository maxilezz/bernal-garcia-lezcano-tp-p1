package juego;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

import entorno.*;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {


	private final Entorno entorno;
	public Jugador mago;

	public Interfaz menu;

	public LinkedList<Enemigos> murcielagos;
	public int murcielagosEliminados;
	public double murcielagosVelocidad;

	public Obstaculo[] roca;

	public ArrayList<Hechizo> hechizos;

	public boolean gameOver;
	public String hechizoSeleccionado;

	Juego() {

		this.entorno = new Entorno(this, "El camino de Gondolf", 800, 600);
		this.entorno.iniciar();

		inicializarJuego();

	}


	private void pantallaFinDeJuego() {
		if (mago.vida == 0) {
			entorno.dibujarImagen(Herramientas.cargarImagen("sprites/gameover1.png"), 400, 300, 0, 0.8);
		} else {
			entorno.dibujarImagen(Herramientas.cargarImagen("sprites/gamewin1.png"), 400, 300, 0, 0.8);
		}
	}

	private void inicializarJuego() {

		this.gameOver = false;

		this.menu = new Interfaz(750, 400, 0, 0, 0.3);

		this.hechizos = new ArrayList<>();
		hechizos.add(new Hechizo("HechizoBase", 650, 225, 40, 500, 0,  Herramientas.cargarImagen("sprites/water.png")));
		hechizos.add(new Hechizo("HechizoIncendiario", 750, 225, 70, 700, 25,  Herramientas.cargarImagen("sprites/fire.png")));
		hechizos.add(new Hechizo("EnemigosPesados", 650, 325, 600, 1000, 10 , Herramientas.cargarImagen("sprites/timefreeze.png")));
		hechizos.add(new Hechizo("SueloSanto", 750, 325, 200, 6000, 30 , Herramientas.cargarImagen("sprites/suelo.png")));

		this.mago = new Jugador(300, 300, 3, 100, 100, hechizos);


		this.murcielagos = new LinkedList<>();
		this.murcielagosEliminados = 0;
		this.murcielagosVelocidad = 0.6;

		roca = new Obstaculo[6];
		Random random = new Random();

		for (int i = 0; i < 10; i++) {
			murcielagos.add(Enemigos.generarMurcielago());
		}

		for (int i = 0; i < roca.length; i++) {
			double arribaY = random.nextInt(50,250);
			double abajoY = random.nextInt(400, 550);
			double izquierdaX = random.nextInt(50, 250);
			double derechaX = random.nextInt(450, 550);

			double[] posicionesX = {izquierdaX, derechaX};
			double[] posicionesY = {arribaY, abajoY};

			roca[i] = new Obstaculo(posicionesX[random.nextInt(2)], posicionesY[random.nextInt(2)], 30, 30);

			for (int j = 0; j < i; j++) {
				if (roca[i].spawnEnSiMismos(roca[j])) {
					roca[i] = new Obstaculo(posicionesX[random.nextInt(2)], posicionesY[random.nextInt(2)], 30, 30);
					j = -1;
				}
			}
		}

	}

	public void tick() {

		if (entorno.numeroDeTick() < 100) {
			return;
		}

		if (gameOver) {
			pantallaFinDeJuego();

			if (entorno.sePresiono(entorno.TECLA_ENTER)) {
				inicializarJuego();
			}
			return;
		}


		entorno.dibujarImagen(Herramientas.cargarImagen("sprites/background_1.png"), 300, 300, 0, 0.6);

		for (Obstaculo rock: roca) {
			rock.dibujar(entorno);
		}

		for (Enemigos enemy: murcielagos) {
			enemy.dibujar(entorno);
			enemy.moverHaciaJugador(mago);
		}

		for (int i = 0; i < murcielagos.size(); i++) {
			Enemigos enemy = murcielagos.get(i);
			for (int j = i + 1; j < murcielagos.size(); j++) {
				enemy.calcularRepulsion(murcielagos.get(j));
			}
		}

		for (int i = murcielagos.size() - 1; i >= 0; i--) {
			Enemigos enemy = murcielagos.get(i);

			if (enemy.colisionMagoMurcielago(mago)) {
				murcielagos.remove(i);
				mago.vida = mago.vida - 10;

				murcielagos.add(Enemigos.generarMurcielago());
			}
		}


		for (Hechizo hechizo : hechizos) {
			if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
				double mouseX = entorno.mouseX();
				double mouseY = entorno.mouseY();

				if (hechizoSeleccionado != null && mouseX > 0 && mouseY > 0 && mouseX < 600 && mouseY < 600) {
					if (hechizoSeleccionado.equals(hechizo.getNombre())) {
						hechizo.lanzar(mouseX, mouseY);
						mago.mana -= hechizo.getCostoMana();
						hechizoSeleccionado = null;
					}
				}
			}

			if (hechizoSeleccionado != null && hechizoSeleccionado.equals(hechizo.getNombre())) {

				hechizo.dibujarAreaEfecto(entorno);
			}

			if (hechizo.estaActivo()) {
				hechizo.actualizar(entorno);

				for (int i = murcielagos.size() - 1; i >= 0; i--) {
					Enemigos murcielago = murcielagos.get(i);

					if (Objects.equals(hechizo.getNombre(), "SueloSanto") && murcielago.colisionHechizoMurcielago(hechizo)) {

						double dx = murcielago.x - hechizo.getPosicionX();
						double dy = murcielago.y - hechizo.getPosicionY();


						double distancia = Math.sqrt(dx * dx + dy * dy);
						if (distancia > 0) {
							dx = dx / distancia;
							dy = dy / distancia;

							murcielago.x += dx * murcielago.velocidad * 2;
							murcielago.y += dy * murcielago.velocidad * 2;
						}
					}

					if (murcielago.colisionHechizoMurcielago(hechizo)) {
						if (hechizo.getNombre().equals("EnemigosPesados")) {
							murcielago.velocidad = 0.1;
						} else {
							murcielagos.remove(i);
							murcielagosEliminados++;
							murcielagos.add(Enemigos.generarMurcielago());
						}

					}

				}
			}
		}

		menu.dibujar(entorno);
		menu.dibujarBarraVida(entorno, mago.vida, 5, Herramientas.cargarImagen("sprites/vida.png"));
		menu.dibujarBarraMana(entorno, mago.mana, 25, Herramientas.cargarImagen("sprites/energia.png"));
		entorno.cambiarFont("Times New Roman", 30, Color.WHITE);
		entorno.escribirTexto("x " + murcielagosEliminados, 700, 25);
		entorno.dibujarImagen(Herramientas.cargarImagen("sprites/logo.png"), 700, 475, 0, 0.2);


        for (Hechizo hechizo : hechizos) {
			boolean sinMana = hechizo.getCostoMana() > mago.mana;
            menu.dibujarBoton(entorno, hechizo.imagenHechizo, hechizo.x, hechizo.y,
                    hechizoSeleccionado != null && hechizoSeleccionado.equals(hechizo.getNombre()), sinMana, hechizo.getCostoMana());

            boolean clickHechizo = entorno.mouseX() > hechizo.x - 50 && entorno.mouseX() < hechizo.x + 50 &&
					entorno.mouseY() > hechizo.y - 50 && entorno.mouseY() < hechizo.y + 50 &&
					entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO);

			if (sinMana){
				clickHechizo = false;
			}

			if (clickHechizo) {
				hechizoSeleccionado = hechizo.getNombre();
			}
			
        }


		mago.dibujar(entorno);

		if (entorno.estaPresionada('a')) {
			double nuevaX = mago.x - mago.velocidad;
			if (mago.verificarColisionRoca(nuevaX, mago.y, roca)) {
				mago.moverIzquierda();
			}
		}

		if (entorno.estaPresionada('d')) {
			double nuevaX = mago.x + mago.velocidad;
			if (mago.verificarColisionRoca(nuevaX, mago.y, roca)) {
				mago.moverDerecha();
			}
		}

		if (entorno.estaPresionada('w')) {
			double nuevaY = mago.y - mago.velocidad;
			if (mago.verificarColisionRoca(mago.x, nuevaY, roca)) {
				mago.moverArriba();
			}
		}

		if (entorno.estaPresionada('s')) {
			double nuevaY = mago.y + mago.velocidad;
			if (mago.verificarColisionRoca(mago.x, nuevaY, roca)) {
				mago.moverAbajo();
			}
		}

		if (mago.vida <= 0 || murcielagosEliminados >= 50) {
			gameOver = true;
		}

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
