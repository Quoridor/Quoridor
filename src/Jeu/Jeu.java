/**
 * Cette classe gère toute la logique du jeu et permet de respecter les règles
 */

package Jeu;

public class Jeu {
	
	private int nbJoueur ;/* Le nombre de joueurs initial : 2 ou 4*/
	private Case[][] tabCase= new Case[9][9] ;
	
	
	public Jeu(int nbJoueur) {
		this.nbJoueur=nbJoueur;
	
		for(int i=0 ; i<9 ; i++)
		{ for(int j ; j<9 ; j++)
		{ if(i==0) tabCase[i][j].gauche = null;
		else tabCase[i-1][j].gauche;
		if(i==8) tabCase[i][j].gauche = null
		else tabCase[i-1][j].gauche
		}
		}
	
	}
	
	/**
	 * Fonction de déplacement d'un joueur
	 * @param joueur Numéro du joueur à déplacer
	 * @param x		 Abscisse où le déplacer
	 * @param y		 Ordonnée où le déplacer
	 * @return		 Renvoie true si l'action est réalisée et réalisable false sinon
	 */
	
	
	
	public boolean deplacer(int joueur, int x, int y) {
		/* convertir x y en ij valables */
		laCase = tabCase[i][j];
		
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
		laCase = tabCase[x][y];
		larete = laCase.get
		
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
