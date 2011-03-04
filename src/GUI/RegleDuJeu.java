package GUI;

import javax.swing.JFrame;
import javax.swing.JEditorPane;
import javax.swing.text.html.HTMLEditorKit;


public class RegleDuJeu extends JFrame {
	public RegleDuJeu() {
		
		super("Aide");
		setSize(500,500); //à modifier
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JEditorPane texteRegle = new JEditorPane();
		texteRegle.setEditorKit( new HTMLEditorKit()); 
		
		texteRegle.setText("<h1><center>Les règles du jeu</h1></center>" +
				"" +
				"<p>Le jeu oppose soit 2 joueurs, soit 4.</p> " +
				"" +
				"Chaque joueur positionne son pion au milieu d’un des bords du plateau, son objectif étant d’atteindre le premier " +
				"l’une des cases du bord opposé Les pions se déplacent horizontalement et verticalement, d’une case a la fois (sauf " +
				"dans un cas particulier décrit ci-dessous). " +
				"" +
				"<p>Les joueurs jouent à tour de rôle. </p>" +
				"" +
				"<p>La richesse du jeu vient de la présence de murs. Un total de vingt murs est disponible en début de partie et est " +
				"équitablement réparti entre les joueurs. A chaque tour de jeu, un joueur peut soit déplacer son pion, soit poser " +
				"un mur (s’il lui en reste). Un mur se place entre les cases du plateau de jeu et bloque le passage entre les quatre " +
				"cases concernées (soit verticalement, soit horizontalement). Quand il place un mur, un joueur ne peut pas bloquer " +
				"totalement un autre joueur qui doit toujours disposer d’au moins un chemin pour atteindre son côté objectif depuis " +
				"sa position actuelle. Les murs sont placés de façon définitive. Lors de leur déplacement, les pions ne peuvent pas " +
				"franchir les murs. En revanche, un pion peut sauter au dessus d’un autre pion adjacent.</p>" +
				"" +
				"<p>En outre, si la case jouxtant le pion voisin n’est pas libre (ou est bloquée par un mur), le joueur peut alors " +
				"déplacer son pion sur n’importe quelle case libre adjacente au pion voisin.</p>");
		add(texteRegle);
		texteRegle.setEditable(false);
		setVisible(true);		
	}	
}