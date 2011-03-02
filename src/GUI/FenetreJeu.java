package GUI;

import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JFrame;

import Jeu.Jeu;

import java.awt.Dimension;


public class FenetreJeu extends JFrame {
	static final long serialVersionUID = 1;
	
	public FenetreJeu(Reseau reseau) {
		super();
		
		// Fenêtre de jeu
		this.setPreferredSize(new Dimension(901, 600));
		
		Box vbox = Box.createVerticalBox();
		
		Boutons boutons = new Boutons();
		vbox.add(new Boutons());
		
		// Alignement horizontal
		Box hbox = Box.createHorizontalBox();
		vbox.add(hbox);
		
		// Chat
		Chat chat = new Chat(reseau);
		
		// Ajout du controleur à reseau
		reseau.setControleur(chat.getControleur());
		
		Curseur grille = new Curseur();
		grille.setPreferredSize(getMaximumSize());
		hbox.add(grille);
		hbox.add(chat);		
		
		this.setContentPane(vbox);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);				
	}
}
