package GUI;

import java.awt.Component;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;



public class Curseur extends JPanel {

      
        private int abscisse;
        private int ordonnee;
        private int tailleCase=40;
        
        // Mode
        //	1 : deplacement
        //	2 : mur horizontal
        //	3 : mur vertical
        private int fonction=1;
       
        
        // Etat
        private boolean actif = false;
        
        Curseur() {
        	
            setPreferredSize(new Dimension(400,400));
            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseMoved (MouseEvent clic) {
                	if ((clic.getX() > (fonction==3 ? tailleCase : 0)) &&
                        	(clic.getX() < (fonction==2 ? 8 : 9)*tailleCase) &&
                        	(clic.getY() > (fonction==2 ? tailleCase : 0)) &&
                        	(clic.getY() < (fonction==3 ? 8 : 9)*tailleCase)) {
                		abscisse = clic.getX();
                		ordonnee = clic.getY();
                    	repaint();   
                	
                    }
                }
            });
       
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent clic) {
            	// Si nous sommes dans la grille
                if ((clic.getX() > 0) &&
                	(clic.getX() < 9*tailleCase) &&
                	(clic.getY() > 0) &&
                	(clic.getY() < 9*tailleCase) &&
                	actif) {
                	
                    int coorX = clic.getX()/tailleCase+1;
                    int coorY = clic.getY()/tailleCase+1;
                	
                	// Envoi
                	switch ()                	


                    System.out.println("x= " + (coorX));
                    System.out.println("y= " + (coorY));

                }                
            }
            
        });
        }
        
        public void ChangeFonction(int parametre) { //à modifier
        	fonction = parametre;
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Damier
            g.setColor(Color.RED);
            g.fillRect(0,0,9*tailleCase,9*tailleCase);
            
            // Grille
            g.setColor(Color.BLACK);
            for (int i= 0; i < 10; i++) {
                g.drawLine(0, i*tailleCase, 9*tailleCase, i*tailleCase);           
                g.drawLine(i*tailleCase, 0, i*tailleCase, 9*tailleCase);
            }
            
            // Affichage du jeu
            //...            
            
            // Curseur si etat actif
            if (actif) {
	            switch(fonction) {
	                case 1:
	                	g.fillOval((abscisse/tailleCase)*tailleCase,(ordonnee/tailleCase)*tailleCase,tailleCase,tailleCase);
	                	break;
	                case 2:
	                	g.fillRect((abscisse/tailleCase)*tailleCase,(ordonnee/tailleCase)*tailleCase-5,80,11);
	                	break;
	                case 3:
	                	g.fillRect((abscisse/tailleCase)*tailleCase-5,(ordonnee/tailleCase)*tailleCase,11,80);
	                	break;
	            }
            }
        }
}