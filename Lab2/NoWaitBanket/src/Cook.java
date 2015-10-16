public class Cook extends Thread {

	private Pot pot;

	public Cook(Pot pot) {
		this.pot = pot;
	}

	public void run() {
		while (true) {
			if (Math.random() < 0.5) yield();
			try {
				sleep((int) (200 * Math.random()));
				pot.fillpot();
			} catch(InterruptedException ex) {};
		}
	}
}
