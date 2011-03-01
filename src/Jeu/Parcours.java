package Jeu;

public class Parcours {

	
	public void marquage ( Case c){
		c.setMarque(true);
		
		if ( c.getGauche()!=null && c.getGauche().arrivee!=null)
			marquage(c.getGauche().arrivee);
		if ( c.getDroite()!=null && c.getDroite().arrivee!=null)
			marquage(c.getDroite().arrivee);
		if ( c.getHaut()!=null && c.getHaut().arrivee!=null)
			marquage(c.getHaut().arrivee);
		if ( c.getBas()!=null && c.getBas().arrivee!=null)
			marquage(c.getGauche().arrivee);
		
		
	}
	
	
	
	
	public boolean parcours (Jeu jeu){
		
		
		
		
		
		for(int i=0 ; i<9 ; i++)
		{ for(int j=0 ; j<9 ; j++)
			jeu.getTabCase()[i][j].setMarque(false);
		}
		
		
		
		
		
		
		
		return false;
		
	}
	
	
}
