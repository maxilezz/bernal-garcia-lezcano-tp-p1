package juego;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import entorno.*;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego{	
	private Entorno entorno;
	public Jugador mago;
	
	public Interfaz menu;
	public Interfaz spellUno;
	public Interfaz spellDos;
	
	public LinkedList<Enemigos> murcielagos;
	public int murcielagosEliminados;
	public double murcielagosVelocidad;
	
	public Obstaculo[] roca;

	public Hechizo bomba;
	public Coleccionable item;
	

	public boolean gameOver = false;

	Juego(){
		
		this.entorno = new Entorno(this, "La Leyenda de Gondolf", 800, 600);
		this.entorno.iniciar();
		
		this.spellUno = new Interfaz(710, 40, 150, 100);
		this.spellDos = new Interfaz(710, 150, 150, 100);
		this.menu = new Interfaz(700, 0, 200, 1200);
		
		this.mago = new Jugador(200,550,3, 3);
		
		this.murcielagos = new LinkedList<>();
		this.murcielagosEliminados = 0;
		this.murcielagosVelocidad = 0.8;
		this.item = new Coleccionable();

	
        roca = new Obstaculo [6];
        
		Random random = new Random();

            for (int i = 0; i < 7; i++) {
        	
            double x = 500 + random.nextInt(500);
            if(i%2==0) {
            	x =- random.nextInt(500);
            }
            double y = random.nextInt(900);           
            double velocidad = murcielagosVelocidad; 
            murcielagos.add(new Enemigos(x, y, velocidad));
        }
        
            for ( int i = 0; i< roca.length; i++) {
        	double x = random.nextInt(550);
        	double y = random.nextInt(550);
        	roca[i] = new Obstaculo(x,y,30,30);
	}
	}
	
	public void tick()
	{
		
		// Le da tiempo para que todo lo del entorno cargue.
		
		if(entorno.numeroDeTick() < 5) {
			return;
		}
		
		// Dibuja el entorno.
		
		entorno.dibujarRectangulo(400, 300, 800, 600, 0, Color.BLACK);
		
		if (gameOver) {
			entorno.cambiarFont("Times New Roman", 80, Color.WHITE);
			entorno.escribirTexto(mago.vida == 0 ? "Game Over!" : "Ganaste!", (double) 600 / 3, (double) 600 / 2);
			entorno.cambiarFont("Times New Roman", 30, Color.WHITE);
			entorno.escribirTexto("Presiona ENTER para jugar de nuevo", (double) 700 / 4, (double) 600 / 2 + 50);
	

			if (entorno.sePresiono(entorno.TECLA_ENTER)) {
				reiniciarJuego();
			}
			return;
		}
		
              for (Obstaculo rock: roca) {
		           rock.dibujar(entorno);
		
		}
		
	        for (Enemigos enemy : murcielagos) {
	        	 enemy.dibujar(entorno);
	        	 enemy.moverHaciaJugador(mago);        	
        }
	        
	        for (int i = 0; i < murcielagos.size(); i++) {
	        	
	        	Enemigos enemy = murcielagos.get(i);
	        	
	        	if(enemy.colisionMagoMurcielago(mago)) {
	        		
	        		murcielagos.remove(i);
	        		murcielagosEliminados++;
	        		mago.vida--;
	        		murcielagos.add(new Enemigos(i,i,murcielagosVelocidad));
	        	}
	        	
	        	if (mago.vida == 0 || murcielagosEliminados == 50) {
	        		gameOver = true;
	        	}
	        }
	        
	    menu.dibujar(entorno);
	    spellUno.dibujarBotones(entorno);
	    spellDos.dibujarBotones(entorno);
		
		mago.dibujar(entorno);
		
		if (entorno.estaPresionada('a')) {
			Jugador siguientePosicion = new Jugador(mago.x - mago.velocidad, mago.y, mago.velocidad, mago.vida);
			boolean hayColision = false;
			for (Obstaculo rock : roca) {
				if (rock.colision(siguientePosicion)) {
					hayColision = true;
					break;
				}
			}
			if (!hayColision) {
				mago.moverIzquierda();
			}
		}
		if (entorno.estaPresionada('d')) {
			Jugador siguientePosicion = new Jugador(mago.x + mago.velocidad, mago.y, mago.velocidad, mago.vida);
			boolean hayColision = false;
			for (Obstaculo rock : roca) {
				if (rock.colision(siguientePosicion)) {
					hayColision = true;
					break;
				}
			}
			if (!hayColision) {
				mago.moverDerecha();
			}
		}
		if (entorno.estaPresionada('w')) {
			Jugador siguientePosicion = new Jugador(mago.x, mago.y - mago.velocidad, mago.velocidad, mago.vida);
			boolean hayColision = false;
			for (Obstaculo rock : roca) {
				if (rock.colision(siguientePosicion)) {
					hayColision = true;
				}
			}
			if (!hayColision) {
				mago.moverArriba();
			}
		}
		if (entorno.estaPresionada('s')) {
			Jugador siguientePosicion = new Jugador(mago.x, mago.y + mago.velocidad, mago.velocidad, mago.vida);
			boolean hayColision = false;
			for (Obstaculo rock : roca) {
				if (rock.colision(siguientePosicion)) {
					hayColision = true;
					break;
				}
			}
			if (!hayColision) {
				mago.moverAbajo();
			}
		}

		
	}
		
	
	

	private void reiniciarJuego() {
		entorno.dibujarRectangulo(400, 300, 800, 600, 0, Color.BLACK);
		
		gameOver = false;
		this.mago = new Jugador(200,550,5, 3);
		this.murcielagos = new LinkedList<>();
		murcielagosEliminados = 0;
	
        roca = new Obstaculo [6];
		Random random = new Random();

            for (int i = 0; i < 7; i++) {
            double x = random.nextInt(1000);                  
            double y = random.nextInt(900);           
            double velocidad = 0.8;
            murcielagos.add(new Enemigos(x, y, velocidad));
        }
        
            for (int i = 0; i< roca.length; i++) {
        	double x = random.nextInt(550);
        	double y = random.nextInt(550);
        	roca[i] = new Obstaculo(x,y,30,30);
	}
				
	}

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
