package GUI;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ListeJoueurs extends JList{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ListeJoueurs() {
		super(new DefaultListModel());
		this.setCellRenderer(new CellJoueur());
	}	
}