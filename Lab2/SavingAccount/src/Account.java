public class Account {
	// This account is unbounded
	private int balance = 0;

	public synchronized void deposit(int amount) {
		// More money
		balance += amount;
		System.out.println("+ $" + amount + " (" + Thread.currentThread().getName() + ")");
		notifyAll();
	}

	public synchronized void withdraw(int amount) throws InterruptedException {
		// Only withdraw if enough money left
		while (balance <= amount) {
			System.out.println("Not enough money (" + Thread.currentThread().getName() + ")");
			// Instead of waiting we could also not withdraw (-> NoWaitBanket)
			wait();
		}
		balance -= amount;
		System.out.println("- $" + amount + " (" + Thread.currentThread().getName() + ")");
	}

	public synchronized int getBalance() {
		return balance;
	}
}
