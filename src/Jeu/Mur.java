package Jeu;


public class Mur {
	int numeroJoueur;
	private Case case1;
	private Case case2;
	private int sens;
	
	public Mur(Case case1,Case case2, int numeroJoueur){
		this.case1=case1;
		this.case2=case2;
		this.numeroJoueur=numeroJoueur;
		this.calculerSens();
	}
	
	public void calculerSens(){
		if(this.case1.getI()==this.case2.getI()){
			this.sens=0;
		}
		if(this.case1.getJ()==this.case2.getJ()){
			this.sens=1;
		}
	}
	
	public int getNumeroJoueur() {
		return numeroJoueur;
	}

	public void setNumeroJoueur(int numeroJoueur) {
		this.numeroJoueur = numeroJoueur;
	}

	public int getSens() {
		return sens;
	}

	public void setSens(int sens) {
		this.sens = sens;
	}

	public int getI(){
		if(this.sens==1){
			if(this.case1.getI()>this.case2.getI()){
				return case1.getI();
			}
			else
				return case2.getI();
		}
		else
		{
			if(this.case1.getJ()>this.case2.getJ()){
				return case1.getI();
			}
			else
				return case2.getI();
		}
	}
	
	public int getJ(){
		if(this.sens==1){
			if(this.case1.getI()>this.case2.getI()){
				return case1.getJ();
			}
			else
				return case2.getJ();
		}
		else
		{
			if(this.case1.getJ()>this.case2.getJ()){
				return case1.getJ();
			}
			else
				return case2.getJ();
		}
	}
	
	public Case getCase1() {
		return case1;
	}
	
	public Case getCase2() {
		return case2;
	}

	@Override
	public boolean equals(Object obj) {
		Mur buf = (Mur) obj;
		return (((buf.getCase1() == this.case1) && (buf.getCase2() == this.case2)) || ((buf.getCase1() == this.case2) && (buf.getCase2() == this.case1)));
	}
}