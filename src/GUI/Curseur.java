package GUI;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Dimension;
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
	
    Curseur() {
    	setPreferredSize(new Dimension(500,500));
    	Toolkit tk = Toolkit.getDefaultToolkit();
		img = tk.getImage("fantome.gif");
		
    	addMouseMotionListener(new MouseMotionAdapter() {
    		public void mouseMoved (MouseEvent evt) {
    			abscisse = evt.getX();
    			ordonnee = evt.getY(); 
    			try {
					Robot suiveur= new Robot();
					if ((abscisse<120)&&(abscisse>100)&&(ordonnee<70)&&(ordonnee>50)/*position relative dans la fenetre*/){
					System.out.println("on y est");
					suiveur.mouseMove(200, 150 /*position absolue dans l'écran*/);
					}
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    				
    		}
    	});
    }

}
