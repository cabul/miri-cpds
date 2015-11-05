import java.util.Stack;

public class Buffer<T> {
	private Stack<T> buffer;
	private int max;

	public Buffer(int max) {
		this.max = max;
		this.buffer = new Stack<T>();
	}

	public synchronized void produce(T o) throws InterruptedException {
		while (buffer.size() == max) wait();
		buffer.push(o);
		notifyAll();
	}

	public synchronized T consume() throws InterruptedException {
		while (buffer.size() == 0) wait();
		T o = buffer.pop();
		notifyAll();
		return o;
	}
}
