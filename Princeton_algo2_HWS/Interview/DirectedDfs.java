import java.io.*;
import java.util.*;

public class DirectedDfs{
    // crictical review of this code. 
    // no error checking //no comments
    
    private boolean [] marked;
    private boolean [] onStack; 
    private int [] edgeTo;
    private Stack<Integer> topological_sort;
    
    private int minCycleLen;     private List<Integer> minCycle;
    private int maxCycleLen;     private List<Integer> maxCycle;
    private int numCycles; 
    
    DirectedDfs(Digraph G, List<Integer> sources) {
        // essential data for dfs
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        
        // variables for detecting cycles. 
        onStack = new boolean[G.V()];
        numCycles = 0;
        minCycleLen = 2*G.V();
        maxCycleLen = 0; 
        minCycle = new ArrayList<Integer>();
        maxCycle = new ArrayList<Integer>();        
        
        // this is for topological sort. 
        topological_sort = new Stack<Integer>(); 
        
        // initialize the arrays
        for(int i=0; i<G.V(); ++i){
            marked[i] = false;
            onStack[i] = false;
            edgeTo[i] = -1;
        }
        
        // call dfs on vertices which haven't been marked.
        for(int i=0; i<sources.size(); ++i){
            int source = sources.get(i); 
            if(!marked[source])
                dfs(G,source);
        }
        
    }
    
    // recursive dfs for a single source path
    private void dfs(Digraph G, int v){
        marked[v] = true;
        onStack[v] = true; 
        
        for(int w: G.adj(v)){
            // if not marked call dfs
            if(!marked[w]){
                edgeTo[w] = v;   
                dfs(G,w);   
            }
            else if (onStack[w] ) //if marked and onStack there is cycle
            {   
                // cycle has been detected
                Stack<Integer> tmpCycle = getCycle(w,v);                 
                // update and check if it is minCycle
                numCycles +=1; 
                
                // find mimumum length cycle.
                if( tmpCycle.size() < minCycleLen){
                    minCycleLen = tmpCycle.size();
                    minCycle.clear();
                    while(tmpCycle.size()>0){
                        minCycle.add( tmpCycle.pop() );
                    }
                }
                
                // find max length cycle. 
                Stack<Integer> tmpCycle2 = getCycle(w,v); 
                if( tmpCycle.size() > maxCycleLen){
                    maxCycleLen = tmpCycle2.size();
                    maxCycle.clear();
                    while(tmpCycle2.size()>0){
                        maxCycle.add( tmpCycle2.pop() );
                    }
                }  
            }
        }
        
        onStack[v] = false;
        topological_sort.push(v);     
    }
    
    private Stack<Integer> getCycle(int w, int v){
        Stack<Integer> tmpCycle = new Stack<Integer>(); 
        tmpCycle.push(w);
        for(int x=v; x!=w ; x = edgeTo[x]){
            tmpCycle.push(x);
        }
        tmpCycle.push(w);
        return tmpCycle;
    }

     public Stack<Integer> getReversePostOrder(){

        return topological_sort; 
    }

    
    public Stack<Integer> getTopologicalOrder(){

        // if cycle has been detected there is no topological sort possible
        // return empty list
        if(numCycles>0)
            return new Stack<Integer>();;
      
        return topological_sort; 
    }
    
    public boolean hasCycles(){
        return (numCycles>0);    
    }
    
    public int numberofCycles(){
        return numCycles;    
    }
    
    public List<Integer> minCycle(){
        return minCycle;    
    }
    
    public List<Integer> maxCycle(){
        return maxCycle;    
    }
    
    public boolean isReachable(int v){
        return marked[v];
    }
    
    public List<Integer> path(int v){
        List<Integer> tmpPath = new ArrayList<Integer>();
        Stack<Integer> stack = new Stack<Integer>();
        int x = v; 
        while(x!=-1){
            stack.push(x);
            x = edgeTo[x];
        }

        while(stack.size()>0){
         tmpPath.add(stack.pop());   
        }
        return tmpPath;
    }; 
    
    
    public static void main(String args[]){
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        StdOut.println(G);
        List<Integer> sources = new ArrayList<Integer>(); 
        for(int i=0; i<G.V(); ++i){
            sources.add(i);
        }
        DirectedDfs dfs = new DirectedDfs(G.reverse(),sources);
        
        /*
        // find SCC components.
        int count = 0;
        DirectedDfs dfs = new DirectedDfs(G.reverse(),sources);
        
        List< Stack<Integer> > cc = new ArrayList< Stack<Integer> >();   
        Stack<Integer> reversePostOrder = dfs.getReversePostOrder();
        for(int i=0; i<reversePostOrder.size(); ++i){

            int v = reversePostOrder.pop(); 

            if(cc[count].search(v)<0){
                dfs = new DirectedDfs(G,v);
                Stack<Integer> reachableVertices = dfs.getReversePostOrder(); 
                cc.add(reachableVertices);
                count +=1;
            }
        }
        
        // print the results
        for(int i=0; i< cc.size(); ++i){
            StdOut.println("Connected Component:" + i);
            for(int v: cc[i]){
                StdOut.print(" v:"+ v);
            }
        }
        */
        
        // mimimum and maximum cycles. 
        StdOut.println("Number of cycles:" + dfs.numberofCycles());
        StdOut.println("maxCycle");
        for(Integer i: dfs.maxCycle()){
            StdOut.println("i:" + i);   
        }
        StdOut.println("minCycle");
        for(Integer i: dfs.minCycle()){
            StdOut.println("i:" + i);   
        }
        
        /*
         List<Integer> top_sort = dfs.getTopologicalOrder();
         StdOut.println("Topo sort");
         for(Integer i: top_sort){
         StdOut.println("i:" + i);   
         }
         */ 
    }
    
}