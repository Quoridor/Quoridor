package Jeu;

import java.util.ArrayList;


public class Case {

	private Joueur joueur;
	private int i;
	private int j;
	private boolean marque=false;

	private ArrayList<Case> listeVoisins= new ArrayList<Case>();
	
	public Case(int i, int j){
		this.i=i;
		this.j=j;
	}

	public void setMarque (boolean b){
		this.marque=b;
	}

	public boolean getMarque(){
		return this.marque;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
	
	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}
	
	public ArrayList<Case> getListeVoisins() {
		return listeVoisins;
	}

	public void setListeVoisins(ArrayList<Case> listeVoisins) {
		this.listeVoisins=listeVoisins;
	}


public void miseAJourVoisins(Jeu jeu){
	this.setListeVoisins(new ArrayList<Case>());
	
	// Haut
	if( !(jeu.getListeMurs().contains(new Mur(this,jeu.getTabCase()[this.getI()-1][this.getJ()], 0))) && !(jeu.getListeMurs().contains(new Mur(jeu.getTabCase()[this.getI()-1][this.getJ()], this, 0)))){
		this.listeVoisins.add(jeu.getTabCase()[this.getI()-1][this.getJ()]);
		System.out.println("haut");
	}

	// Bas
	if( !(jeu.getListeMurs().contains(new Mur(this,jeu.getTabCase()[this.getI()+1][this.getJ()], 0))) && !(jeu.getListeMurs().contains(new Mur(jeu.getTabCase()[this.getI()+1][this.getJ()], this, 0)))){
		this.listeVoisins.add(jeu.getTabCase()[this.getI()+1][this.getJ()]);
		System.out.println("bas");
		
	}

	// Gauche
	if( !(jeu.getListeMurs().contains(new Mur(this,jeu.getTabCase()[this.getI()][this.getJ()-1], 0))) && !(jeu.getListeMurs().contains(new Mur(jeu.getTabCase()[this.getI()][this.getJ()-1], this, 0)))){
		this.listeVoisins.add(jeu.getTabCase()[this.getI()][this.getJ()-1]);
		System.out.println("gauche");
	}	

	// Droite
	if( !(jeu.getListeMurs().contains(new Mur(this,jeu.getTabCase()[this.getI()][this.getJ()+1], 0))) && !(jeu.getListeMurs().contains(new Mur(jeu.getTabCase()[this.getI()][this.getJ()+1], this, 0)))){
		this.listeVoisins.add(jeu.getTabCase()[this.getI()][this.getJ()+1]);
		System.out.println("droite");
	}
	
}

@Override
public boolean equals(Object obj) {
	Case buf = (Case) obj;
	return (buf.getJ() == this.j) && (buf.getI() == this.i);
}
	
}

