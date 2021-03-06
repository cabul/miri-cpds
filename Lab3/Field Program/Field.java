public class Field {

	// Usage:
	// javac *.java
	// java Field [mode]
	public static void main(String args[]) {

		boolean mode;
		Flag flag = new Flag();

		// Detect mode from CMD Line
		if (args.length > 0 && args[0].equals("greedy")) {
			System.out.println("GREEDY MODE");
			mode = true;
		} else {
			System.out.println("NORMAL MODE");
			mode = false;
		}

		Thread a = new Neighbour(flag, mode);
		a.setName("alice");
		Thread b = new Neighbour(flag, mode);        
		b.setName("bob");

		a.start();
		b.start();

	}
}
