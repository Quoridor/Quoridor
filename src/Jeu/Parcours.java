package Jeu;

public class Parcours {}
/*
	
	public void marquage ( Case c){
		c.setMarque(true);
		
		if ( c.getGauche()!=null && c.getGauche().getArrivee()!=null)
			marquage(c.getGauche().getArrivee());
		if ( c.getDroite()!=null && c.getDroite().getArrivee()!=null)
			marquage(c.getDroite().getArrivee());
		if ( c.getHaut()!=null && c.getHaut().getArrivee()!=null)
			marquage(c.getHaut().getArrivee());
		if ( c.getBas()!=null && c.getBas().getArrivee()!=null)
			marquage(c.getGauche().getArrivee());
		
		
	}
	
	
	/* public boolean testAcces ( Jeu jeu,Joueur joueur) */
	
	

	
	public void poseMur (Jeu jeu , boolean sens , int joueur , int i , int j){
		
		if( sens==true)
		{jeu.getTabCase()[i][j].getHaut().getArriveePlus().getDroite().joueur=joueur;
		jeu.getTabCase()[i][j].getDroite().joueur=joueur; 
		}
		else{ jeu.getTabCase()[i][j].getDroite().getArriveePlus().getHaut().joueur=joueur;
		jeu.getTabCase()[i][j].getHaut().joueur=joueur; }
		
		
		
		
	}
	
	
	public void enleverMur (Jeu jeu , boolean sens , int i , int j){
		
		if( sens==true)
		{jeu.getTabCase()[i][j].getHaut().getArriveePlus().getDroite().joueur=0;
		jeu.getTabCase()[i][j].getDroite().joueur=0; 
		}
		else{ jeu.getTabCase()[i][j].getDroite().getArriveePlus().getHaut().joueur=0;
		jeu.getTabCase()[i][j].getHaut().joueur=0; }
		
		
		
		
	}
	
	
	
	public boolean testMur (Jeu jeu , boolean b, int i , int j){
		
		poseMur(jeu , b, jeu.getListeJoueurs().get(1).getNumeroJoueur() , i , j);
		
		marquage(jeu.getListeJoueurs().get(1).getPosition());
		
		
		
		
		
		for(int i1=0 ; i1<9 ; i1++)
		{ for(int j1=0 ; j1<9 ; j1++)
			jeu.getTabCase()[i1][j1].setMarque(false);
		}
		
		
		
		
		
		
		
		return false;
		
	}
	
	
}*/
