package Jeu;


public class Case {

	public Case(){
	}
	
	private int pion;
	private Case gauche, droite, haut, bas ; /* tableau des aretes adjacentes */
	
	
	
	public void setGauche( Case gauche){
		this.gauche=gauche;
	}
	
	public void setDroite( Case c){
		this.droite=c;
	}
	
	public void setHaut( Case c){
		this.haut=c;
	}
	
	public void setBas( Case c){
		this.bas=c;
	}
	
	public Case getGauche(){
		return this.gauche;
	}
	
	public Case getDroite(){
		return this.droite;
	}
	
	public Case getHaut(){
		return this.haut;
	}
	
	public Case getBas(){
		return this.bas;
	}
	
}
