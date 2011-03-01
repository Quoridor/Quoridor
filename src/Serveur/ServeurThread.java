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
			while(inputLine != null) {
				// Récupération de la commande du client
				this.pipe.out.println(inputLine);
				
				// Si le serveur veut écrire, on transmet son message
				String buf;
				if ((buf = pipe.in.readLine()) != null)
					out.println(buf);
				
				/*String[] args = inputLine.split(" ");
				if ((args.length > 0)) {
					try {
					int cmd = Integer.parseInt(args[0]);
												
					switch (cmd) {
					// NOM
					case(3):
						if (args.length > 1) {
							this.name = args[1];
							System.out.println(name() + " : le client donne son nom : " + this.name);
						}
						break;
					// Quitter
					case(7):
						this.pipe.out.writeln("");
							
					}
					}
					catch (NumberFormatException e) {
						System.err.println(name() + " : requête invalide !");
					}*/
					inputLine = in.readLine();
				}
			//}
			
			// Test d'écriture dans le pipe
			pipe.out.println(name() + " c'est moi !");
			String buf = pipe.in.readLine();
			System.out.println(name() + " : " + buf);
			
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
