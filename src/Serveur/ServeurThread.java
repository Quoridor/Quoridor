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

			// Test d'écriture dans le pipe
			for (int i = 0 ; i < 10 ; i++) {
				pipe.out.println(getName() + " c'est moi pour la " + i + " fois");
				String buf = pipe.in.readLine();
				
				System.out.println(getName() + " : " + buf);
			}
			
			
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
