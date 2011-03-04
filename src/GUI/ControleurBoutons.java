package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControleurBoutons implements ActionListener {
	private Curseur curseur;
	private FenetreJeu fenetre;
	private Reseau reseau;
	
	ControleurBoutons(Curseur curseur, FenetreJeu fenetre, Reseau reseau) {
		this.curseur = curseur;
		this.fenetre = fenetre;
		this.reseau = reseau;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Déplacement"))
			this.curseur.ChangeFonction(1);
		if (e.getActionCommand().equals("Mur Horizontal"))
			this.curseur.ChangeFonction(2);
		if (e.getActionCommand().equals("Mur Vertical"))
			this.curseur.ChangeFonction(3);
		if (e.getActionCommand().equals("Accueil"))
			this.reseau.signalerFin();
	}
}
