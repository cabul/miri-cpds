public class Savage extends Thread {

	private Pot pot;

	public Savage(Pot pot) {
		this.pot = pot;
	}

	public void run() {
		while (true) {
			if (Math.random() < 0.5) yield();
			try {
				sleep((int) (200 * Math.random()));
				pot.getserving();
			} catch (InterruptedException e) {};
		}
	}

}
