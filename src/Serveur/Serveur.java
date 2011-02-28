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
	static Pipe[] threads = new Pipe[4];
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		// Démarrage
		try {
			serverSocket = new ServerSocket(4242);
		} catch (IOException e) {
			System.err.println("Impossible d'ouvrir le port 4242");
			System.exit(-1);
		}
		
		System.out.println("Serveur démarré");
		Socket clientSocket = null;
		while (nbClients < 4) {
			try {
				clientSocket = serverSocket.accept();
				Pipe pipe1 = new Pipe();
				Pipe pipe2 = new Pipe();
				threads[nbClients] = new Pipe(pipe1.out, pipe2.in);
				new ServeurThread(clientSocket, new Pipe(pipe2.out, pipe1.in)).start();
				nbClients++;
			} catch (IOException e) {
				System.err.println("Connexion avec le client impossible");
			}
		}
		try {
			for (Pipe p : threads)
				System.out.println(p.in.readLine());
			for (Pipe p : threads)
				p.out.println("Salut petit thread");
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
