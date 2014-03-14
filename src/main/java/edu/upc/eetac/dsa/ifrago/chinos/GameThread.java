package edu.upc.eetac.dsa.ifrago.chinos;

import java.io.IOException;
import java.net.Socket;

public class GameThread implements Runnable {
	private Thread thread;
	private Player[] players;
	private int connected = 0;
	private int montotal=0;

	public GameThread() {//Cuando se inicializa el thread este crea dos objetos de la clase Player
		players = new Player[2];
	}

	public void addPlayer(Socket socket) {
		try {

			players[connected] = new Player(socket);//añadimos un objeto tipo Jugador, pasandole el socket de tal.
			if (connected == 0)
				players[connected].sendWait();//Si es el primero, a este le enviamos un WAIT
			else {
				thread = new Thread(this);//Si no es el primero, Ya podemos comportarnos como Thread y dejar al Hilo del Main que siga escuchando
				thread.start();

			}

			connected++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		//Enviamos los versus a cada jugador
		players[0].sendVersus(players[1].getName());
		players[1].sendVersus(players[0].getName());

		try {
			players[1].sendWaitBet();//Al segundo jugador le decimos que espero
			players[0].sendYourBet();//Al primer jugador le decimos que apueste
			
			players[0].sendBetOf(players[0].getName(), players[0].getBet());//Enviamos al jgador1, la apuesta que ha hecho
			players[1].sendBetOf(players[0].getName(), players[0].getBet());//Enviamos al jugador2, la apuesta que ha hecho el jugador1
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			players[0].sendWaitBet();//Al primer jugador le decimos que espero
			players[1].sendYourBet();//Al segundo jugador le decimos que apueste
			
			players[1].sendBetOf(players[1].getName(), players[1].getBet());//Enviamos al jgador2, la apuesta que ha hecho
			players[0].sendBetOf(players[1].getName(), players[1].getBet());//Enviamos al jugador1, la apuesta que ha hecho el jugador2
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Miramos el ganador
		montotal=players[0].getcoinsPlayed()+players[1].getcoinsPlayed();
		if( montotal== players[0].getBet()){
			players[0].sendWinner(players[0].getName());
			players[1].sendWinner(players[0].getName());
		}else if ( montotal== players[1].getBet()){
			players[0].sendWinner(players[1].getName());
			players[1].sendWinner(players[1].getName());
		}else{
			players[0].sendWinner();
			players[1].sendWinner();
		}
		
		//Cerramos todo
		try {
			players[0].CloseAll();
			players[1].CloseAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}