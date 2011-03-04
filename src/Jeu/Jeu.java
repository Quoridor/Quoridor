package Jeu;

import java.util.ArrayList;

/**
 * Cette classe gère toute la logique du jeu et permet de respecter les règles
 */

public class Jeu {

	public int nbJoueur ;/* Le nombre de joueurs initial : 2 ou 4*/


	private Case[][] tabCase= new Case[11][11] ;
	private ArrayList<Mur> listeMurs =new ArrayList<Mur>();
	private ArrayList<Joueur> listeJoueurs =new ArrayList<Joueur>();


	public Jeu(int nbJoueur) {

		this.nbJoueur=nbJoueur;

		for(int i=0 ; i<11 ; i++)
		{ 
			for(int j=0 ; j<11 ; j++)
			{ 
				tabCase[i][j]=new Case(i,j);
			}
		}


		for(int i=0 ; i<10 ; i++)
		{ 
			this.listeMurs.add( new Mur(getTabCase()[i][0],getTabCase()[i][1], 0));
			this.listeMurs.add( new Mur(getTabCase()[i][9],getTabCase()[i][10], 0));
		}
		for(int j=0 ; j<10 ; j++)
		{ 
			this.listeMurs.add( new Mur(getTabCase()[0][j],getTabCase()[1][j], 0));
			this.listeMurs.add( new Mur(getTabCase()[9][j],getTabCase()[10][j], 0));
		}


		for(int i=1; i<=nbJoueur;i++){
			Joueur J = new Joueur(i,this);
			this.listeJoueurs.add(J);
		}

	}




	public boolean deplacer(int numeroJoueur, int i, int j) {
		Joueur joueurDeplace=this.listeJoueurs.get(numeroJoueur-1);
		Case caseArrivee = tabCase[i][j];
		//Tester si caseArrivee est dans la liste des voisins de caseDepart !
		Case caseDepart = joueurDeplace.getPosition();

		if(caseDepart.getListeVoisins().contains(caseArrivee))
		{
			if (caseArrivee.getJoueur()==null) {
				joueurDeplace.setCoord( i ,j ,this);
				return true;
			} 
			return false;
		}

		else
		{
			ArrayList<Case> pionsvoisins = new ArrayList<Case>();
			for(Case cases : caseDepart.getListeVoisins()){
					if (cases.getJoueur()!=null){
						pionsvoisins.add(cases);
					}
			}
			int nombrepionsvoisins = pionsvoisins.size();
			if (nombrepionsvoisins==0) return false;
			else {
				for(Case casevoisine : pionsvoisins) {
					if ((casevoisine.getListeVoisins().contains(caseArrivee))&&((i==caseDepart.getI()+2)||(i==caseDepart.getI()-2)||(j==caseDepart.getJ()+2)||(j==caseDepart.getJ()-2))){
						{joueurDeplace.setCoord( i ,j ,this); return true;}
					}
					else if((casevoisine.getListeVoisins().contains(caseArrivee))&&!(casevoisine.getListeVoisins().contains(tabCase[2*(casevoisine.getI())-caseDepart.getI()][2*(casevoisine.getJ())-caseDepart.getJ()]))){
						{joueurDeplace.setCoord( i ,j ,this); return true;}
					}
					}
				return false;
				}


			}
		}

