package GUI;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.RootPaneContainer;
import java.awt.BorderLayout;

public class PlateauJeu extends JPanel {
	static final long serialVersionUID = 1;
	
	public PlateauJeu() {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(270,270));
		JPanel plateauJeu = new JPanel();
		setLayout(new BorderLayout(0,0));
		add(plateauJeu, BorderLayout.NORTH);
		add(FenetreJeu, BorderLayout.CENTER);
		((RootPaneContainer) plateauJeu).setContentPane(new Grille());
	}
}
