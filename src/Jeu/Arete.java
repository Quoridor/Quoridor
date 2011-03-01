package Jeu;
import Case;


public class Arete {
 int joueur;
 Case depart;
 Case arrivee;
	
	public Arete(Case depart , Case arrivee , int joueur){
		this.depart=depart;
		this.arrivee=arrivee;
		this.joueur=joueur;
	}
}
