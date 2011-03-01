package Serveur;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import Jeu.Jeu;

public class Partie extends Thread {
	private Pipe[] threads;
	private int nbClients;
	private String[] clients;
	private int nb = 0;
	private int courant = 1;
	private Jeu jeu;
	
	public Partie(int nbclients) {
		this.nbClients = nbclients;
		clients = new String[nbClients];
		threads = new Pipe[nbclients];
		jeu = new Jeu(nbClients);
	}
	
	public void addClient(Socket client) {
		// Création des connexions
		Pipe pipe1 = new Pipe(); // Thread -> Serveur
		Pipe pipe2 = new Pipe(); // Serveur -> Thread
		threads[nb] = new Pipe(pipe1.out, pipe2.in);
			
		// Thread par client
		new ServeurThread(client, new Pipe(pipe2.out, pipe1.in)).start();
		
		
		// Récupération du nom
		while (clients[nb] == null) {
			try {
				threads[nb].out.println("3");
				clients[nb] = threads[nb].in.readLine();
				System.out.println("Thread " + (nb) + " envoie son nom " + clients[nb]);
			} catch (IOException e) {
				System.err.println("Erreur lors de la connexion du client !");
				return;
			}
		}
		// On dit aux autres joueurs qu'il y a un nouveau client
		//for (int i = 0 ; i < nb ; i++)
		//	threads[i].out.println("3" + clients[i]);
		
		nb++;
		
		// Début de la partie
		if (nb == nbClients)
			this.start();
	}
	
	// Partie
	@Override
	public void run() {
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
