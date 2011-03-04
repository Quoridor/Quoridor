package Serveur;

public class BoiteAuxLettres {
	protected String message = null;
	public int num;
	
	public BoiteAuxLettres(int num) {
		this.num = num;
	}

	public BoiteAuxLettres() {
	}
	
	public synchronized void setMessage(String message) {
		if (message != null) {
			while (this.message != null) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			this.message = message;
			notifyAll();
		}
		//System.out.println("Ajout de '" + message + "' à la boite " + num);
	}

	public synchronized String getMessage() {
		while (message == null) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		String resultat = message;
		message = null;
		notifyAll();
		//System.out.println("Lecture de '" + resultat + "' dans la boite " + num);
		return resultat;
	}
}
