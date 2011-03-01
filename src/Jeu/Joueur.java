package Jeu;

public class Joueur {

	
	private ArrayList<Case> = new listeCasesArrivee<Case>(); 
	private int numeroJoueur;
	private int nombreMurs;
	private Case position;
	
public Joueur(int numeroJoueur, Jeu jeu){
	this.numeroJoueur=numeroJoueur;
	if(numeroJoueur==1){
		for(int j=0;j<9;j++){
			this.listeCasesArrivee.add(jeu.getTabCase()[1][j]);
		}
		
	}
		
	}

	public int getNumeroJoueur() {
		return numeroJoueur;
	}
	public int getNombreMurs() {
		return nombreMurs;
	}
	public Case getPosition() {
		return position;
	}
	
}
