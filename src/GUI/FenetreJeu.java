package GUI;

//import java.awt.Toolkit;
import javax.swing.JFrame;
import java.awt.Dimension;

public class FenetreJeu extends JFrame {
	static final long serialVersionUID = 1;
	
	public FenetreJeu() {
		JFrame fenetre = new JFrame();
		fenetre.setPreferredSize(new Dimension(1000,1000)); //à modifier plus tard
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
	}
}
