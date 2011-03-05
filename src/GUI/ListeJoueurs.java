package GUI;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ListeJoueurs extends JList{
	private static final long serialVersionUID = 1L;
	private DefaultListModel modele = new DefaultListModel();

	ListeJoueurs() {
		super(modele);
		this.setCellRenderer(new CellJoueur());
		this.setSelectedIndex(0);
	}	
}