package Serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Serveur {

	static int nbClients = 0;
	static Pipe[] threads ;
	static int nb = 0;
		
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		if (args.length != 1) {
			System.err.println("Mauvais nombre d'arguments !\n\tUsage : Serveur port");
			System.exit(-1);
		}
		// Démarrage
		try {
			serverSocket = new ServerSocket(Integer.parseInt(args[0]));
		} catch (IOException e) {
			System.err.println("Impossible d'ouvrir le port " + args[0]);
			System.exit(-1);
		}
						
		System.out.println("Serveur démarré sur le port " + args[0]);
		Socket clientSocket = null;
		
		// Salle d'attente		
		ListePartie parties = new ListePartie();
		parties.start();

		// Attente des clients
		while (true) {
			try {
				// Attente de la connexion
				clientSocket = serverSocket.accept();			
				
				// Envoi dans la salle d'attente
				parties.addJoueur(clientSocket);
			} catch (IOException e) {
				System.err.println("Connexion avec le client impossible");
			}
		}	
	}
}
class Pipe {
	public BALTimeOut client;
	public BALTimeOut serveur;
	
	public Pipe(int nb) {
		client = new BALTimeOut(nb);
		serveur = new BALTimeOut(nb);
	}
	
	public Pipe() {
		client = new BALTimeOut();
		serveur = new BALTimeOut();
	}
}