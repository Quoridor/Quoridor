package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import Jeu.Jeu;

public class Reseau {
	private BufferedReader in;
	private PrintWriter out;
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
	}
	
	/**
	 * Fonction d'envoi du nom du client au serveur
	 * @param nom	Nom à envoyer
	 * @return		Retourne false si il y a une erreur
	 */
	public boolean envoyerNom(String nom) {
		out.println("NOM " + nom);
		try {
			if (in.readLine().equals("OK"))		
				return true;
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return false;
	}
	
	public void recupererJoueurs() {
		out.println("10");
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
		return 1;
	}
	
	

}
