package Jeu;


public class Mur {
	int numeroJoueur;
	private Case case1;
	private Case case2;

	public Mur(Case case1,Case case2, int numeroJoueur){
		this.case1=case1;
		this.case2=case2;
		this.numeroJoueur=numeroJoueur;
	}
}