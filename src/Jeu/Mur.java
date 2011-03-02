package Jeu;


public class Mur {
	int numeroJoueur;
	private Case case1;
	private Case case2;
	private int sens;
	private int i;
	private int j;
	

	
	public Mur(Case case1,Case case2, int numeroJoueur){
		this.case1=case1;
		this.case2=case2;
		this.numeroJoueur=numeroJoueur;
		this.calculerSens();
	}
	
	public void calculerSens(){
		if(this.case1.getI()==this.case2.getI()){
			this.sens=1;
		}
		if(this.case1.getJ()==this.case2.getJ()){
			this.sens=0;
		}
	}
	
	public int getI(){
		if(this.sens==0){
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
		if(this.sens==0){
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
}