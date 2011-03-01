package Serveur;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import Jeu.Jeu;

public class Partie extends Thread {
	private BALTimeOut[] threads;
	private int nbClients;
	private String[] clients;
	private int nb = 0;
	private int courant = 1;
	private Jeu jeu;
	
	public Partie(int nbclients) {
		this.nbClients = nbclients;
		clients = new String[nbClients];
		threads = new BALTimeOut[nbclients];
		jeu = new Jeu(nbClients);
	}
	
	public void addClient(Socket client) {
		// Création des connexions
		threads[nb] = new BALTimeOut();
			
		// Thread par client
		new ServeurThread(client, threads[nb], nb).start();
		
		nb++;
		
		// Début de la partie
		if (nb == nbClients)
			this.start();
	}
	
	// Partie
	@Override
	public void run() {
		// Demande des noms
		for (BALTimeOut b : threads)
			b.setMessage("3");
				
		// Affichage des joueurs
		System.out.println("Début de partie\n\tJoueurs :");
		for (String s : clients)
			System.out.println("\t\t- " + s);
		
		
		
		// tant que la partie n'est pas finie
		while (true) {
			boolean bon = false;
			String req = null;
			while (req == null) {
				// Demande au joueur courant de jouer
				threads[courant - 1].out.println("2");
				// Récupération de son coup
				try {
					req = threads[courant - 1].in.readLine();
					requete(req);
				} catch (IOException e) {
					System.err.println("Erreur lors de la récupération du coup du joueur " + clients[courant]);
				}				
			}
			System.out.println("Le joueur " + clients[courant - 1] + " veut jouer : " + req);
			
			// Etude du coup
			switch (req)
			if ()
			
			// Changement de joueur
			courant = courant % nbClients + 1;			
			
			try {
				this.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void requete(String req) {
		String[] args = req.split(" ");
		
		switch (Integer.parseInt(args[0])) {
		// NOM
		case(3):
			if (args.length > 1) {
				String name = args[1];
				//System.out.println("Thread " + thread + " : Le client donne son nom : " + name);
			}
			return;
		// Quitter
		case(7):
			//System.out.println("Thread " + thread + " : Le client quitte");
			System.exit(-1);
			return;
		// Echo
		case(9):
			//threads[thread].out.println("Echo : " + args[0]);
			//threads[thread].notify();
			return;
		default:
			//System.err.println("Numéro de requête invalide : " + cmd);
	}
		
	}
}
