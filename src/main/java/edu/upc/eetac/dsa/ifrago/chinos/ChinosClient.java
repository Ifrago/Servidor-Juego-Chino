package edu.upc.eetac.dsa.ifrago.chinos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChinosClient {
	public static void main(String args[]) throws Exception {
		Socket socket = new Socket("localhost", 4567);
		String name = args[0];
		int coins = 2;
		int bet = 3;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

		writer.println("PLAY " + name);
		boolean finished = false;
		while (!finished) {
			String response = reader.readLine();
			if (response.startsWith("WAIT"))
				System.out.println(response);
			else if (response.startsWith("VERSUS"))
				System.out.println(response);
			else if (response.startsWith("YOUR BET"))
				writer.println("MY BET " + coins + " " + bet);
			else if (response.startsWith("BET OF"))
				System.out.println(response);
			else if(response.startsWith("WINNER")){
				System.out.println(response);
				finished=true;
			}
		}
		reader.close();
		writer.close();
		socket.close();
	}
}
