package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

import Jeu.Jeu;

public class Reseau extends Observable{
	private BufferedReader in;			// Socket en lecture
	private PrintWriter out;			// Socket en ecriture
	private int joueur;					// Numéro du joueur
	private String[] joueurs;			// Liste des joueurs
	private String nom;					// Nom du joueur
	private Jeu jeu;					// Instance de jeu
	private ControleurReseau controleur;// Controleur du chat
	private	Curseur curseur;			// Curseur pour lui signaler de jouer et de rafraichir
	
	/**
	 * Constructeur
	 * @param host	Adresse du serveur
	 * @param port	Port sur lequel on veut se connecter
	 * @param jeu	Instance Jeu du client
	 */
	public Reseau(String host, int port, String nom) throws Exception {
		this.nom = nom;
		Socket connexion = null;
		try {
			connexion = new Socket(host, port);
		} catch (UnknownHostException e) {
			System.err.println("Machine inconnue ou port occupé : " + host + ":" + port);
			throw e;
		} catch (IOException e) {
			System.err.println("Connexion impossible au serveur");
			throw e;
		}
		try {
			in = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
			out = new PrintWriter(connexion.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception();
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
		lecture.start();
		
		this.envoyerNom(nom);
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
			// Jouer
			case(2):
				System.out.println("A vous de jouer !");
				curseur.setJouer(true);
				break;
			
			// Envoyer son nom
			case(3):
				System.out.println("->Le serveur demande mon nom");
				envoyerNom(nom);				
				break;
			// MURH
			case(4):
				System.out.println("Le joueur " + joueurs[Integer.parseInt(args[1])] + " ajoute un mur horizontal en (" + Integer.parseInt(args[2]) + "," + Integer.parseInt(args[3]) + ")");
				if (args.length == 4) {
					// Ajout du mur à la représentation du client
					jeu.poseMur(Integer.parseInt(args[1]), 0, Integer.parseInt(args[2]), Integer.parseInt(args[3]));
					// Rafraichissement de l'affichage
					curseur.repaint();
				}	
				else
					throw new Exception();
				break;
			// MURV
			case(5):
				System.out.println("Le joueur " + joueurs[Integer.parseInt(args[1])] + " ajoute un mur vertical en (" + Integer.parseInt(args[2]) + "," + Integer.parseInt(args[3]) + ")");
				if (args.length == 4)
					jeu.mur(Integer.parseInt(args[1]), 1, Integer.parseInt(args[2]), Integer.parseInt(args[3]));
				else
					throw new Exception();
				break;
			// DEPLACER
			case(6):
				System.out.println("Le joueur " + joueurs[Integer.parseInt(args[1])] + " se déplace en (" + Integer.parseInt(args[2]) + "," + Integer.parseInt(args[3]) + ")");
				if (args.length == 4) {
					// Déplacement du pion dans la représentation du client
					jeu.getListeJoueurs().get(Integer.parseInt(args[1]) - 1).setCoord(Integer.parseInt(args[2]), Integer.parseInt(args[3]), jeu);
					// Rafraichissement de l'affichage
					curseur.repaint();
				}
				else
					throw new Exception();
				break;
			// Quitter
			case(7):
				System.out.println("->Le serveur quitte !");
				System.exit(-1);
			// Chat
			case(8):
				controleur.ecrire(req.substring(2));
				break;
			// Liste joueurs
			case(10):				
				joueurs = req.substring(2).split(";");
				break;
			// Numéro dans le jeu
			case(11):
				if (args.length == 2) {
					joueur = Integer.parseInt(args[1]);
					System.out.println("->Je suis le joueur N°" + joueur);
				}
				break;
			// Nombre de joueurs 2 ou 4
			case(12):
				System.out.println("->Partie a " + Integer.parseInt(args[1]) + " joueurs");
				this.jeu = new Jeu(Integer.parseInt(args[1]));
				break;	
			// Fin d'etat actif
			case(13):
				System.out.println("->Ce n'est plus à moi de jouer  !");
				curseur.setJouer(false);
				break;	
			
			default:
				System.err.println("->Numéro de requête invalide : " + Integer.parseInt(args[0]));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("->Requête invalide : " + req);
		}
		
	}
	
	/**
	 * Fonction d'envoi du nom du client au serveur
	 * @param nom	Nom à envoyer
	 * @return		Retourne false si il y a une erreur
	 */
	public void envoyerNom(String nom) {
		out.println("3 " + nom);
	}
	
	/**
	 * Envoie la requête pour récupérer la liste des joueurs
	 */
	public void recupererJoueurs() {
		out.println("10");
	}
	
	/**
	 * Envoie la requête pour récupérer son numéro sur le serveur
	 */
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
	public void deplacer(int x, int y) {
		out.println("6 " + x + " " + y);
	}
	
	/**
	 * Fonction d'ajout de mur
	 * @param sens   Sens du mur : vertical=true, horizontal=false
	 * @param x		 Abscisse du coin haut gauche
	 * @param y		 Ordonnée du coin haut gauche
	 */
	public void mur(boolean sens, int x, int y) {
		out.println((sens ? "5" : "4") + " " + x + " " + y);
	}
	
	/** Renvoit le numéro du joueur
	 * @return		 Numéro du joueur
	 */
	public int getJoueur() {
		return joueur;
	}
	
	public void finalize()
    {
         signalerFin();   
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
	
	public void chat(String texte) {
		out.println("8 " + texte);
	}
	
	public void setControleur(ControleurReseau controleur) {
		this.controleur = controleur;
		
		// Un fois le controleur mis on le signale
		controleur.ecrire("->Connexion réussie");
		out.println("8 Je viens de me connecter");
	}
	
	public void setCurseur(Curseur curseur) {
		this.curseur = curseur;
	}
	
	public Jeu getJeu() {
		return this.jeu;
	}
}
