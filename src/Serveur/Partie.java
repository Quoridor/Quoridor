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
		
		// Noms par défaut
		for (int i = 0 ; i < nbClients ; i++)
			clients[i] = "Joueur " + (i+1);
		
		jeu = new Jeu(nbClients);
	}
	
	public void addClient(Socket client) {
		// Création des connexions
		threads[nb] = new Pipe(nb + 1);
	
		// Thread par client
		new ServeurThread(client, threads[nb]).start();		
		nb++;
		
		// Début de la partie
		if (nb == nbClients)
			this.start();
	}
	
	// Partie
	@Override
	public void run() {
		// Demande des noms
		for (Pipe b : threads)
			b.client.setMessage("3");
				
		// Affichage des joueurs
		System.out.println("Début de partie\n\tJoueurs :");
		for (String s : clients)
			System.out.println("\t\t- " + s);
		
		// Tant que la partie n'est pas finie
		while (true) {
			boolean bon = true;
			String req = null;
			
			// Boucle tant que l'on a pas une action du joueur courant
			while (bon) {
				// Demande au joueur courant de jouer
				threads[courant - 1].client.setMessage("2");
				
				// Récupération de son coup
				req = threads[courant - 1].serveur.tryGetMessage(2000);
				bon = !requete(req, 2, courant); // Renvoit true si c'est la requete voulue
				
				// Traiter d'autres requetes pour ne pas tout bloquer
				for (int i = 1 ; i <= nbClients ; i++)
					if (i != courant)
						requete(threads[i - 1].serveur.tryGetMessage(2000), 0, i);
			}					
						
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
	
	public boolean requete(String req, int cmd, int joueur) {
		if (req == null)
			return false;
		
		// Séparation des valeurs
		String[] args = req.split(" ");
		
		try {
			switch (Integer.parseInt(args[0])) {
			// NOM
			case(3):
				if (args.length > 1) {
					System.out.println("->Le client " + clients[joueur - 1] + " envoie son nom : " + req.substring(2));
					clients[joueur - 1] = req.substring(2);
					return (3 == cmd);
				}
				break;
			// MUR
			case(4):
				
				
			// Quitter
			case(7):
				System.out.println("->Le client " + clients[joueur - 1] + " quitte !");
				System.exit(-1);
			// Chat
			case(8):
				if (req.length() <= 2)
					return false;
				System.out.println("->Le client " + clients[joueur - 1] + " écrit : " + req.substring(2));
				for (Pipe b : threads)
					b.client.setMessage(clients[joueur - 1] + " : " + req.substring(2));
				break;
			// Echo
			case(9):
				threads[joueur - 1].client.setMessage(req.substring(2));
				break;
			// Liste joueurs
			case(10):
				String buf = "10";
				for (String s : clients)
					buf += (" " + s);
				threads[joueur - 1].client.setMessage(buf);
			default:
				System.err.println("->Numéro de requête invalide : " + Integer.parseInt(args[0]));
			}
		} catch (NumberFormatException e) {
			System.err.println("->Requête invalide : " + req);
		}
		return false;
	}
}
