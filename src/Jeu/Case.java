package Jeu;


public class Case {

	public Case(){
	}
	
	private int pion=0;
	private Arete gauche, droite, haut, bas ; /* tableau des aretes adjacentes */
	
	
	
	
	
	
	
	public void setPion ( int joueur){
		this.pion = joueur;
	}
	
	public int getPion(){
		return this.pion;
	}
	
	
	
	public void setGauche( Arete gauche){
		this.gauche=gauche;
	}
	
	public void setDroite( Arete c){
		this.droite=c;
	}
	
	public void setHaut( Arete c){
		this.haut=c;
	}
	
	public void setBas( Arete c){
		this.bas=c;
	}
	
	public Arete getGauche(){
		return this.gauche;
	}
	
	public Arete getDroite(){
		return this.droite;
	}
	
	public Arete getHaut(){
		return this.haut;
	}
	
	public Arete getBas(){
		return this.bas;
	}
	
}
