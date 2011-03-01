package Jeu;

import java.util.ArrayList;

/**
 * Cette classe gère toute la logique du jeu et permet de respecter les règles
 */

public class Jeu {
	
	private ArrayList<Joueur> listeJoueurs = new ArrayList<Joueur>();
	private int nbJoueur ;/* Le nombre de joueurs initial : 2 ou 4*/

	private Case[][] tabCase= new Case[9][9] ;
	
	
	public Jeu(int nbJoueur) {
		this.nbJoueur=nbJoueur;
	
		for(int i=0 ; i<9 ; i++)
		{ for(int j=0 ; j<9 ; j++)
		{ if(j==0) 
			tabCase[i][j].setGauche(null);
		else 
			tabCase[i][j].setGauche( new Arete(tabCase[i][j] , tabCase[i][j-1])) ;
		if(j==8)
			tabCase[i][j].setGauche(null);
		else
			tabCase[i][j].setGauche (new Arete(tabCase[i][j] , tabCase[i][j+1]));
		if(i==0) 
			tabCase[i][j].setHaut(null);
		else 
			tabCase[i][j].setHaut( new Arete(tabCase[i][j] , tabCase[i-1][j])) ;
		if(i==8)
			tabCase[i][j].setBas(null);

		else
			tabCase[i][j].setBas (new Arete(tabCase[i][j] , tabCase[i+1][j]));
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
	
	
	
	public boolean deplacer(int numeroJoueur, int x, int y) {
		int i=0, j=0 ;
		/* convertir x y en ij valables */
		
		Joueur joueur = this.listeJoueurs.get(numeroJoueur-1);
		Case caseArrivee = tabCase[i][j];
		Case caseDepart = joueur.getPosition();
		
		
		
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
		Case laCase = tabCase[x][y];
		Arete larete;
		
		if(sens == true)
		larete = laCase.getHaut();
		else larete = laCase.getDroite();
		
		return true;
	}
	
	/**
	 * Test si la partie est finie
	 * @return 		 Renvoie le numéro du joueur gagnant ou 0 si la partie est en cours
	 */
	public int victoire() {
		
		return 0;
	}	
	
	public Case[][] getTabCase() {
		return tabCase;
	}

	public void setTabCase(Case[][] tabCase) {
		this.tabCase = tabCase;
	}

}
