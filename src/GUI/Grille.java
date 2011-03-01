package GUI;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
//import java.


public class Grille extends JPanel {
	
	final int tailleCase = 30; //à modifier
	static final long serialVersionUID = 1;
	
	public Grille() {
		//JPanel grille = new JPanel();
		//grille.setLayout(new BorderLayout, CENTER);

	}
	
	public void paintComponent (Graphics g) {
		this.drawGrille(g);
	}

	private void drawGrille (Graphics g) {
		g.setColor(Color.BLACK);
		for (int i= 1; i < 10; i++) {
			g.drawLine(0, i*tailleCase, 9*tailleCase, i*tailleCase);			
			g.drawLine(i*tailleCase, 0, i*tailleCase, 9*tailleCase);
		}	
	}
	
}
