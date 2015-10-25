public class Field {
    
    public static void main(String args[]) {

    	Flag flag = new Flag();
    
        Thread a = new Neighbour(flag);
        a.setName("alice");
        Thread b = new Neighbour(flag);        
        b.setName("bob");
        
        a.start();
        b.start();
                    
    }
}