package GUI;

import javax.swing.*;
import java.awt.*;

public class Boutons {
	public static void main(String[] args) {
    
		JToolBar boutons = new JToolBar("Barre d'Outils", JToolBar.HORIZONTAL);
		
		//Liste des boutons et à modifier
		JButton deplacement = new JButton(new ImageIcon("")),
    			murH = new JButton(new ImageIcon("")),
    			murV = new JButton(new ImageIcon("")),
    			//retourMenu = new JButton(new ImageIcon("")),
				Regle = new JButton(new ImageIcon(""));
    
		boutons.add(deplacement);
		boutons.add(murH);
		boutons.add(murV);
		//boutons.add(retourMenu);
		boutons.add(Regle);
    
    /*frame.getContentPane().add(toolbar,BorderLayout.NORTH);
    frame.setUndecorated(true);
    frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500,400);
    frame.setVisible(true);*/
  }
}
