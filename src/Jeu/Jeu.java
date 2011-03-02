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
		
		

		for(int i=0 ; i<11 ; i++)
		{ 
			this.listeMurs.add( new Mur(getTabCase()[i][0],getTabCase()[i][1],1));
			this.listeMurs.add( new Mur(getTabCase()[i][9],getTabCase()[i][10],1));
		}
		for(int j=0 ; j<11 ; j++)
		{ 
			this.listeMurs.add( new Mur(getTabCase()[0][j],getTabCase()[1][j],1));
			this.listeMurs.add( new Mur(getTabCase()[9][j],getTabCase()[10][j],1));
		}
		
		
		for(int i=1; i<=nbJoueur;i++){
			Joueur J = new Joueur(i,this);
			this.listeJoueurs.add(J);
		}
		
	}

	


	public boolean deplacer(int numeroJoueur, int i, int j) {
		Joueur joueurDeplace=this.listeJoueurs.get(numeroJoueur-1);
		Case caseArrivee = tabCase[i][j];
		//Tester si caseArrivee est dans la liste des voisins de caseDepart !
		Case caseDepart = joueurDeplace.getPosition();

		if(!caseDepart.getListeVoisins().contains(caseArrivee))
		{ System.out.println("pas voisin");
		return false;
		}
		else
		{
			return  true;
		}
	}



	public boolean mur(int numeroJoueur, boolean sens, int x, int y) {
		
		
		
		return true;
	}


	private void poseMur(int numeroJoueur, int sens, int i , int j){
		Case ici = this.tabCase[i][j];
		if(sens==0){
			this.listeMurs.add(new Mur(ici,this.getTabCase()[i-1][j], numeroJoueur));
			this.listeMurs.add(new Mur(this.getTabCase()[i][j+1],this.getTabCase()[i-1][j+1], numeroJoueur));
		}
		else
			if (sens==1){
				this.listeMurs.add(new Mur(ici,this.getTabCase()[i][j-1], numeroJoueur));
				this.listeMurs.add(new Mur(this.getTabCase()[i+1][j],this.getTabCase()[i+1][j-1], numeroJoueur));
			}

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

	public void demarquer(){

		for(int i=0 ; i<11 ; i++)
		{ 
			for(int j=0 ; j<11 ; j++)
			{ 
				tabCase[i][j].setMarque(false);}}
	}

	
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
		System.out.println(jeu.getListeJoueurs().get(0).getPosition().getI());
		System.out.println(jeu.getListeJoueurs().get(0).getPosition().getJ());
		System.out.println(jeu.getListeJoueurs().get(1).getPosition().getI());
		System.out.println(jeu.getListeJoueurs().get(1).getPosition().getJ());
		if (jeu.deplacer(1,8,5)) {
			System.out.println("coucou");
		}
		jeu.getListeJoueurs().get(0).setPosition(jeu.getTabCase()[4][2], jeu);
		if (jeu.deplacer(1,4,2)){
			System.out.println("sjklfjskl");
		}
	}

}
