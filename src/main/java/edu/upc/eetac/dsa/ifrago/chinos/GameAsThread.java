package edu.upc.eetac.dsa.ifrago.chinos;

import java.io.IOException;
import java.net.Socket;

public class GameAsThread extends Thread {

	private Player[] players;
	private int connected = 0;

	public GameAsThread() {
		super();
		players = new Player[2];
	}

	public void addPlayer(Socket socket) {
		try {

			players[connected] = new Player(socket);
			if (connected == 0)
				players[connected].sendWait();
			else {

				start();

			}

			connected++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		players[0].sendVersus(players[1].getName());
		players[1].sendVersus(players[0].getName());

		try {
			players[1].sendWaitBet();
			players[0].sendYourBet();

			players[0].sendBetOf(players[0].getName(), players[0].getBet());
			players[1].sendBetOf(players[0].getName(), players[0].getBet());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
