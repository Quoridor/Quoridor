package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import GUI.Joueur;
import GUI.ListeJoueurs;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import Jeu.Jeu;

public class Reseau extends Observable{
	private BufferedReader in;			// Socket en lecture
	private PrintWriter out;			// Socket en ecriture
	private int joueur;					// Numéro du joueur
	private int joueurCourant;
	private String[] joueurs;			// Liste des joueurs
	private String nom;					// Nom du joueur
	private Jeu jeu;					// Instance de jeu
	private ControleurReseau controleur;// Controleur du chat
	private	Curseur curseur;			// Curseur pour lui signaler de jouer et de rafraichir
	private SelectionPartie selectionPartie;
	private	ArrayList<Partie> parties;	// Liste des parties
	private FenetreJeu fenetreJeu;
	private DefaultListModel listeJoueurs;	// Liste des joueurs de la partie
	
	/**
	 * Constructeur
	 * @param host	Adresse du serveur
	 * @param port	Port sur lequel on veut se connecter
	 * @param jeu	Instance Jeu du client
	 */
	public Reseau(String host, int port, String nom) throws Exception {
		this.nom = nom;
		this.parties = new ArrayList<Partie>();
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
		
		// Envoyer son nom
		this.envoyerNom(nom);
		
		// Récupérer la liste des parties
		this.recupererParties();
	}
	
