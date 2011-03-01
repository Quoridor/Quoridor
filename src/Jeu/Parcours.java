package Jeu;

public class Parcours {

	
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
	
	

	
	
	
	
	
	
	public boolean testMur (Jeu jeu , boolean b, int i , int j){
		
		
		
		
		
		for(int i1=0 ; i1<9 ; i1++)
		{ for(int j1=0 ; j1<9 ; j1++)
			jeu.getTabCase()[i1][j1].setMarque(false);
		}
		
		
		
		
		
		
		
		return false;
		
	}
	
	
}
