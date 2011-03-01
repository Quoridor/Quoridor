package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

import Jeu.Jeu;
import Serveur.Pipe;

public class Reseau extends Observable{
	private BufferedReader in;	// Socket en lecture
	private PrintWriter out;	// Socket en ecriture
	private int joueur;			// Numéro du joueur
	private String[] joueurs;	// Liste des joueurs
	private String nom;			// Nom du joueur
	
	/**
	 * Constructeur
	 * @param host	Adresse du serveur
	 * @param port	Port sur lequel on veut se connecter
	 * @param jeu	Instance Jeu du client
	 */
	public Reseau(String host, int port, Jeu jeu) {
		Socket connexion = null;
		try {
			connexion = new Socket(host, port);
		} catch (UnknownHostException e) {
			System.err.println("Machine inconnue ou port occupé : " + host + ":" + port);
			System.exit(-1);
		} catch (IOException e) {
			System.err.println("Connexion impossible au serveur");
			System.exit(-1);
		}
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
			PrintWriter out = new PrintWriter(connexion.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Thread de lecture
		Thread lecture = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true)
					try {
						if (in.ready())
							requete(in.readLine());
					} catch (IOException e) {
						e.printStackTrace();
					}								
			}
		});
	}
	/**
	 * Fonction qui gère les informations reçues du serveur
	 * @param req Requete reçue
	 */
	private void requete(String req) {
		if (req == null)
			return;
		
		// Séparation des valeurs
		String[] args = req.split(" ");
		
		try {
			switch (Integer.parseInt(args[0])) {
			// Envoyer son nom
			case(3):
				System.out.println("->Le serveur demande mon nom");
				envoyerNom(nom);				
				break;
			// MUR
			case(4):
				break;
				
			// Quitter
			case(7):
				System.out.println("->Le serveur quitte !");
				System.exit(-1);
			// Chat
			case(8):
				System.out.println(req.substring(2));				
				break;
			// Liste joueurs
			case(10):
				joueurs = req.substring(2).split(";");
				break;
			default:
				System.err.println("->Numéro de requête invalide : " + Integer.parseInt(args[0]));
			}
		} catch (NumberFormatException e) {
			System.err.println("->Requête invalide : " + req);
		}
		
	}
	
	/**
	 * Fonction d'envoi du nom du client au serveur
	 * @param nom	Nom à envoyer
	 * @return		Retourne false si il y a une erreur
	 */
	public boolean envoyerNom(String nom) {
		out.println("3 " + nom);
	}
	
	public void recupererJoueurs() {
		out.println("10");
	}
	
	public void recupererJoueur() {
		out.println("11");
	}
	
	/**
	 * Fonction qui dit au serveur que l'on se déconnecte
	 * @return		Retourne false si il y a une erreur
	 */
	public boolean quitter() {
		return true;
	}
	
	/**
	 * Fonction de déplacement du joueur
	 * @param x		 Abscisse où le déplacer
	 * @param y		 Ordonnée où le déplacer
	 * @return		 Renvoie true si l'action est réalisée et réalisable false sinon
	 */
	public boolean deplacer(int x, int y) {
		return true;
	}
	
	/**
	 * Fonction d'ajout de mur
	 * @param sens   Sens du mur : vertical=true, horizontal=false
	 * @param x		 Abscisse du coin haut gauche
	 * @param y		 Ordonnée du coin haut gauche
	 * @return		 Renvoie true si l'action est réalisée et réalisable false sinon
	 */
	public boolean mur(boolean sens, int x, int y) {
		
		return true;
	}
	
	/**
	 * Test si la partie est finie
	 * @return 		 Renvoie le numéro du joueur gagnant ou 0 si la partie est en cours
	 */
	public int victoire() {
		
		return 0;
	}
	
	/** Renvoit le numéro du joueur
	 * @return		 Numéro du joueur
	 */
	public int getJoueur() {
		return joueur;
	}
	
	/**
	 * Renvoit la liste des joueurs
	 * @return
	 */
	public String[] getJoueurs() {
		return joueurs;
	}
	
	/**
	 * Signale au serveur que l'on quitte
	 */
	public void signalerFin() {
		out.println("7");
	}
}
