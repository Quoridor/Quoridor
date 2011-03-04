package GUI;

import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Jeu.Jeu;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class FenetreJeu extends JFrame {
	static final long serialVersionUID = 1;
	private SelectionPartie parties;
	
	public FenetreJeu(final Reseau reseau, SelectionPartie parties) {
		super();
		this.parties = parties;
			
		// Fenêtre de jeu
		this.setPreferredSize(new Dimension(950, 700));
		
		Box vbox = Box.createVerticalBox();
			
		// Alignement horizontal
		Box hbox = Box.createHorizontalBox();
		
		// Chat
		Chat chat = new Chat(reseau);
		
		// Liste joueur		
		Box lbox = Box.createVerticalBox();
		lbox.setVisible(true);
		ListeJoueurs ljoueurs = new ListeJoueurs();
		ljoueurs.setVisible(true);
		ljoueurs.setPreferredSize(new Dimension(50,50));
		Joueur[] list = new Joueur[4];
		list[0]=new Joueur(1,"paulo");
		list[1]=new Joueur(2,"henri");
		list[2]=new Joueur(3,"dominique");
		list[3]=new Joueur(4,"heinrich");
		ljoueurs.setListData(list);
		hbox.add(ljoueurs); 
		
		// Ajout du controleur à reseau
		reseau.setControleur(chat.getControleur());
		
		// Ajout de la fenêtre
		reseau.setFenetreJeu(this);
		
		// Ajout de la liste des joueurs
		reseau.setListeJoueur(ljoueurs);
		
		Curseur grille = new Curseur(reseau);
		grille.setPreferredSize(getMaximumSize());
		
		ControleurBoutons controleurBoutons = new ControleurBoutons(grille, this, reseau);
				
		Boutons boutons = new Boutons(controleurBoutons);
		vbox.add(boutons);
		vbox.add(hbox);
		
		hbox.add(grille);
		vbox.add(chat);		
		
		// Connaitre lorsque l'on ferme la fenêtre
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				// Dire au serveur que l'on quitte la partie
				reseau.signalerFin();
				//retourListe();
			}
			
		});
		
		this.setContentPane(vbox);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);				
	}
	
	// Retour à la liste des parties
	public void retourListe() {
		// Fermeture de la fenêtre
		this.dispose();
		
		// Affichage de la liste
		parties.setVisible(true);
		
	}
	
	// Afficher un popup
	public void popup(String texte, String titre) {
		JOptionPane.showMessageDialog(null, texte, titre, JOptionPane.INFORMATION_MESSAGE);
	}
}
