package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServeurThread extends Thread {
	private Socket client;
	private Pipe pipe;
	private String name = null;

	public ServeurThread(Socket client, Pipe pipe) {
		this.client = client;
		this.pipe = pipe;
	}

	@Override
	public void run() {
		try {
			System.out.println(name() + " lancé...");
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			// Communication avec le client
			String inputLine = in.readLine();
			while(true) {
				// Récupération de la commande du client
				//if (in.ready()) {
					System.out.println("Lecture du client");
					this.pipe.out.println(in.readLine());
					
				//}
				synchronized(this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					System.out.println("Lecture du serveur");
					out.println(this.pipe.in.readLine());
				}
				}	
				// On transmet le message du serveur
				//if (this.pipe.in.ready()) {
					
				//}
				
				}
			//}
			//}
			
			// Test d'écriture dans le pipe
			//pipe.out.println(name() + " c'est moi !");
			//String buf = pipe.in.readLine();
			//System.out.println(name() + " : " + buf);
			
			/*String inputLine = in.readLine();
			while(inputLine!=null) {
				System.out.println(getName()+" : le client dit : "+inputLine);
				System.out.println(getName()+" : le serveur répond : "+inputLine);
				out.println(inputLine);
				inputLine = in.readLine();
			}
			out.close();
			in.close();
			client.close();*/
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(name() + " : client déconnecté");
	}
	
	public String name() {
		return this.name != null ? getName() + "(" + this.name + ")" : getName();
	}

}
