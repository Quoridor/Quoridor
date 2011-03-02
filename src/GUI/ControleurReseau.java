package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurReseau implements ActionListener {
	private Chat chat;
	private Reseau reseau;
	
	public ControleurReseau(Chat chat, Reseau reseau) {
		this.chat = chat;
		this.reseau = reseau;
	}
	
	// Envoi du message
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.reseau.chat(this.chat.getTexte());
		this.chat.cleanPrompt();
	}
	
	public void ecrire(String texte) {
		this.chat.ecrire(texte);
	}
}
