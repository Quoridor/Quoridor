package GUI;

import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JFrame;

import Jeu.Jeu;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class FenetreJeu extends JFrame {
	static final long serialVersionUID = 1;
	
	public FenetreJeu(final Reseau reseau) {
		super();
		
		// Fenêtre de jeu
		this.setPreferredSize(new Dimension(901, 600));
		
		Box vbox = Box.createVerticalBox();
			
		// Alignement horizontal
		Box hbox = Box.createHorizontalBox();
		
		// Chat
		Chat chat = new Chat(reseau);
		
		// Ajout du controleur à reseau
		reseau.setControleur(chat.getControleur());
		
		// Ajout de la fenêtre
		reseau.setFenetreJeu(this);
		
		Curseur grille = new Curseur(reseau);
		grille.setPreferredSize(getMaximumSize());
		
		ControleurBoutons controleurBoutons = new ControleurBoutons(grille);
				
		Boutons boutons = new Boutons(controleurBoutons);
		vbox.add(boutons);
		vbox.add(hbox);
		
		hbox.add(grille);
		hbox.add(chat);		
		
		// Connaitre lorsque l'on ferme la fenêtre
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				// Dire au serveur que l'on quitte la partie
				reseau.quitter();
				retourListe();
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
	}
}
