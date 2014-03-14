package edu.upc.eetac.dsa.ifrago.chinos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class Player {
	private String name;
	private int coinsPlayed;
	private int bet;

	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;

	public Player(Socket socket) throws IOException {
		super();
		this.socket = socket;
		reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		String play = reader.readLine();
		StringTokenizer st = new StringTokenizer(play, " ");
		String cmd = st.nextToken();
		if (cmd.equals("PLAY"))
			this.name = st.nextToken();

		writer = new PrintWriter(socket.getOutputStream(), true);
	}

	public String getName() {
		return name;
	}

	public void sendWait() {
		writer.println("WAIT");
	}

	public void sendWaitBet() {
		writer.println("WAIT BET");
	}
	
	public void sendBetOf(String player, int bet) {
		writer.println("BET OF " +player+ " " + bet);
	}

	public void sendYourBet() throws IOException {
		writer.println("YOUR BET");
		String response = reader.readLine();
		StringTokenizer st = new StringTokenizer(response, " ");//Equivale al STRTOK de lenguage C.
		st.nextToken(); // MY
		st.nextToken(); // BET
		coinsPlayed = Integer.parseInt(st.nextToken());
		bet = Integer.parseInt(st.nextToken());
	}

	public void sendVersus(String versus) {
		writer.println("VERSUS " + versus);
	}
	
	public void sendWinner(String winner){
		writer.println("WINNER "+winner);
	}
	public void sendWinner(){
		writer.println("WINNER NONE");
	}

	public int getBet() {
		return bet;
	}
	
	public int getcoinsPlayed(){
		return coinsPlayed;
	}
	
	public void CloseAll() throws IOException{
		writer.close();
		reader.close();
		socket.close();
		
	}

}
