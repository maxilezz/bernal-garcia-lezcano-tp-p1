package juego;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
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
	public int ultimoItemGeneradoEn;

	public Obstaculo[] roca;

	public ArrayList<Hechizo> hechizos;
	public Hechizo hechizoAgua;
	public Hechizo hechizoFuego;

	public boolean gameOver;
	public String hechizoSeleccionado;
	
	public ArrayList<Item> itemVida;

	Juego() {

		this.entorno = new Entorno(this, "El camino de Gondolf", 800, 600);
		this.entorno.iniciar();

		inicializarJuego();

	}
	
	boolean verificarColisionRoca(double nuevaX, double nuevaY) {
		for (Obstaculo rock : roca) {
			if (rock.colision(mago, nuevaX, nuevaY)) {
				return false;
			}
		}
		return true;
	}

	private void pantallaFinDeJuego() {
		entorno.cambiarFont("Times New Roman", 80, Color.WHITE);
		entorno.escribirTexto(this.mago.vida == 0 ? "Game Over!" : "Ganaste!", (double) 200, (double) 200);
		entorno.cambiarFont("Times New Roman", 30, Color.WHITE);
		entorno.escribirTexto("Presiona ENTER para jugar de nuevo", (double) 175, (double) 350);
	}

	private void generarMurcielago(Random random) {
		int lado = random.nextInt(4); // 0: arriba, 1: derecha, 2: abajo, 3: izquierda
		double x = 0, y = 0;

		switch (lado) {
			case 0: // arriba
				x = random.nextInt(750);
				y = -50;
				break;
			case 1: // derecha
				x = 750;
				y = random.nextInt(600);
				break;
			case 2: // abajo
				x = random.nextInt(750);
				y = 650;
				break;
			case 3: // izquierda
				x = -50;
				y = random.nextInt(600);
				break;
		}

		double velocidad = 0.7;
		murcielagos.add(new Enemigos(x, y, velocidad));
	}

	private void inicializarJuego() {

		this.menu = new Interfaz(750, 400, 0, 0, 0.3);

		this.hechizos = new ArrayList<>();
		hechizos.add(new Hechizo("HechizoBase", 650, 200, 40, 500, 0,  Herramientas.cargarImagen("sprites/water.png")));
		hechizos.add(new Hechizo("HechizoIncendiario", 750, 200, 70, 700, 25,  Herramientas.cargarImagen("sprites/fire.png")));
		hechizos.add(new Hechizo("EnemigosPesados", 650, 300, 50, 2000, 10 , Herramientas.cargarImagen("sprites/timefreeze.png")));
		hechizos.add(new Hechizo("SueloSanto", 750, 300, 50, 2000, 50 , Herramientas.cargarImagen("sprites/suelo.png")));
		
		this.mago = new Jugador(300, 300, 3, 100, 100, hechizos);
		
		
		this.murcielagos = new LinkedList<>();
		this.murcielagosEliminados = 0;
		this.ultimoItemGeneradoEn = 0;
		this.murcielagosVelocidad = 0.7;
		this.gameOver = false;
		
		

		this.itemVida = new ArrayList<>();
		
		
		roca = new Obstaculo[6];
		Random random = new Random();

		for (int i = 0; i < 10; i++) {
			generarMurcielago(random);
		}

		for (int i = 0; i < roca.length; i++) {
			double arribaY = random.nextInt(50,250);
			double abajoY = random.nextInt(400, 550);
			double izquierdaX = random.nextInt(50, 250);
			double derechaX = random.nextInt(450, 550);

			double[] posicionesX = {izquierdaX, derechaX};
			double[] posicionesY = {arribaY, abajoY};

			roca[i] = new Obstaculo(posicionesX[random.nextInt(2)], posicionesY[random.nextInt(2)], 30, 30);

			for (int j = 0; j < i; j++) {  // Solo compara con obstáculos ya creados
				if (roca[i].spawnEnSiMismos(roca[j])) {
					roca[i] = new Obstaculo(posicionesX[random.nextInt(2)], posicionesY[random.nextInt(2)], 30, 30);
					j = -1;  // Reinicia el bucle para verificar la nueva posición contra todos los obstáculos
				}
			}
		}
	}

	public void tick() {

		// Le da tiempo para que todo lo del entorno cargue.
		if (entorno.numeroDeTick() < 50) {
			return;
		}

		if (gameOver) {
			pantallaFinDeJuego();

			if (entorno.sePresiono(entorno.TECLA_ENTER)) {
				inicializarJuego();
			}
			return;
		}

		// Dibuja el entorno.
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

		Random random = new Random();

		for (int i = murcielagos.size() - 1; i >= 0; i--) {
			Enemigos enemy = murcielagos.get(i);

			if (enemy.colisionMagoMurcielago(mago)) {
				murcielagos.remove(i);
				murcielagosEliminados++;
				mago.vida = mago.vida - 10;

				generarMurcielago(random);
			}
		}

		menu.dibujar(entorno);
		menu.dibujarBarraVida(entorno, mago.vida, 5, Herramientas.cargarImagen("sprites/vida.png"));
		menu.dibujarBarraMana(entorno, mago.mana, 25, Herramientas.cargarImagen("sprites/energia.png"));
		entorno.cambiarFont("Times New Roman", 30, Color.WHITE);
		entorno.escribirTexto("x " + murcielagosEliminados, 700, 25);


		// Verificar clicks en los botones de hechizos
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

			if (hechizo.estaActivo()) {
				hechizo.actualizar(entorno);
				for (int i = murcielagos.size() - 1; i >= 0; i--) {
					Enemigos murcielago = murcielagos.get(i);
					if (murcielago.colisionHechizoMurcielago(hechizo)) {
						murcielagos.remove(i);
						murcielagosEliminados++;
						generarMurcielago(random);
					}
					
					if (murcielagosEliminados % 10 == 0 && murcielagosEliminados != ultimoItemGeneradoEn) {
		                itemVida.add(new Item(murcielago.getX(), murcielago.getY(), 30, 30, 0));
		                ultimoItemGeneradoEn = murcielagosEliminados; // Actualiza el último múltiplo
		            }
		
				}
				
			
			}
			
			for(Item item : itemVida) {
				
				item.dibujarItemVida(entorno);
				break;
			}
				
			}
		
			
		
		

		// Dibujar botones de hechizos y verificar selección
        for (Hechizo hechizo : hechizos) {
            menu.dibujarBoton(entorno, hechizo.imagenHechizo, hechizo.x, hechizo.y,
                    hechizoSeleccionado != null && hechizoSeleccionado.equals(hechizo.getNombre()), hechizo.getCostoMana());

			// Este booleano lo que hace es confirma si el X y el Y del mouse estan dentro del cuadrado del hechizo.
            boolean clickHechizo = entorno.mouseX() > hechizo.x - 50 && entorno.mouseX() < hechizo.x + 50 &&
					entorno.mouseY() > hechizo.y - 50 && entorno.mouseY() < hechizo.y + 50 &&
					entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO);

			if(mago.mana < hechizo.getCostoMana()){	
				clickHechizo = false;
					}
			if (clickHechizo) {
				hechizoSeleccionado = hechizo.getNombre();
			}
       
			
        }


		mago.dibujar(entorno);
		

		if (entorno.estaPresionada('a')) {
			double nuevaX = mago.x - mago.velocidad;
			if (verificarColisionRoca(nuevaX, mago.y)) {
				mago.moverIzquierda();
			}
		}

		if (entorno.estaPresionada('d')) {
			double nuevaX = mago.x + mago.velocidad;
			if (verificarColisionRoca(nuevaX, mago.y)) {
				mago.moverDerecha();
			}
		}

		if (entorno.estaPresionada('w')) {
			double nuevaY = mago.y - mago.velocidad;
			if (verificarColisionRoca(mago.x, nuevaY)) {
				mago.moverArriba();
			}
		}

		if (entorno.estaPresionada('s')) {
			double nuevaY = mago.y + mago.velocidad;
			if (verificarColisionRoca(mago.x, nuevaY)) {
				mago.moverAbajo();
			}
		}

		if (mago.vida == 0 || murcielagosEliminados == 50) {
			gameOver = true;
		}
		

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
