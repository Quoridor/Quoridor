package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerNumberModel;

public class SelectionPartie extends JFrame  implements ActionListener{
	private Reseau reseau;
	private DefaultListModel modele = new DefaultListModel();
	private JList liste;
	
	public SelectionPartie(Reseau reseau) {
		super("Liste de partie sur le serveur");		
		
		this.reseau = reseau;
		reseau.setPartie(this);
		
		ListCellRenderer renduCellule = new Cell();		
		
		
		liste = new JList(modele);
		liste.setCellRenderer(renduCellule);

		Box hbox1 = Box.createHorizontalBox();
		
		JButton rafraichir = new JButton("Rafraichir la liste");
		JButton connecter = new JButton("Se connecter");
		JButton creer = new JButton("Créer une partie");
		hbox1.add(creer);
		hbox1.add(rafraichir);
		hbox1.add(connecter);		
		
		Box vbox = Box.createVerticalBox();
		vbox.add(new JScrollPane(liste));
		vbox.add(hbox1);
		
		creer.addActionListener(this);
		rafraichir.addActionListener(this);
		connecter.addActionListener(this);
		
		this.add(vbox);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		rafraichir();
	}

	// Connexion
	@Override
	public void actionPerformed(ActionEvent e) {		
		if (e.getActionCommand().equals("Rafraichir la liste")) { //TODO moche
			reseau.recupererParties();
		}
		// Rejoindre une partie
		if (e.getActionCommand().equals("Se connecter") && liste.getSelectedIndices().length != 0) { //TODO moche
			reseau.rejoindre(((Partie)modele.get(liste.getSelectedIndices()[0])).nom); //TODO Vérifier s'il ne peut pas y avoir un problème lors d'une latence trop élevée
			//this.setVisible(false);			
			//new FenetreJeu(reseau, this);
		}
		// Créer une partie
		if (e.getActionCommand().equals("Créer une partie")) { //TODO moche
			try {
				reseau.creerPartie(JOptionPane.showInputDialog("Nom de la partie"), Integer.parseInt(JOptionPane.showInputDialog("Nombre de joueurs (2 ou 4)")));
			} catch (Exception erreur) {} // Cas où le nombre serait vide			
		}
	}
	
	// Rafraichir la liste
	public void rafraichir() {				
		// Réinitialisation
		modele.clear();
		
		// Remplissage
		for (Partie p : reseau.getParties())
			modele.addElement(p);
		
		// Rafraichissement dans le cas où il n'y a pas de parties
		liste.setModel(modele);
	}
}
