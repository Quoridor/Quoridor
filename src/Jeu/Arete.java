package Jeu;


public class Arete {
 int joueur;
 Case depart;
 private Case arrivee;
	
	public Arete(Case depart , Case arrivee ){
		this.depart=depart;
		this.arrivee=arrivee;
		this.joueur=0;
	}
	
	
	
	public Case getArrivee(){
		if(this.joueur==0)
			return this.arrivee;
		else return null;
	}
	
	public Case getArriveePlus(){
		
			return this.arrivee;
		
	}
	
	
	
	
	
}
