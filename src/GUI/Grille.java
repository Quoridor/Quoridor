package GUI;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
//import java.awt.BorderLayout;


public class Grille extends JPanel {
	
	final int tailleCase = 40; //à modifier
	static final long serialVersionUID = 1;
	
	public Grille() {
		JPanel grille = new JPanel();
		grille.setBackground(Color.WHITE);
		//setLayout(new BorderLayout(0,0));
		grille.setLocation(50,50);
	}
	
	public void paintComponent (Graphics g) {
		this.drawGrille(g);
	}

	private void drawGrille (Graphics g) {
		g.setColor(Color.BLACK);
		for (int i= 1; i < 11; i++) {
			g.drawLine(tailleCase, i*tailleCase, 10*tailleCase, i*tailleCase);			
			g.drawLine(i*tailleCase, tailleCase, i*tailleCase, 10*tailleCase);
		}
	}
	
}
