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
	private int courant = 0;//TODO A changer à 0 lors de la mise en production
	private Jeu jeu;
	private Thread attente;
	
	public Partie(int nbclients) {
		this.nbClients = nbclients;
		clients = new String[nbClients];
		threads = new Pipe[nbclients];
		
		// Noms par défaut
		for (int i = 0 ; i < nbClients ; i++)
			clients[i] = "Emplacement libre " + (i+1);
		
		jeu = new Jeu(nbClients);
	}
	
	public void addClient(Socket client) {
		// Création des connexions
		threads[nb] = new Pipe(nb + 1);
	
		// Thread par client
		new ServeurThread(client, threads[nb]).start();		
		nb++;
		
		
		// Envoi du nombre de clients 2 ou 4
		threads[nb - 1].client.setMessage("12 " + nbClients);
			
		// Premier joueur
		if (nb == 1) {
			// Avant le début de partie
			attente = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						for (int i = 1 ; i <= nb ; i++) {
							requete(threads[i - 1].serveur.tryGetMessage(20), 0, i);
							System.out.println("Méchant Thread !!");
						}
					}
				}
			});
			attente.start();
		}			
		
		// Début de la partie
		if (nb == nbClients) {
			attente.stop();
			this.start();
		}
		
		// Envoi de la liste des joueurs connectés ou emplacements libres
		String buf = "10 ";
		for (String s : clients)
			buf += (";" + s);
		threads[nb - 1].client.setMessage(buf);
		
		// Envoi du numéro dans le jeu du client
		threads[nb - 1].client.setMessage("11 " + nb);
	}
	
	// Partie
	@Override
	public void run() {
		courant = 1;
		
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
				req = threads[courant - 1].serveur.tryGetMessage(20);
				bon = !requete(req, 6, courant); // Renvoit true si c'est la requete voulue
				
				// Traiter d'autres requetes pour ne pas tout bloquer
				for (int i = 1 ; i <= nbClients ; i++)
					if (i != courant)
						requete(threads[i - 1].serveur.tryGetMessage(20), 0, i);
			}					
						
			// Changement de joueur
			System.out.println("->Changement de joueur");
			courant = courant % nbClients + 1;			
		}			
	}
	/**
	 * Fonction qui traite les requetes
	 * @param req
	 * @param cmd
	 * @param joueur
	 * @return
	 */
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
			// MURH
			case(4):
				System.out.println("->Le client " + clients[joueur - 1] + " veut mettre un mur horizontal en (" + Integer.parseInt(args[1]) + "," + Integer.parseInt(args[2]) + ")");
				jeu.mur(joueur, 0, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
				break;
			// MURV
			case(5):
				System.out.println("->Le client " + clients[joueur - 1] + " veut mettre un mur vertical en (" + Integer.parseInt(args[1]) + "," + Integer.parseInt(args[2]) + ")");
				jeu.mur(joueur, 1, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
				break;
				
			//---------	
			// DEPLACER
			case(6):
				System.out.println("->Le client " + clients[joueur - 1] + " veut se déplacer en (" + Integer.parseInt(args[1]) + "," + Integer.parseInt(args[2]) + ")");
				
				// Si ce n'est pas son tour
				if (courant != joueur) {
					System.out.println("->Ce n'est pas le tour de " + clients[joueur - 1] + "!");
					return false;
				}
				
				// Cas où le coup est valide
				if (jeu.deplacer(joueur, Integer.parseInt(args[1]), Integer.parseInt(args[2]))) {
					System.out.println("->Le coup du joueur " + clients[joueur - 1] + " est valide");
					// On le dit à tout le monde
					for (Pipe t : threads)
						if (t != null) //TODO remove lors de la production
							t.client.setMessage("6 " + joueur + " " + args[1] + " " + args[2]);
					// Problème de requete de jeu envoyée
					threads[joueur - 1].client.setMessage("13");
					return (cmd == 6);
				}
				// Sinon
				System.out.println("->Le coup du joueur " + clients[joueur - 1] + " est invalide !");
				return false;
				
			//--------	
			// QUITTER
			case(7):
				System.out.println("->Le client " + clients[joueur - 1] + " quitte !");
				System.exit(-1);
				
			//-----
			// CHAT
			case(8):
				if (req.length() <= 2)
					return false;
				System.out.println("->Le client " + clients[joueur - 1] + " écrit : " + req.substring(2));
				for (int i = 0 ; i < nb ; i++)
					threads[i].client.setMessage("8 " + clients[joueur - 1] + " : " + req.substring(2));
				break;
			// Echo
			case(9):
				if (req.length() > 2)
					threads[joueur - 1].client.setMessage(req.substring(2));
				else
					threads[joueur - 1].client.setMessage("Echo");
				break;
			// Liste joueurs
			case(10):
				String buf = "10 ";
				for (String s : clients)
					buf += (";" + s);
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
