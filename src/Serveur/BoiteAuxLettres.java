package Serveur;

public class BoiteAuxLettres {
	protected String message = null;

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
		return resultat;
	}
}
