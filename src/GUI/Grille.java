package GUI;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;


public class Grille extends JPanel {
	final int tailleCase = 10;


	public void paintComponent (Graphics g) {
		this.drawGrille(g);
}

	private void drawGrille (Graphics g) {
		g.setColor(Color.BLACK);
		for (int i= 1; i < 11; i++) {
			g.drawLine(i*tailleCase, i*tailleCase, (i+1)*tailleCase, (i+1)*tailleCase);			
			g.drawLine(i*tailleCase, i*tailleCase, (i+1)*tailleCase, (i+1)*tailleCase);
		}	
	}
}
