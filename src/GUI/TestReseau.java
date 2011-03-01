package GUI;

import Jeu.Jeu;

public class TestReseau {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jeu jeu = new Jeu(2);
		Reseau reseau = new Reseau("localhost", 4242, jeu);

		
	}

}
