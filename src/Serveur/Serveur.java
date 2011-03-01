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
		
		System.out.println("Serveur démarré sur le port " + args[0]);
		Socket clientSocket = null;
		
		// Attente des clients
		while (nb < nbClients) {
			try {
				clientSocket = serverSocket.accept();
				Pipe pipe1 = new Pipe(); // Thread -> Serveur
				Pipe pipe2 = new Pipe(); // Serveur -> Thread
				threads[nb] = new Pipe(pipe1.out, pipe2.in);
				
				// Thread par client
				new ServeurThread(clientSocket, new Pipe(pipe2.out, pipe1.in)).start();
				nb++;
			} catch (IOException e) {
				System.err.println("Connexion avec le client impossible");
			}
		}
		
		String buf;
		
		// Début de partie
		try {
			while (true) {
				for (int i = 0 ; i < nbClients ; i++) {
					if (threads[i].in.ready()) {
						buf = threads[i].in.readLine();
						System.out.println("Le joueur " + i + " : " + buf);
						threads[i].out.println("echo" + buf);
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Connexion avec les threads impossible");
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class Pipe {
	public PrintWriter out;
	public BufferedReader in;
	
	/**
	 * Crée un pipe "normal" dans le sens où les extrémités sont vraiment dans le même pipe
	 */
	public Pipe() {
		try {
			PipedWriter tmp = new PipedWriter();
			out = new PrintWriter(tmp);
			in = new BufferedReader(new PipedReader(tmp));
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	/**
	 * Crée un faux pipe pour gérer un connexion bidirectionelle via 2 pipes mais seuls un des
	 * cotés de chaque pipe intéresse chaque thread 
	 */
	public Pipe(PrintWriter out, BufferedReader in) {
		this.out = out;
		this.in = in;
	}
}

/*class BiConnexion {
	// Le serveur écrit dessus et le thread lit dessus
	public Pipe pipe1 = new Pipe();
	// Le thread écrit dessus et le serveur lit dessus
	public Pipe pipe2 = new Pipe();
}*/
