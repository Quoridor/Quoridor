package GUI;

import java.awt.Component;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import Jeu.*;


public class Curseur extends JPanel {

  
    private int abscisse;
    private int ordonnee;
    private int tailleCase=40;
    
    // Mode
    //	1 : deplacement
    //	2 : mur horizontal
    //	3 : mur vertical
    private int fonction=1;
    private Cursor curseurVide;
           
    // Etat
    private boolean actif = false;
    private boolean afficherCurseur;
    
    // Reseau
    private Reseau reseau;
    
    Curseur(final Reseau reseau) {
    	// Curseur->Reseau
    	this.reseau = reseau;
    	
    	// Reseau->Curseur
    	this.reseau.setCurseur(this);
    	
    	// Envoi du nom
    	this.reseau.envoyerNom();
    	
    	// Récupération des noms
    	this.reseau.recupererJoueurs();
    	
    	Image img = Toolkit.getDefaultToolkit().createImage("");
    	curseurVide= Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0,0), "position");
    	 	
        setPreferredSize(new Dimension(9*tailleCase, 9*tailleCase));
        this.setMinimumSize(new Dimension(9*tailleCase, 9*tailleCase));
        this.setMaximumSize(new Dimension(9*tailleCase, 9*tailleCase));
        
        // Gestion du curseur
        addMouseMotionListener(
        new MouseMotionAdapter() {
        	@Override
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
   
        // Gestion du coup à jouer via le clic
	    addMouseListener(
	    new MouseAdapter() {
	        public void mouseClicked(MouseEvent clic) {
	        	// Si nous sommes dans la grille
	            if ((clic.getX() > 0) &&
	            	(clic.getX() < 9*tailleCase) &&
	            	(clic.getY() > 0) &&
	            	(clic.getY() < 9*tailleCase) &&
	            	actif) {
	            	
	            	//Effacement du curseur
	            	
	            	
	            	
	                int coorX = clic.getX()/tailleCase+1;
	                int coorY = clic.getY()/tailleCase+1;
	            	
	            	// Envoi
	            	switch (fonction) {
	            	case(1):
	            		reseau.deplacer(coorX, coorY);
	            		break;
	            	case(2):
	            		if (coorX >= 9)
	            			return;
	            		reseau.mur(false, coorX, coorY);
	            		break;
	            	case(3):
	            		if (coorY >= 9)
	            			return;
	            		reseau.mur(true, coorX, coorY);
	        			break;
	            	}
	            	
	            	actif = false;
	
	
	                System.out.println("x= " + (coorX));
	                System.out.println("y= " + (coorY));
	
	            }                
	        }
            @Override
            public void mouseExited (MouseEvent clic) {
            	afficherCurseur = false;
            	repaint();
            }
            @Override
            public void mouseEntered (MouseEvent clic) {
            	afficherCurseur = true;
            	setCursor(curseurVide);
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
        for (int i=0; i < 10; i++) {
            g.drawLine(0, i*tailleCase, 9*tailleCase, i*tailleCase);           
            g.drawLine(i*tailleCase, 0, i*tailleCase, 9*tailleCase);
        }
        
        // Affichage du jeu
        // Pions
        for(Joueur J : reseau.getJeu().getListeJoueurs()) {
        	couleur(g, J.getNumeroJoueur());
        	g.fillOval((J.getPosition().getI() - 1)*tailleCase+3, (J.getPosition().getJ() - 1)*tailleCase+3, tailleCase-6, tailleCase-6);
        }
        // Murs
        for(Mur M : reseau.getJeu().getListeMurs()) {
        	// Si c'est les murs de base
        	if (M.getNumeroJoueur() != 0) {
	        	couleur(g, M.getNumeroJoueur());
	        	if(M.getSens() == 0)
	        		// Horizontal
	        		g.fillRect((M.getI()-1)*tailleCase, (M.getJ()-1)*tailleCase-5, tailleCase, 7);
	        	else
	        		g.fillRect((M.getI()-1)*tailleCase-5,(M.getJ()-1)*tailleCase, 7, tailleCase);
	        }          
        }
        
        // Curseur si etat actif
        if (actif && afficherCurseur) {
        	couleur(g, reseau.getJoueur());
            switch(fonction) {
                case 1:
                	g.fillOval((abscisse/tailleCase)*tailleCase+3,(ordonnee/tailleCase)*tailleCase+3,tailleCase-6,tailleCase-6);
                	break;
                case 2:
                	g.fillRect((abscisse/tailleCase)*tailleCase,(ordonnee/tailleCase)*tailleCase-5,80,7);
                	break;
                case 3:
                	g.fillRect((abscisse/tailleCase)*tailleCase-5,(ordonnee/tailleCase)*tailleCase,7,80);
                	break;
            }
        }
    }
    
    /**
     * Fonction qui change la couleur d'affichage en fonction du joueur
     * @param g			Là où l'on change la couleur
     * @param joueur	Le numéro du joueur [1:4]	
     */
    public void couleur(Graphics g, int joueur) {
    	switch(joueur) {
    		case 1 :
    			g.setColor(Color.CYAN);
    			break;
    		case 3 : 
    			g.setColor(Color.YELLOW);
    			break;
    		case 2 : 
    			g.setColor(Color.PINK);
    			break;
    		case 4 : 
    			g.setColor(Color.ORANGE);
    			break;
    	}
    }
    
    /**
     * Fonction appelée par le réseau lorsque c'est à nous de jouer ou pas
     */
    public void setJouer(boolean b) {
    	actif = b;
    }
    
    /**
     * Fonction appelée par le réseau lorsque la partie se termine de manière anormale
     */
    public void finBrutale() {
		JOptionPane.showMessageDialog(null, "La partie vient de se terminer brutalement, soit un joueur a quitté soit il y a une erreur", "Fin de partie anormale", JOptionPane.ERROR_MESSAGE);
    }
    
}