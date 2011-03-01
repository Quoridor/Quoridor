package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class Boutons extends JButton{

	/*private Panneau pan = new Panneau();
    private JPanel container = new JPanel();
    private int compteur = 0;
    private boolean animated = true;
    private boolean backX, backY;
    private int x,y ;
    private Thread t;*/  

    private ButtonGroup BoutonsGroupe = new ButtonGroup();
    
    //Création de notre barre d'outils
    private JToolBar BarreDOutil = new JToolBar();
    
    //Les boutons
    private JButton 	deplacement = new JButton(new ImageIcon("images/play.jpg")),
    					murH = new JButton(new ImageIcon("images/stop.jpg")),
    					murV= new JButton(new ImageIcon("images/carré.jpg")),
    					regleDeJeu = new JButton(new ImageIcon("images/triangle.jpg"));
    					//retourAuMenu = new JButton(new ImageIcon("images/rond.jpg")),
    					
    private Color fondBouton = Color.WHITE;
    private FormeListener fListener = new FormeListener();
    
    public Boutons() {
           
            this.setTitle("Animation");
            this.setSize(400, 300);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);

            container.setBackground(Color.white);
            container.setLayout(new BorderLayout());
            
            //On initialise le menu stop
            stop.setEnabled(false);
            //On affecte les écouteurs
            stop.addActionListener(stopAnimation);
    		launch.addActionListener(startAnimation);
            
    		//On affecte les écouteurs aux points de menus
    		rouge.addActionListener(frmColor);
    		bleu.addActionListener(frmColor);
    		vert.addActionListener(frmColor);
    		blanc.addActionListener(frmColor);
    		
    		rougeBack.addActionListener(bgColor);
    		bleuBack.addActionListener(bgColor);
    		vertBack.addActionListener(bgColor);
    		blancBack.addActionListener(bgColor);
    		//On crée et on passe l'écouteur pour afficher le menu contextuel
    		//Création d'une implémentation de MouseAdapter
    		//avec redéfinition de la méthode adéquate
            pan.addMouseListener(new MouseAdapter(){
            	public void mouseReleased(MouseEvent event){
            		//Seulement s'il s'agit d'un clic droit
            		if(event.isPopupTrigger())
            		{	            		
            			background.add(blancBack);
            			background.add(rougeBack);
	            		background.add(bleuBack);
	            		background.add(vertBack);
	            		
	            		couleur.add(blanc);
	            		couleur.add(rouge);
	            		couleur.add(bleu);
	            		couleur.add(vert);
	            			            		
	            		jpm.add(launch);
	            		jpm.add(stop);
	            		jpm.add(couleur);
	            		jpm.add(background);
	            		
	            		//La méthode qui va afficher le menu
	            		jpm.show(pan, event.getX(), event.getY());
            		}
            	}
            });
            
            container.add(pan, BorderLayout.CENTER);
            
            this.setContentPane(container);
            this.initMenu();
            this.initToolBar();
            this.setVisible(true);            
                        
    }
    

    private void initToolBar(){
    	
    	this.cancel.setEnabled(false);
    	this.cancel.addActionListener(stopAnimation);
    	this.cancel.setBackground(fondBouton);
    	this.play.addActionListener(startAnimation);
    	this.play.setBackground(fondBouton);
    	
    	this.toolBar.add(play);
    	this.toolBar.add(cancel);
    	this.toolBar.addSeparator();
    	
    	//Ajout des Listeners
    	this.circle.addActionListener(fListener);
    	this.circle.setBackground(fondBouton);
    	this.toolBar.add(circle);
    	
    	this.square.addActionListener(fListener);
    	this.square.setBackground(fondBouton);
    	this.toolBar.add(square);
    	
    	this.tri.setBackground(fondBouton);
    	this.tri.addActionListener(fListener);
    	this.toolBar.add(tri);
    	
    	this.star.setBackground(fondBouton);
    	this.star.addActionListener(fListener);
    	this.toolBar.add(star);
    	
    	this.add(toolBar, BorderLayout.NORTH);
    	
    }
    
	
    private void initMenu(){
    	//Menu animation
    	//****************************
    	
    	//Ajout du listener pour lancer l'animation
    	//ATTENTION LE LISTENER EST GLOBAL ! ! ! ! 
    	//-----------------------------------------------
    	lancer.addActionListener(startAnimation);
    	//On attribue l'accélerateur c
    	lancer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
    												KeyEvent.CTRL_MASK));
    	animation.add(lancer);
    	
    	//Ajout du listener pour arrêter l'animation
    	//LISTENER À CHANGER ICI AUSSI
    	//--------------------------------------------
    	arreter.addActionListener(stopAnimation);
    	arreter.setEnabled(false);
    	arreter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
    												KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
    	animation.add(arreter);
    	
    	animation.addSeparator();
    	quitter.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent event){
    			System.exit(0);
    		}
    	});
    	animation.add(quitter);
    	
    	//Menu forme
    	
    	bg.add(carre);
    	bg.add(triangle);
    	bg.add(rond);
    	bg.add(etoile);
    	
    	//On crée un nouvel écouteur, inutile de créer 4 instances différentes
    	
    	carre.addActionListener(fListener);
    	rond.addActionListener(fListener);
    	triangle.addActionListener(fListener);
    	etoile.addActionListener(fListener);
    	
    	typeForme.add(rond);
    	typeForme.add(carre);    	
    	typeForme.add(triangle);
    	typeForme.add(etoile);
    	
    	rond.setSelected(true);
    	
    	forme.add(typeForme);
    	
    	//Ajout du listener pour le morphing
    	morph.addActionListener(new MorphListener());
    	forme.add(morph);
    	
    	//menu à propos
    	
    	//Ajout de ce que doit faire le "?"
    	aProposItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop = new JOptionPane();
				ImageIcon img = new ImageIcon("images/cysboy.gif");
				
				String mess = "Merci ! \n J'espère que vous vous amusez bien ! \n";
				mess += "Je crois qu'il est temps d'ajouter des accélérateurs et des mnémoniques dans tout ça...\n";
				mess += "\n Allez, GO les ZérOs !";
				
				jop.showMessageDialog(null, mess, "À propos", JOptionPane.INFORMATION_MESSAGE, img);
				
			}    		    		
    	});
    	aPropos.add(aProposItem);
    	
    	//Ajout des menus dans la barre de menus
    	animation.setMnemonic('A');
    	menuBar.add(animation);
    	
    	forme.setMnemonic('F');
    	menuBar.add(forme);
    	
    	aPropos.setMnemonic('P');
    	menuBar.add(aPropos);
    	
    	//Ajout de la barre de menus sur la fenêtre
    	this.setJMenuBar(menuBar);
    }
    
	
	/**
	 * Écouteur du menu Lancer
	 * @author CHerby
	 */
	public class StartAnimationListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			
			JOptionPane jop = new JOptionPane();			
			int option = jop.showConfirmDialog(null, "Voulez-vous lancer l'animation ?", "Lancement de l'animation", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			
			if(option == JOptionPane.OK_OPTION)
			{
				lancer.setEnabled(false);
				arreter.setEnabled(true);
				
				//ON AJOUTE L'INSTRUCTION POUR LE MENU CONTEXTUEL
				//************************************************
				launch.setEnabled(false);
				stop.setEnabled(true);
				
				play.setEnabled(false);
				cancel.setEnabled(true);
				
				animated = true;
				t = new Thread(new PlayAnimation());
				t.start();			
			}
		}		
	}
	
	/**
	 * Écouteur du menu Quitter
	 * @author CHerby
	 */
	
}
