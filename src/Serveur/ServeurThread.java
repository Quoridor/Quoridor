package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServeurThread extends Thread {
	private BufferedReader in;
	private PrintWriter out;
	private Pipe pipe;

	public ServeurThread(Socket client, Pipe pipe) {
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.pipe = pipe;
	}

	@Override
	public void run() {		
		System.out.println("Try");
		// Thread de lecture
		Thread lecture = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true)
					try {
						if (in.ready())
							pipe.serveur.setMessage(in.readLine());
					} catch (IOException e) {
						e.printStackTrace();
					}								
			}
		});
		
		lecture.start();
		
		// Ecriture
		System.out.println("Thread N°" + pipe.serveur.num + " lancé...");
		// Envoi des données
		while (true)			
			out.println(pipe.client.getMessage());
	}
}
