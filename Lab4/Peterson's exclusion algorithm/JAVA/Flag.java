public class Flag {

	private boolean flag_alice;
	private boolean flag_bob;
	private int turn;
	
	public Flag() {    	
		flag_alice = false;
		flag_bob = false;
		turn = 1;
	}

	public synchronized boolean query_flag(String s) {
		//no condition synchronization is needed
		if (s.equals("alice") && turn==2 ) 
			return flag_bob;
		else if(s.equals("bob") && turn==1)
			return flag_alice ;
		else return false;
	}

	public synchronized void set_true(String s) {
		//no condition synchronization is needed
		if (s.equals("alice")) { 
			flag_alice = true;
			turn = 2;
		} else { 
			flag_bob = true; 
			turn = 1;
		}
	}

	public synchronized void set_false(String s) {
		//no condition synchronization is needed
		if (s.equals("alice")) { 
			flag_alice = false;
		} else {
			flag_bob = false; 
		}
	}

}
