package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

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
		
		nbClients = 4;//Integer.parseInt(args[1]);
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

	
/*class Pipe {
	public PrintWriter out;
	public BufferedReader in;
	
	
	 Crée un pipe "normal" dans le sens où les extrémités sont vraiment dans le même pipe
	 
	public Pipe() {
		try {
			PipedWriter tmp = new PipedWriter();
			out = new PrintWriter(tmp);
			in = new BufferedReader(new PipedReader(tmp));
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	 Crée un faux pipe pour gérer un connexion bidirectionelle via 2 pipes mais seuls un des
	 cotés de chaque pipe intéresse chaque thread 
	 
	public Pipe(PrintWriter out, BufferedReader in) {
		this.out = out;
		this.in = in;
	}
}*/


