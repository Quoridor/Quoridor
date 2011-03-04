package GUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CellJoueur extends JLabel implements ListCellRenderer {
	//ImageIcon icon; 
	//ImageIcon selectIcon;
	//Color partiePleine = Color.RED;

	
	CellJoueur (){
		this.setEnabled(false);
	}
	  
	public void Rendu(){
		//icon = new ImageIcon(getClass().getResource("/jlist/img1.gif"));
		//selectIcon  = new ImageIcon(getClass().getResource("/jlist/img2.gif"));
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object obj,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		//Partie partie = (Partie) obj;
	    Joueur joueur = (Joueur) obj;
	    if (!cellHasFocus) {
	    	setBackground(Color.GRAY);
		}	
	    else	    
	    {
	       setBackground(Color.BLACK);
	    }
		switch(index - 1) {
		case 1 :
			setForeground(Color.BLUE);
			break;
		case 3 : 
			setForeground(Color.RED);
			break;
		case 2 : 
			setForeground(Color.YELLOW);
			break;
		case 4 : 
			setForeground(Color.GREEN);
			break;
	}
	    
	    setIcon(joueur.icone);
	    setText(joueur.nom);
	    
	    setEnabled(list.isEnabled());
	    setFont(list.getFont());
	    setOpaque(true);
	    return this;
	}
	
}

class Joueur{
	int numero;
	String nom;
	ImageIcon icone;
	
	Joueur(int _numero, String _nom){
		this.numero = _numero;
		this.nom = _nom;
		if(numero==1)
			this.icone=new ImageIcon("images/joueurRouge.png");
		if(numero==2)
			this.icone=new ImageIcon("images/joueurBleu.png");
		if(numero==3)
			this.icone=new ImageIcon("images/joueurJaune.png");
		if(numero==4)
			this.icone=new ImageIcon("images/joueurVert.png");
	}
}
	
