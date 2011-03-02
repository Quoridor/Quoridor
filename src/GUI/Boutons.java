package GUI;

//import java.awt.Dimension;

import javax.swing.*;
//import java.awt.*;

public class Boutons extends JToolBar {
	
	public Boutons(ControleurBoutons  controleur) {
		super("Barre d'Outils", JToolBar.HORIZONTAL);
		//Liste des boutons et à modifier
		JButton deplacement = new JButton(new ImageIcon("test.jpg")),
    	murH = new JButton(new ImageIcon("test.jpg")),
    	murV = new JButton(new ImageIcon("test.jpg")),
    	
    	//retourMenu = new JButton(new ImageIcon("test.jpg")),
		Regle = new JButton(new ImageIcon("test.jpg"));
    
		deplacement.setText("Déplacement");
		murH.setText("Mur Horizontal");
		murV.setText("Mur Vertical");
		Regle.setText("Règle du jeu");
		
		deplacement.addActionListener(controleur);
		murH.addActionListener(controleur);
		murV.addActionListener(controleur);
		Regle.addActionListener(controleur);
				
		//boutons.setPreferredSize(new Dimension(800,200));
		this.add(deplacement);
		this.add(murH);
		this.add(murV);
		//boutons.add(retourMenu);
		this.add(Regle);
  }
	
	

}
