public class Pot {

	int servings = 0;
	int capacity;

	public Pot(int capacity) {
		this.capacity = capacity;
	}
	
	public synchronized void getserving() throws InterruptedException {
		if (servings == 0) {
			System.out.println(Thread.currentThread().getName() + " go walk");			
		} else {
			--servings;
			System.out.println(Thread.currentThread().getName()+ " is served");
			print_servings();
		}
	}
	
	public synchronized void fillpot() throws InterruptedException {
		if (servings > 0) {
			System.out.println(Thread.currentThread().getName() + " go walk");
		} else {
			servings = capacity;
			System.out.println(Thread.currentThread().getName()+ " fill the pot");
			print_servings();
		}
	}
	
	private synchronized void print_servings() {
		System.out.println("servings in the pot => " + servings);
	}
	
	
/*	
	public synchronized void getserving() throws InterruptedException {
		if (servings == 0) {
			System.out.println("getserving: " + Thread.currentThread().getName() + " waits");
			wait();
		}
		--servings;
		System.out.println("servings - 1 => " + servings);
		if (servings == 0) notifyAll();
	}

	public synchronized void fillpot() throws InterruptedException {
		while (servings > 0) {
			System.out.println("fillpot: " + Thread.currentThread().getName() + " waits");
			wait();
		}
		servings = capacity;
		System.out.println("servings => " + servings);
		notifyAll();
	}
*/
	
}
