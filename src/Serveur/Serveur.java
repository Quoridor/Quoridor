package Serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Serveur {

	static int nbClients = 0;
	static Pipe[] threads ;
	static int nb = 0;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		if (args.length != 2) {
			System.err.println("Mauvais nombre d'arguments !\n\tUsage : serveur port nbJoueurs");
			System.exit(-1);
		}
		// Démarrage
		try {
			serverSocket = new ServerSocket(Integer.parseInt(args[0]));
		} catch (IOException e) {
			System.err.println("Impossible d'ouvrir le port " + args[0]);
			System.exit(-1);
		}
		
		nbClients = Integer.parseInt(args[1]);
		threads = new Pipe[nbClients];
		
		System.out.println("Serveur démarré sur le port " + args[0] + " pour " + nbClients + " clients");
		Socket clientSocket = null;
		
		Partie partie = new Partie(nbClients);
		
		// Attente des clients
		while (nb < nbClients) {
			try {
				clientSocket = serverSocket.accept();
				partie.addClient(clientSocket);
				nb++;
			} catch (IOException e) {
				System.err.println("Connexion avec le client impossible");
			}
		}
		while(true);		
	}
}
class Pipe {
	public BALTimeOut client;
	public BALTimeOut serveur;
	
	public Pipe(int nb) {
		client = new BALTimeOut(nb);
		serveur = new BALTimeOut(nb);
	}
}