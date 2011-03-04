package Serveur;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import Jeu.Jeu;

public class Partie extends Thread {
private ArrayList<Pipe> threads = new ArrayList<Pipe>();
	public int nbClients;
	private String[] clients;
	private int courant = 0;
	private Jeu jeu;
	private Thread attente;
	public String nom;
	private ListePartie listePartie;
	
	public Partie(int nbclients, String nom, ListePartie listePartie) {
		this.nom = nom;
		this.nbClients = nbclients;
		this.listePartie = listePartie;
		clients = new String[nbClients];
				
		// Noms par défaut
		for (int i = 0 ; i < nbClients ; i++)
			clients[i] = "Emplacement libre " + (i+1);
		
		jeu = new Jeu(nbClients);
	}
	
	public void addClient(Pipe pipe) {
		// Création des connexions
		threads.add(pipe);
	
		// Envoi du nombre de clients 2 ou 4
		threads.get(threads.size() - 1).client.setMessage("12 " + nbClients);
			
		// Premier joueur
		if (threads.size() == 1) {
			// Avant le début de partie
			attente = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						for (int i = 1 ; i <= threads.size() ; i++) {
							requete(threads.get(i - 1).serveur.tryGetMessage(20), 0, i);
						}
					}
				}
			});
			attente.start();
		}			
		
		// Envoi de la liste des joueurs connectés ou emplacements libres
		String buf = "10 ";
		for (String s : clients)
			buf += (";" + s);
		threads.get(threads.size() - 1).client.setMessage(buf);
		
		// Envoi du numéro dans le jeu du client
		threads.get(threads.size() - 1).client.setMessage("11 " + threads.size());
		
		// Envoi du message de connexion aux autres joueurs
		for (int i = 1 ; i <= threads.size() ; i++)
			if (i != threads.size() - 1) // Sauf à lui-même
				threads.get(i - 1).client.setMessage("8 " + clients[i - 1] + " vient de se connecter");
		
		// Début de la partie
		if (threads.size() == nbClients) {
			attente.stop();
			this.start();
		}
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
				threads.get(courant - 1).client.setMessage("2");
				
				// Récupération de son coup
				req = threads.get(courant - 1).serveur.tryGetMessage(20);
				bon = !requete(req, 6, courant); // Renvoit true si c'est la requete voulue
				
				// Traiter d'autres requetes pour ne pas tout bloquer
				for (int i = 1 ; i <= nbClients ; i++)
					if (i != courant)
						requete(threads.get(i - 1).serveur.tryGetMessage(20), 0, i);
			}					
			
			// On regarde si la partie est finie
			if (jeu.victoire() != 0) {
				System.out.println("->Le joueur " + clients[courant - 1] + " gagne !");
				// On signale la victoire aux joueurs
				for (int i = 1 ; i <= nbClients ; i++)
					if (i != courant)
						threads.get(i - 1).client.setMessage("14 " + courant);
				//TODO Gestion des scores
				this.stop();
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
				// Si ce n'est pas son tour
				if (courant != joueur) {
					System.out.println("->Ce n'est pas le tour de " + clients[joueur - 1] + "!");
					return false;
				}
				
				// Cas où le coup est valide
				if (jeu.mur(joueur, 0, Integer.parseInt(args[1]), Integer.parseInt(args[2]))) {
					System.out.println("->Le coup du joueur " + clients[joueur - 1] + " est valide");
					// On le dit à tout le monde
					for (Pipe t : threads)
						if (t != null) //TODO remove lors de la production
							t.client.setMessage("4 " + joueur + " " + args[1] + " " + args[2]);
					// On dit au client que ce n'est plus son tour
					threads.get(joueur - 1).client.setMessage("13");
					return true;
				}
				// Sinon
				System.out.println("->Le coup du joueur " + clients[joueur - 1] + " est invalide !");
				return false;
			
			
			
			// MURV
			case(5):
				System.out.println("->Le client " + clients[joueur - 1] + " veut mettre un mur vertical en (" + Integer.parseInt(args[1]) + "," + Integer.parseInt(args[2]) + ")");
				// Si ce n'est pas son tour
				if (courant != joueur) {
					System.out.println("->Ce n'est pas le tour de " + clients[joueur - 1] + "!");
					return false;
				}
				
				// Cas où le coup est valide
				if (jeu.mur(joueur, 1, Integer.parseInt(args[1]), Integer.parseInt(args[2]))) {
					System.out.println("->Le coup du joueur " + clients[joueur - 1] + " est valide");
					// On le dit à tout le monde
					for (Pipe t : threads)
						if (t != null) //TODO remove lors de la production
							t.client.setMessage("5 " + joueur + " " + args[1] + " " + args[2]);
					// On dit au client que ce n'est plus son tour
					threads.get(joueur - 1).client.setMessage("13");
					return true;
				}
				// Sinon
				System.out.println("->Le coup du joueur " + clients[joueur - 1] + " est invalide !");
				return false;			
				
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
					threads.get(joueur - 1).client.setMessage("13");
					return true;
				}
				// Sinon
				System.out.println("->Le coup du joueur " + clients[joueur - 1] + " est invalide !");
				return false;
				
			//--------	
			// QUITTER
			case(7):
				System.out.println("->Le client " + clients[joueur - 1] + " quitte !");
				
				// Si la partie n'est pas encore commencée
				if (threads.size() < nbClients) {
					// On place le joueur dans la salle d'attente
					listePartie.addJoueur(threads.get(joueur - 1));
					
					// On le dit au client
					threads.get(joueur - 1).client.setMessage("23");
					
					// On le dit aux autres
					for (int i = 1 ; i <= threads.size() ; i++)
						if (i != joueur)
							threads.get(i - 1).client.setMessage("23");
					
					// On enlève le joueur de la liste
					threads.remove(joueur - 1);
				}					
				else {
					// On signale la fin aux joueurs
					for (int i = 1 ; i <= nbClients ; i++)
						if (i != joueur)
							threads.get(i - 1).client.setMessage("23");
					this.stop();
				}
			//-----
			// CHAT
			case(8):
				if (req.length() <= 2)
					return false;
				System.out.println("->Le client " + clients[joueur - 1] + " écrit : " + req.substring(2));
				for (Pipe p : threads)
					p.client.setMessage("8 " + clients[joueur - 1] + " : " + req.substring(2));
				break;
			// Echo
			case(9):
				if (args.length > 1)
					threads.get(joueur - 1).client.setMessage(req.substring(2));
				else
					threads.get(joueur - 1).client.setMessage("Echo");
				break;
			// Liste joueurs
			case(10):
				String buf = "10 ";
				for (String s : clients)
					buf += (";" + s);
				threads.get(joueur - 1).client.setMessage(buf);
			// Passer son tour
			case(15):
				// Si c'est le joueur courant
				if (courant == joueur) {
					courant = courant % nbClients + 1;
					System.out.println("->Le joueur " + clients[joueur - 1] + " passe son tour");
				}
			default:
				System.err.println("->Numéro de requête invalide : " + Integer.parseInt(args[0]));
			}
		} catch (Exception e) {
			System.err.println("->Requête invalide : " + req);
		}
		return false;
	}

	public int getConnectes() {		
		return this.threads.size();
	}
}
