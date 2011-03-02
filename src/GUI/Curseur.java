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

      
        int abscisse;
        int ordonnee;
        Image img;
        int tailleCase=40;
        int fonction=1;
       
        Curseur() {
            setPreferredSize(new Dimension(400,400));
            Toolkit tk = Toolkit.getDefaultToolkit();
            switch(fonction)
            {
            case 1: img = tk.getImage("images/pion.gif"); break;
            case 2: img = tk.getImage("images/wallh.gif"); break;
            case 3: img = tk.getImage("images/wallv.gif"); break;
            }
            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseMoved (MouseEvent evt) {
                    abscisse = evt.getX();
                    ordonnee = evt.getY();
                    repaint();                       
                }
            });
       
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent clic) {
                if ((clic.getX()>40)&&(clic.getX()<400)) {
                    int coorX = clic.getX()/tailleCase;
                    System.out.println("x= " + (coorX));
                }
                if ((clic.getY()>40)&&(clic.getY()<400)) {
                    int coorY = clic.getY()/tailleCase;
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
            g.setColor(Color.RED);
            g.fillRect(tailleCase,tailleCase,9*tailleCase,9*tailleCase);
            g.setColor(Color.BLACK);
            for (int i= 1; i < 11; i++) {
                g.drawLine(tailleCase, i*tailleCase, 10*tailleCase, i*tailleCase);           
                g.drawLine(i*tailleCase, tailleCase, i*tailleCase, 10*tailleCase);
            }
            switch(fonction) {
                case 1:
                    for (int k=0; k<9; k++){
                        for (int j=0; j<9; j++){
                            if ((abscisse<((k+2)*tailleCase))&&(abscisse>((k+1)*tailleCase))&&(ordonnee<((j+2)*tailleCase))&&(ordonnee>((j+1)*tailleCase))/*position relative dans la fenetre*/){
                                g.fillOval((k+1)*tailleCase,(j+1)*tailleCase,tailleCase,tailleCase);
                            }
                        }
                    }
                break;
                case 2:
                    for (int k=0; k<8; k++){
                        for (int j=1; j<9; j++){
                            if ((abscisse<((k+2)*tailleCase))&&(abscisse>((k+1)*tailleCase))&&(ordonnee<((j+2)*tailleCase))&&(ordonnee>((j+1)*tailleCase))/*position relative dans la fenetre*/){
                                g.fillRect((k+1)*tailleCase,(j+1)*tailleCase-5,80,11);
                            }
                        }
                    }
                break;
                case 3:
                    for (int k=1; k<9; k++){
                        for (int j=0; j<8; j++){
                            if ((abscisse<((k+2)*tailleCase))&&(abscisse>((k+1)*tailleCase))&&(ordonnee<((j+2)*tailleCase))&&(ordonnee>((j+1)*tailleCase))/*position relative dans la fenetre*/){
                                g.fillRect((k+1)*tailleCase-5,(j+1)*tailleCase,11,80);
                            }
                        }
                    }
            }
        }
   
}