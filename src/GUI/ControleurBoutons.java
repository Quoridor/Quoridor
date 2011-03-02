package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControleurBoutons implements ActionListener {
	private Curseur curseur;
	
	ControleurBoutons(Curseur curseur) {
		this.curseur = curseur;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Déplacement"))
			this.curseur.ChangeFonction(1);
		if (e.getActionCommand().equals("Mur Horizontal"))
			this.curseur.ChangeFonction(2);
		if (e.getActionCommand().equals("Mur Vertical"))
			this.curseur.ChangeFonction(3);
		//if (e.getActionCommand().equals("Règle du jeu"))
			//this.curseur.ChangeFonction(4);
		
	}

}
