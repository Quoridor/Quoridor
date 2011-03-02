package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServeurThread extends Thread {
	private BufferedReader in;
	private PrintWriter out;
	private BALTimeOut pipe;
	private int num;

	public ServeurThread(Socket client, BALTimeOut pipe, int num) {
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.pipe = pipe;
		this.num = num;
	}

	@Override
	public void run() {
		
		
		// Thread de lecture
		Thread lecture = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true)
					try {
						pipe.setMessage(in.readLine());
					} catch (IOException e) {
						e.printStackTrace();
					}								
			}
		});
		lecture.start();
		
		// Ecriture
		System.out.println("Thread N°" + num + " lancé...");
		// Envoi des données
		while (true) {
			out.println(pipe.getMessage());			
		}
	}
}
