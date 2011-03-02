package Jeu;

import java.util.ArrayList;

public class Joueur {


	private ArrayList<Case> listeCasesArrivee = new ArrayList<Case>(); 
	private int numeroJoueur;
	private int nombreMurs;
	private Case position;


	public Joueur(int numeroJoueur, Jeu jeu){
		this.numeroJoueur=numeroJoueur;
		switch(numeroJoueur){
		case 1 :
			for(int j=1;j<10;j++){
				this.listeCasesArrivee.add(jeu.getTabCase()[1][j]);
			}
			this.setPosition(jeu.getTabCase()[9][5]);
			break;
		case 3 :
			for(int i=1;i<10;i++){
				this.listeCasesArrivee.add(jeu.getTabCase()[i][9]);
			}
			this.setPosition(jeu.getTabCase()[5][1]);
			break;
		case 2 :
			for(int j=1;j<10;j++){
				this.listeCasesArrivee.add(jeu.getTabCase()[9][j]);
			}
			this.setPosition(jeu.getTabCase()[1][5]);
			break;
		case 4 :
			for(int i=1;i<10;i++){
				this.listeCasesArrivee.add(jeu.getTabCase()[i][1]);
			}
			this.setPosition(jeu.getTabCase()[5][9]);
			break;
		default :
			this.listeCasesArrivee=null;
			break;
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
	

	public void setPosition(Case position) {
		this.position = position;
		this.position.setJoueur(this);
	}

}
