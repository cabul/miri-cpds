public class Field {

	// Usage:
	// javac *.java
	// java Field [mode]
	public static void main(String args[]) {

		boolean mode = true;
		Flag flag = new Flag();

		// Detect mode from CMD Line
		if (args.length > 0 && args[0].equals("normal")) {
			System.out.println("NORMAL MODE");
			mode = false;
		} else {
			System.out.println("GREEDY MODE");
			mode = true;
		}

		Thread a = new Neighbour(flag, mode);
		a.setName("alice");
		Thread b = new Neighbour(flag, mode);        
		b.setName("bob");

		a.start();
		b.start();

	}
}
