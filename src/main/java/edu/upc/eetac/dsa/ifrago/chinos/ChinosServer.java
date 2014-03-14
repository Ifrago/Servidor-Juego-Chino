package edu.upc.eetac.dsa.ifrago.chinos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChinosServer {//El servidor lo único que tiene que hacer es aceptar connexiones, y cada dos crear un thread de partida

	public static void main(String args[]) {
		int numplayers = 0;
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(4567);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Server started");
		GameThread game = new GameThread();

		while (true) {
			try {
				Socket socket = ss.accept();
				System.out.println("client accepted");
				game.addPlayer(socket);//Añadimos un Usuario al objeto game.
				numplayers++;
				if (numplayers % 2 == 0) {// numplayers mod 2 ==0, es decir cuando numplayers es par.
					game = new GameThread();//Creamos otro Thread ( partida )
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
