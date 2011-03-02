package Jeu;

import java.util.ArrayList;

/**
 * Cette classe gère toute la logique du jeu et permet de respecter les règles
 */

public class Jeu {

	private int nbJoueur ;/* Le nombre de joueurs initial : 2 ou 4*/


	private Case[][] tabCase= new Case[11][11] ;
	private ArrayList<Mur> listeMurs =new ArrayList<Mur>();
	private ArrayList<Joueur> listeJoueurs =new ArrayList<Joueur>();


	public Jeu(int nbJoueur) {

		this.nbJoueur=nbJoueur;

		for(int i=0 ; i<11 ; i++)
		{ 
			for(int j=0 ; j<11 ; j++)
			{ 
				tabCase[i][j]=new Case(i,j);
			}
		}
		
		for(int i=1; i<=nbJoueur;i++){
			this.listeJoueurs.add(new Joueur(i,this));
		}
		
		
		for(int i=0 ; i<11 ; i++)
		{ 
			this.listeMurs.add( new Mur(getTabCase()[i][0],getTabCase()[i][1], getListeJoueurs().get(0)));
			this.listeMurs.add( new Mur(getTabCase()[i][9],getTabCase()[i][10], getListeJoueurs().get(0)));
		}
		for(int j=0 ; j<11 ; j++)
		{ 
			this.listeMurs.add( new Mur(getTabCase()[0][j],getTabCase()[1][j], getListeJoueurs().get(0)));
			this.listeMurs.add( new Mur(getTabCase()[9][j],getTabCase()[10][j], getListeJoueurs().get(0)));
		}

		

	}

	/**
	 * Fonction de déplacement d'un joueur
	 * @param joueur Numéro du joueur à déplacer
	 * @param x		 Abscisse où le déplacer
	 * @param y		 Ordonnée où le déplacer
	 * @return		 Renvoie true si l'action est réalisée et réalisable false sinon
	 */



	public boolean deplacer(Joueur joueurDeplace, int i, int j) {

		Case caseArrivee = tabCase[i][j];
		//Tester si caseArrivee est dans la liste des voisins de caseDepart !
		Case caseDepart = joueurDeplace.getPosition();
		for(Joueur joueurs : this.listeJoueurs){
			for(Mur mur : this.listeMurs)
			{
				if(this.listeMurs.contains(new Mur(caseDepart,caseArrivee,joueurs))==true | this.listeMurs.contains(new Mur(caseArrivee,caseDepart,joueurs))==true)
					return false;
			}

			if(caseArrivee.getJoueur()!=null)
			{ System.out.println(j+caseArrivee.getJ()-caseDepart.getJ());
				Case caseSaut = tabCase[i+caseArrivee.getI()-caseDepart.getI()][j+caseArrivee.getJ()-caseDepart.getJ()];
				return deplacer(caseArrivee.getJoueur(), caseSaut.getI(), caseSaut.getJ());
			}

		}

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

	public void marquer(Case c){
		c.miseAJourVoisins(this);
		c.setMarque(true);
		for(Case casesVoisines : c.getListeVoisins())
		{
			if(!casesVoisines.getMarque())
				marquer(casesVoisines);
		}


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

	public ArrayList<Mur> getListeMurs() {
		return listeMurs;
	}

	public void setListeMurs(ArrayList<Mur> listeMurs) {
		this.listeMurs = listeMurs;
	}

	public ArrayList<Joueur> getListeJoueurs() {
		return listeJoueurs;
	}

	public void setListeJoueurs(ArrayList<Joueur> listeJoueurs) {
		this.listeJoueurs = listeJoueurs;
	}
	

	public static void main(String[] argv){
		Jeu jeu = new Jeu(2);
		System.out.println(jeu.getListeJoueurs().get(1).getPosition().getI());
		if (jeu.deplacer(jeu.getListeJoueurs().get(1),4,1)) {
			System.out.println("coucou");
		}
		jeu.getListeJoueurs().get(0).setPosition(jeu.getTabCase()[4][2]);
		if (jeu.deplacer(jeu.getListeJoueurs().get(1),4,2)){
			System.out.println("sjklfjskl");
		}
	}

}
