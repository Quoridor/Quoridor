/**
 * Cette classe gère toute la logique du jeu et permet de respecter les règles
 */

package Jeu;

public class Jeu {
	
	/**
	 * Constructeur
	 * @param nbJoueur Le nombre de joueurs initial : 2 ou 4
	 */
	public Jeu(int nbJoueur) {
		
	}
	
	/**
	 * Fonction de déplacement d'un joueur
	 * @param joueur Numéro du joueur à déplacer
	 * @param x		 Abscisse où le déplacer
	 * @param y		 Ordonnée où le déplacer
	 * @return		 Renvoie true si l'action est réalisée et réalisable false sinon
	 */
	public boolean deplacer(int joueur, int x, int y) {
		
		return  true;
	}
	
	/**
	 * Fonction d'ajout de mur
	 * @param joueur Numéro du joueur qui veut construire le mur
	 * @param sens   Sens du mur : vertical=true, horizontal=false
	 * @param x		 Abscisse du coin haut gauche
	 * @param y		 Ordonnée du coin haut gauche
	 * @return		 Renvoie true si l'action est réalisée et réalisable false sinon
	 */
	public boolean mur(int joueur, boolean sens, int x, int y) {
		
		return true;
	}
	
	/**
	 * Test si la partie est finie
	 * @return 		 Renvoie le numéro du joueur gagnant ou 0 si la partie est en cours
	 */
	public int victoire() {
		
		return 0;
	}	

}
