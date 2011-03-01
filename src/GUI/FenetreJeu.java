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
		this.setPreferredSize(new Dimension(900, 600));
				
		// Alignement horizontal
		Box hbox = Box.createHorizontalBox();
		
		// Chat
		Chat chat = new Chat(reseau);
		
		// Ajout du controleur à reseau
		System.out.println(chat.getControleur());
		reseau.setControleur(chat.getControleur());
		Grille grille = new Grille();
		grille.setPreferredSize(getMaximumSize());
		hbox.add(grille);
		hbox.add(chat);		
		
		this.setContentPane(hbox);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);				
	}
}