	public boolean mur(int numeroJoueur, int sens, int i, int j) {
		if(this.listeJoueurs.get(numeroJoueur-1).getNombreMurs()>=1){
		   if(sens==1){ // Horizontal
               if(this.listeMurs.contains(new Mur(this.tabCase[i][j], this.tabCase[i-1][j], numeroJoueur))||(this.listeMurs.contains(new Mur(this.tabCase[i][j+1], this.tabCase[i-1][j+1], numeroJoueur)))){
            	   System.out.println("blocage horizontal");    
            	   return false;
                       }
               if (this.listeMurs.contains(new Mur(this.tabCase[i][j], this.tabCase[i][j+1], numeroJoueur))&&(this.listeMurs.contains(new Mur(this.tabCase[i-1][j], this.tabCase[i-1][j+1], numeroJoueur)))) {
            	   System.out.println("blocage horizontal 2");  
            	   return false;
               }
               }
       
		   else{ // Vertical
               if(this.listeMurs.contains(new Mur(this.tabCase[i][j], this.tabCase[i][j-1], numeroJoueur))||(this.listeMurs.contains(new Mur(this.tabCase[i+1][j-1], this.tabCase[i+1][j], numeroJoueur)))){
                     System.out.println("blocage vertical");
            	   return false;
               }
               if (this.listeMurs.contains(new Mur(this.tabCase[i+1][j], this.tabCase[i][j], numeroJoueur))&&(this.listeMurs.contains(new Mur(this.tabCase[i+1][j-1], this.tabCase[i][j-1], numeroJoueur)))) {
            	   System.out.println("blocage vertical 2");
            	   return false;
               }
		   }	
		
		
		   boolean b[] = new boolean[this.nbJoueur];
		   this.poseMur(numeroJoueur,sens,i,j);
		   for(Joueur joueur : this.listeJoueurs){
			   marquer(joueur.getPosition());
			   for (Case casesArrivee : joueur.getListeCasesArrivee()){
				   if(casesArrivee.getMarque())
				   {
					   b[joueur.getNumeroJoueur()-1]=true;
				   }
			   }
			   this.demarquer();
			}
		   	if(!(this.nbJoueur == 4 ? (b[0]&&b[1]&&b[2]&&b[3]) : b[0]&&b[1])) {
			   this.retirerMur(numeroJoueur, sens, i, j);
		   	}
		   	else {
		   		this.listeJoueurs.get(numeroJoueur-1).setnombreMurs(this.listeJoueurs.get(numeroJoueur-1).getNombreMurs() -1);
		   		}
		   	System.out.println("blocage marquage");  
		   	return((this.nbJoueur == 4 ? (b[0]&&b[1]&&b[2]&&b[3]) : b[0]&&b[1]));
		   	
			}
		else return false;
	}
	
	public void poseMur(int numeroJoueur, int sens, int i , int j){
			
		if(sens==1){
			
			Case ici = this.tabCase[i][j];	
			this.listeMurs.add(new Mur(ici,this.tabCase[i-1][j], numeroJoueur));
			this.listeMurs.add(new Mur(this.tabCase[i][j+1],this.tabCase[i-1][j+1], numeroJoueur));
		}
		else {
			
			Case ici = this.tabCase[i][j];	
			this.listeMurs.add(new Mur(ici,this.tabCase[i][j-1], numeroJoueur));
			this.listeMurs.add(new Mur(this.tabCase[i+1][j],this.tabCase[i+1][j-1], numeroJoueur));
		}
	}

	private void retirerMur(int numeroJoueur, int sens, int i , int j){
		Case ici = this.tabCase[i][j];
		if(sens==1){
			this.listeMurs.remove(new Mur(ici,this.getTabCase()[i-1][j], numeroJoueur));
			this.listeMurs.remove(new Mur(this.getTabCase()[i][j+1],this.getTabCase()[i-1][j+1], numeroJoueur));
		}
		else
			{
				this.listeMurs.remove(new Mur(ici,this.getTabCase()[i][j-1], numeroJoueur));
				this.listeMurs.remove(new Mur(this.getTabCase()[i+1][j],this.getTabCase()[i+1][j-1], numeroJoueur));
			}
		for(Joueur player : this.getListeJoueurs())
			player.getPosition().miseAJourVoisins(this);
			
	}



	public void marquer(Case c){
		c.miseAJourVoisins(this);
		c.setMarque(true);
		for(Case casesVoisines : c.getListeVoisins())
		{
			if(!casesVoisines.getMarque())
				marquer(casesVoisines);
		}


	}

	public void demarquer(){

		for(int i=0 ; i<11 ; i++)
		{ 
			for(int j=0 ; j<11 ; j++)
			{ 
				tabCase[i][j].setMarque(false);}}
	}


	public int victoire() {
		for(Joueur j : this.listeJoueurs)
			{if(j.getListeCasesArrivee().contains(j.getPosition()))
					return j.getNumeroJoueur();}
		return 0;
	}	

	public Case[][] getTabCase() {
		return tabCase;
	}

	public void setTabCase(Case[][] tabCase) {
		this.tabCase = tabCase;
	}

	public ArrayList<Mur> getListeMurs() {
		return listeMurs;
	}

	public void setListeMurs(ArrayList<Mur> listeMurs) {
		this.listeMurs = listeMurs;
	}

	public ArrayList<Joueur> getListeJoueurs() {
		return listeJoueurs;
	}

	public void setListeJoueurs(ArrayList<Joueur> listeJoueurs) {
		this.listeJoueurs = listeJoueurs;
	}


	public static void main(String[] argv){
		Jeu jeu = new Jeu(2);
		System.out.println(jeu.getListeJoueurs().get(0).getNombreMurs());
		
		System.out.println(jeu.mur(1,1,4,8));
		System.out.println(jeu.getListeJoueurs().get(0).getNombreMurs());
		jeu.mur(1,0,8,2);
		System.out.println(jeu.getListeJoueurs().get(0).getNombreMurs());
		}
}
