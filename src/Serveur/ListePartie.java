package Serveur;

import java.net.Socket;
import java.util.ArrayList;

public class ListePartie extends Thread {
	private ArrayList<Partie> parties = new ArrayList<Partie>();
	private volatile ArrayList<Pipe> joueurs = new ArrayList<Pipe>();
	
	/**
	 * Ajout d'un joueur qui se connecte pour la première fois
	 * @param client
	 */
	public synchronized void addJoueur(Socket client) {
		// Création de la connexion
		joueurs.add(new Pipe(joueurs.size()));
	
		// Thread par client
		new ServeurThread(client, joueurs.get(joueurs.size() - 1)).start();		
	}
	
	/**
	 * Ajout d'un joueur qui a déjà son thread de connexion
	 * @param joueur
	 */
	public synchronized void addJoueur(Pipe joueur) {
		System.out.println("->Retour d'un joueur sur la liste");
		joueurs.add(joueur);
	}
	
	// Partie
	@Override
	public void run() {
		System.out.println("->Salle d'attente lancée");
		
		while (true) {
			// Traiter les requetes
			for (int i = 0 ; i < joueurs.size() ; i++) {
				try {
					requete(joueurs.get(i).serveur.tryGetMessage(20), i);
				} catch (Exception e) {}
			}
		}			
	}
	
	/**
	 * Fonction qui traite les requetes
	 * @param req
	 * @param joueur
	 * @return
	 */
	public void requete(String req, int joueur) {
		if (req == null)
			return;
		
		// Séparation des valeurs
		String[] args = req.split(" ");
		
		System.out.println(args[0]);
		
		try {
			switch (Integer.parseInt(args[0])) {
			//--------	
			// QUITTER
			case(7):
				joueurs.remove(joueur);
				break;
			// Echo
			case(9):
				if (args.length > 1)
					joueurs.get(joueur).client.setMessage(req.substring(2));
				else
					joueurs.get(joueur).client.setMessage("Echo");
				break;
			// Liste parties
			case(20):
				String buf = "20 ";
				for (Partie p : parties)
					buf += (";" + p.nom + p.nbClients + p.getConnectes());
				joueurs.get(joueur).client.setMessage(buf);
				break;
			// Créer une partie
			case(21):
				System.out.println(req.substring(3).split(";")[0] + " " + req.substring(3).split(";")[1]);
				if ((Integer.parseInt(req.substring(3).split(";")[0])==2 || Integer.parseInt(req.substring(3).split(";")[0])==4)) {
					parties.add(new Partie(Integer.parseInt(req.substring(3).split(";")[0]), req.substring(3).split(";")[1], this));
					// Signaler aux client qu'il y a une nouvelle partie
					rafraichirListe();
				}
				else
					throw new Exception();
				break;
			// Rejoindre une partie
			case(22):
				if (args.length < 2)
					throw new Exception();				
				for (int i = 0 ; i < parties.size() ; i++)
					if (parties.get(i).nom.equals(req.substring(3))) {						
						// Si la partie commence
						if (parties.get(i).addClient(joueurs.get(joueur)))
							parties.remove(i);
						// Dire au joueur qu'il est connecté
						joueurs.get(joueur).client.setMessage("22");
						// Enlever le joueur de la salle d'attente
						joueurs.remove(joueurs.get(joueur));												
						// Update de la liste pour les clients
						rafraichirListe();
						return;
					}
						
				break;
			default:
				System.err.println("->Numéro de requête invalide : " + Integer.parseInt(args[0]));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("->Requête invalide : " + req);
		}
		return;
	}
	
	public void rafraichirListe() {
		String buf = "20 ";
		for (Partie p : parties)
			buf += (";" + p.nom + p.nbClients + p.getConnectes());
		for (Pipe j : joueurs)
			j.client.setMessage(buf);
	}
}
