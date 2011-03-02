package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import Jeu.Jeu;

public class FenetreConnexion extends JFrame implements ActionListener{
	private JTextField nom = new JTextField();
	private JTextField serveur = new JTextField();
	private JSpinner port = new JSpinner(new SpinnerNumberModel(1025, 1, 65536, 1));
	
	public FenetreConnexion() {
		super("Connexion");
		
		Box hbox1 = Box.createHorizontalBox();
		hbox1.add(new JLabel("Utilisateur"));
		hbox1.add(nom);
		
		Box hbox2 = Box.createHorizontalBox();
		hbox2.add(new JLabel("Serveur"));
		hbox2.add(serveur);
		
		Box hbox3 = Box.createHorizontalBox();
		hbox3.add(new JLabel("Port"));
		hbox3.add(port);
		
		Box vbox = Box.createVerticalBox();
		vbox.add(hbox1);
		vbox.add(hbox2);
		vbox.add(hbox3);
		
		JButton bouton = new JButton("Connexion");
		bouton.addActionListener(this);
		vbox.add(bouton);		
		
		this.add(vbox);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// Connexion
	@Override
	public void actionPerformed(ActionEvent arg0) {		
		// Création de l'objet Reseau
		try {
			Reseau reseau = new Reseau(serveur.getText(), (Integer) port.getValue(), nom.getText());
			
			// Lancement du jeu
			new FenetreJeu(reseau);		
			
			// Cacher la fenetre de connexion
			this.setVisible(false);				
		}
		catch (Exception e) {			
			JOptionPane.showMessageDialog(null, "Impossible de joindre le serveur", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}
