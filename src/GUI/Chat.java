package GUI;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import sun.awt.resources.awt;

public class Chat extends JPanel {
	// Interface
	private JTextArea discussion = new JTextArea(); 		// Zone de texte
	public JTextField phrase = new JTextField();			// Zone de saisie de texte
	private JButton envoyer = new JButton("Envoyer");		// Bouton d'envoi
	
	// Connexions
	private ControleurReseau controleur;
	
	/**
	 * Constructeur
	 * @param controleur ControleurReseau
	 */
	Chat(Reseau reseau) {
		this.controleur = new ControleurReseau(this, reseau);		
		Box vbox = Box.createVerticalBox();
		Box hbox = Box.createHorizontalBox();
		
		this.setMinimumSize(new Dimension(900, 300));
		this.setPreferredSize(new Dimension(900, 300));
		discussion.setPreferredSize(new Dimension(800, 200));
		discussion.setEditable(false);
		phrase.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent event) {
				System.out.println("Touche pressée : " + event.getKeyCode() + " " + KeyEvent.VK_ENTER);
				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
					controleur.envoyer();
				}
			}
		});
		vbox.add(discussion);
		vbox.add(hbox);
		
		hbox.add(phrase);
		hbox.add(envoyer);
		
		envoyer.addActionListener(controleur);
		this.add(vbox);
		phrase.setText("Message");
		

	}
	
	public void ecrire(String texte) {
		discussion.setText(discussion.getText() + "\n" + texte);
	}
	
	public String getTexte() {
		return phrase.getText();
	}
	
	public void cleanPrompt() {
		phrase.setText("");
	}
	
	public ControleurReseau getControleur() {
		return this.controleur;
	}	
}
