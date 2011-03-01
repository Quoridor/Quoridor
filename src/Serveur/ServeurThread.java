package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServeurThread extends Thread {
	private Socket client;
	private Pipe pipe;

	public ServeurThread(Socket client, Pipe pipe) {
		this.client = client;
		this.pipe = pipe;
	}

	@Override
	public void run() {
		try {
			System.out.println(getName() + " lancé...");
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			// Communication avec le client
			String inputLine = in.readLine();
			while(inputLine != null) {
				// Récupération de la commande
				String[] args = inputLine.split(" ");
				if ((args.length > 0)) {
					try {
					int cmd = Integer.parseInt(args[0]);
												
					switch (cmd) {
					// NOM
					case(3):
						if (args.length > 1)
							System.out.println(getName() + " : le client donne son nom : " + args[1]);
					}
					}
					catch (NumberFormatException e) {
						System.err.println(getName() + " : requête invalide !");
					}
					inputLine = in.readLine();
				}
			}
			
			// Test d'écriture dans le pipe
			pipe.out.println(getName() + " c'est moi !");
			String buf = pipe.in.readLine();
			System.out.println(getName() + " : " + buf);
			
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
		System.out.println(getName() + " : client déconnecté");
	}

}
