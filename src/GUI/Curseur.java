package GUI;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;


public class Curseur extends JPanel{
	
	int abscisse;
	int ordonnee;
	Image img;
	int tailleCase=40;
	
    Curseur() {
    	setPreferredSize(new Dimension(400,400));
    	Toolkit tk = Toolkit.getDefaultToolkit();
		img = tk.getImage("images/pion.gif");
		
    	addMouseMotionListener(new MouseMotionAdapter() {
    		public void mouseMoved (MouseEvent evt) {
    			abscisse = evt.getX();
    			ordonnee = evt.getY(); 
    			repaint();
    			
    				
    		}
    	});
    }

    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(0, 0, 32, 32 /*dans la fenetre*/);
		g.setColor(Color.WHITE);
		g.fillRect(tailleCase,tailleCase,9*tailleCase,9*tailleCase);
		g.setColor(Color.BLACK);
		for (int i= 1; i < 11; i++) {
			g.drawLine(tailleCase, i*tailleCase, 10*tailleCase, i*tailleCase);			
			g.drawLine(i*tailleCase, tailleCase, i*tailleCase, 10*tailleCase);
		}
		for (int k=0; k<9; k++){
			for (int j=0; j<9; j++){
				if ((abscisse<((k+2)*tailleCase))&&(abscisse>((k+1)*tailleCase))&&(ordonnee<((j+2)*tailleCase))&&(ordonnee>((j+1)*tailleCase))/*position relative dans la fenetre*/){
					g.drawImage(img,(k+1)*tailleCase,(j+1)*tailleCase,this);
				}
			}
		}
		}
}