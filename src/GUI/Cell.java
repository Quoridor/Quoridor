package GUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class Cell extends JLabel implements ListCellRenderer {
	ImageIcon icon; 
	ImageIcon selectIcon;
	Color partiePleine = Color.RED;
	  
	public void Rendu(){
		//icon = new ImageIcon(getClass().getResource("/jlist/img1.gif"));
		//selectIcon  = new ImageIcon(getClass().getResource("/jlist/img2.gif"));
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object obj,
			int arg2, boolean arg3, boolean isSelected) {
		
		Partie partie = (Partie) obj;
	    
	    if (isSelected) {
	       setBackground(list.getSelectionBackground());
	       setForeground(Color.darkGray);
	       setIcon(selectIcon);
	    }else{
	       setBackground(list.getBackground());
	       setForeground(list.getForeground());
	       setIcon(icon);
	    }
	    
	    setText("<" + partie.nbConnectes + "/" + partie.nbJoueurs + " joueurs> " + partie.nom);
	    
	    setEnabled(list.isEnabled());
	    setFont(list.getFont());
	    setOpaque(true);
	    return this;
	}
}
