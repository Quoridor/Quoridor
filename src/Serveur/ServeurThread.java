package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServeurThread extends Thread {
	private Socket client;
	private Pipe pipe;

	public ServeurThread(Socket client, Pipe pipe) {
		this.client = client;
		this.pipe = pipe;
	}

	@Override
	public void run() {
		try {
			System.out.println(getName() + " lancé...");
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			// Communication avec le client
			while(true) {
				// Envoi des données
				out.println(this.pipe.in.readLine());
				// Récupération de la commande du client
				this.pipe.out.println(in.readLine());				
			}				
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(getName() + " : client déconnecté");
	}
}
