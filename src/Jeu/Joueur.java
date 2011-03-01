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
		for(int j=0;j<9;j++){
			this.listeCasesArrivee.add(jeu.getTabCase()[1][j]);
		}
		break;
	case 2 :
		for(int i=0;i<9;i++){
			this.listeCasesArrivee.add(jeu.getTabCase()[i][8]);
		}
		break;
	case 3 :
		for(int j=0;j<9;j++){
			this.listeCasesArrivee.add(jeu.getTabCase()[8][j]);
		}
		break;
	case 4 :
		for(int i=0;i<9;i++){
			this.listeCasesArrivee.add(jeu.getTabCase()[i][1]);
		}
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
	
}