	/**
	 * Fonction qui gère les informations reçues du serveur
	 * @param req Requete reçue
	 */
	private void requete(String req) {
		if (req == null)
			return;
		//System.out.println("##" + req + " | " + req.split(" ").length);
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
				System.out.println("Le joueur " + joueurs[Integer.parseInt(args[1]) - 1] + " ajoute un mur horizontal en (" + Integer.parseInt(args[2]) + "," + Integer.parseInt(args[3]) + ")");
				if (args.length == 4) {
					// Ajout du mur à la représentation du client
					jeu.mur(Integer.parseInt(args[1]), 0, Integer.parseInt(args[2]), Integer.parseInt(args[3]));
					// Rafraichissement de l'affichage
					curseur.repaint();
				}	
				else
					throw new Exception();
				break;
			// MURV
			case(5):
				System.out.println("Le joueur " + joueurs[Integer.parseInt(args[1]) - 1] + " ajoute un mur vertical en (" + Integer.parseInt(args[2]) + "," + Integer.parseInt(args[3]) + ")");
				if (args.length == 4) {
					jeu.mur(Integer.parseInt(args[1]), 1, Integer.parseInt(args[2]), Integer.parseInt(args[3]));
					// Rafraichissement de l'affichage
					curseur.repaint();
				}
				else
					throw new Exception();
				break;
			// DEPLACER
			case(6):
				System.out.println("Le joueur " + joueurs[Integer.parseInt(args[1]) - 1] + " se déplace en (" + Integer.parseInt(args[2]) + "," + Integer.parseInt(args[3]) + ")");
				if (args.length == 4) {
					// Déplacement du pion dans la représentation du client
					jeu.deplacer(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
					//jeu.getListeJoueurs().get(Integer.parseInt(args[1]) - 1).setCoord(Integer.parseInt(args[2]), Integer.parseInt(args[3]), jeu);
					// Rafraichissement de l'affichage
					curseur.repaint();
				}
				else
					throw new Exception();
				break;
			// Quitter
			case(7):
				System.out.println("->La partie se termine de manière impromptue !");
				curseur.finBrutale();
			// Chat
			case(8):
				controleur.ecrire(req.substring(2));
				break;
			// Liste joueurs
			case(10):				
				joueurs = req.substring(4).split(";");
				listeJoueurs.clear();
				System.out.println("##########Joueurs");
				for (int i = 0 ; i < joueurs.length ; i++) {
					this.listeJoueurs.addElement(new Joueur(i + 1, (joueurCourant==i+1 ? "->" : "") + joueurs[i]));
					System.out.println(joueurs[i] + " " + (i+1));
				}
				;				
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
			// Victoire
			case(14):
				if (args.length < 2)
					return;
				if (joueur == Integer.parseInt(args[1])) {
					System.out.println("->Victoire");
					fenetreJeu.popup("Vous avez gagné !", "Victoire");
				}
				else {
					System.out.println("->Défaite, le joueur " + joueurs[Integer.parseInt(args[1]) - 1] + " gagne...");
					fenetreJeu.popup("Vous avez perdu ! Le joueur " + joueurs[Integer.parseInt(args[1]) - 1] + " gagne", "Défaite");					//controleur.ecrire("## Vous avez perdu ! Le joueur " + joueurs[Integer.parseInt(args[1]) - 1] + " gagne");
				}
				break;
			// Récupérer le joueur courant
			case(15):
				if (args.length != 2)
					return;
				joueurCourant = Integer.parseInt(args[1]);
				System.out.println("->Le joueur courant est " + joueurs[Integer.parseInt(args[1]) - 1]);
				//((Joueur) this.listeJoueurs.get(Integer.parseInt(args[1]) - 1)).nom = "->" + joueurs[Integer.parseInt(args[1]) - 1];
				//this.fenetreJeu.repaint();
				break;
			// Récupérer la liste des parties
			case(20):
				parties.clear();
				// Si il n'y en a pas
				if (args.length == 1)
					return;
				else {
					for (String s : req.substring(4).split(";"))
						//System.out.println(args[1] + " : " + s);
						parties.add(new Partie(s.substring(0, s.length() - 2), Integer.parseInt(s.substring(s.length()-2, s.length()-1)), Integer.parseInt(s.substring(s.length()-1))));
				}
				System.out.println("->Rafraichissement de la liste des parties");
				// Updater l'affichage
				selectionPartie.rafraichir();
				break;
			// Rejoindre une partie
			case(22):
				envoyerNom();
				selectionPartie.setVisible(false);
				new FenetreJeu(this, selectionPartie);
				break;
			// Retourner à la liste de sélection de partie
			case(23):
				System.out.println("->Retour à la liste des parties");
				fenetreJeu.retourListe();
				recupererParties();
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
	
	public void envoyerNom() {
		out.println("3 " + nom);
	}
	
	public void recupererParties() {
		out.println("20");		
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
	
	public ArrayList<Partie> getParties() {
		return parties;
	}
	
	/** Renvoit le numéro du joueur
	 * @return		 Numéro du joueur
	 */
	public int getJoueur() {
		return joueur;
	}
	
	public int getJoueurCourant() {
		return joueurCourant;
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
	 * Demande de création de partie
	 */
	public void creerPartie(String nom, int joueurs) {
		if (nom != null)
			out.println("21 " + joueurs + ";" + nom);
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
	
	/**
	 * Fonction pour rejoindre la partie
	 */
	public void rejoindre(String nom) {
		out.println("22 " + nom);
	}
	
	/**
	 * Fonction permettant de passer son tour
	 */
	public void passerTour() {
		out.println("15");
		curseur.setJouer(false);
	}
	
	public void setControleur(ControleurReseau controleur) {
		this.controleur = controleur;
		
		// Un fois le controleur mis on le signale
		controleur.ecrire("->Connexion réussie");
	}
	
	public void setCurseur(Curseur curseur) {
		this.curseur = curseur;
	}
	
	public void setPartie(SelectionPartie partie) {
		this.selectionPartie = partie;
	}
	
	public Jeu getJeu() {
		return this.jeu;
	}
	
	public void setFenetreJeu(FenetreJeu fenetreJeu) {
		this.fenetreJeu = fenetreJeu;
	}
	
	public void setListeJoueur(DefaultListModel listeJoueurs) {
		this.listeJoueurs = listeJoueurs;
	}	
}

class Partie {
	public String nom;
	public int nbJoueurs;
	public int nbConnectes;
	
	public Partie(String nom, int nbJoueurs, int nbConnectes) {
		this.nom = nom;
		this.nbJoueurs = nbJoueurs;
		this.nbConnectes = nbConnectes;
	}
}
