public class Scheduler {
    
    static final int COUNT = 3;
    static final int SLEEP = 500; 
    
    public static void main(String args[]){
        // create a couple of threads   
        SimpleThread threads[] = new SimpleThread[COUNT];
        for (int i = 0; i < COUNT; ++i)
            threads[i] = new SimpleThread(i+1);
        
        int index = 0;
        /*
        while (true) { 
            
          
            synchronized(threads[index]) 
            {
                //threads[index].notifyAll();
                threads[index].notify();
            };
          
            
            try {
                Thread.sleep(SLEEP);  
            } 
            catch ( InterruptedException e) { 
                throw new RuntimeException(e);
            };
            
            index = (++index) % COUNT;
        }
        */
        
    };
};

