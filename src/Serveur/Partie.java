package Serveur;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Partie extends Thread {
	private Pipe[] threads = new Pipe[4];
	private int nbClients;
	private String[] clients;
	private int nb = 0;
	
	public Partie(int nbclients) {
		this.nbClients = nbclients;		
	}
	
	public void addClient(Socket client) {
		// Création des connexions
		Pipe pipe1 = new Pipe(); // Thread -> Serveur
		Pipe pipe2 = new Pipe(); // Serveur -> Thread
		threads[nb] = new Pipe(pipe1.out, pipe2.in);
			
		// Thread par client
		new ServeurThread(client, new Pipe(pipe2.out, pipe1.in)).start();
		nb++;
		
		// Récupération du nom
		System.out.println("Thread " + (nb - 1) + "envoie son nom" + threads[nb-1].in.readLine());
		
		// Début de la partie
		if (this.threads.length == nbClients)
			this.start();
	}
	
	// Partie
	@Override
	public void run() {
		
	}
	

}
