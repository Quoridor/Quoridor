package Jeu;


public class Mur {
	Joueur joueur;
	private Case case1;
	private Case case2;

	public Mur(Case case1,Case case2, Joueur joueur){
		this.case1=case1;
		this.case2=case2;
		this.joueur=joueur;
	}
}