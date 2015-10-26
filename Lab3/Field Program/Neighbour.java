public class Neighbour extends Thread {

	private Flag flag;
	private boolean greedy;

	public Neighbour(Flag flag, boolean greedy) {
		this.flag = flag;
		this.greedy = greedy;
	}

	public void run() {

		while (true) {
			try {
				String name = Thread.currentThread().getName() ;
				System.out.println("try again, my name is: "+ name);
			
				if (this.greedy) {
					flag.set_true(name);               
					Thread.sleep((int)(200*Math.random()));
				} else {
					Thread.sleep((int)(200*Math.random()));
					flag.set_true(name);               
				}

				if (!flag.query_flag(name) ) {
					System.out.println(name + " enter");
					Thread.sleep(400);
					System.out.println(name + " exits");
				}

				Thread.sleep((int)(200*Math.random()));
				flag.set_false(name); 
			}
			catch (InterruptedException e) {};
		}
	}

}
