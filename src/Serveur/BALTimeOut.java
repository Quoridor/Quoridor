package Serveur;


public class BALTimeOut extends BoiteAuxLettres {
	
	public BALTimeOut(int num) {
		super(num);
	}

	public synchronized boolean trySetMessage(String message, long timeout) {
		if (message != null) {
			if (this.message != null) {
				try {
					wait(timeout);
				} catch (InterruptedException e) {
				}
				if (this.message == null) {
					this.message = message;
					notifyAll();
					return true;
				}
			}
		}
		return false;
	}

	public synchronized String tryGetMessage(long timeout) {
		if (message == null) {
			try {
				wait(timeout);
			} catch (InterruptedException e) {
			}
		}
		String resultat = message;
		if (message != null) {
			message = null;
			notifyAll();
		} 
		return resultat;
	}

}
