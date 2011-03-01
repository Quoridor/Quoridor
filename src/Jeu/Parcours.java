package Jeu;

public class Parcours {

	
	
	
	public boolean parcours (Jeu jeu){
		
		for(int i=0 ; i<9 ; i++)
		{ for(int j=0 ; j<9 ; j++)
			jeu.getTabCase()[i][j].setMarque(0);
		}
		
		return false;
		
	}
	
	
}
