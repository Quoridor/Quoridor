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
			
		// Alignement horizontal
		Box hbox = Box.createHorizontalBox();
		
		// Chat
		Chat chat = new Chat(reseau);
		
		// Ajout du controleur à reseau
		reseau.setControleur(chat.getControleur());
		
		Curseur grille = new Curseur(reseau);
		grille.setPreferredSize(getMaximumSize());
		
		ControleurBoutons controleurBoutons = new ControleurBoutons(grille);
				
		Boutons boutons = new Boutons(controleurBoutons);
		vbox.add(boutons);
		vbox.add(hbox);
		
		hbox.add(grille);
		hbox.add(chat);		
		
		this.setContentPane(vbox);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);				
	}
}
