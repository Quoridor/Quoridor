package GUI;

import java.awt.Toolkit;
import javax.swing.JFrame;
import java.awt.Dimension;


public class FenetreJeu extends JFrame {
	static final long serialVersionUID = 1;
	
	public static void main(String[] arg) {
		Toolkit boiteAOutil = Toolkit.getDefaultToolkit();
		Dimension dim = boiteAOutil.getScreenSize();
		int largeurEcran = dim.width;
		int hauteurEcran = dim.height;
		int largeurFenetre = largeurEcran/2; //à modifier
		int hauteurFenetre = hauteurEcran/2; //à modifier
		
		JFrame fenetreJeu = new JFrame();
		fenetreJeu.setSize(largeurFenetre, hauteurFenetre);
		fenetreJeu.setLocation((largeurEcran - largeurFenetre)/2, (hauteurEcran - hauteurFenetre)/2);
		fenetreJeu.setVisible(true);
		//fenetreJeu.setContentPane(new Grille());

		fenetreJeu.add(new Curseur());
		fenetreJeu.pack();
		fenetreJeu.setDefaultCloseOperation(EXIT_ON_CLOSE);

		//fenetreJeu.add(new Grille());
		//fenetreJeu.pack();

		
	}
}
