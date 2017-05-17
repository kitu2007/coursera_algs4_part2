/*
 * javac HamiltonianPath
 * 
 * Given a directed acyclic graph find hamiltonian cycle
 * 
 */
import java.io.*;
import java.util.*; 

public class HamiltonianPath{
    private boolean exist; 
    private List<Integer> topOrder; 
    
    HamiltonianPath(Digraph G){
        // get the set of vertices
        List<Integer> sources = new ArrayList<Integer>();
        for(int i=0; i<G.V(); ++i){
            sources.add(i);
        }
        //run a directed search
        DirectedDfs dfs = new DirectedDfs(G,sources);
        topOrder = dfs.getTopologicalOrder();
        
        if(topOrder.size()<=0){
            throw new java.lang.IllegalArgumentException("Graph contains cycle");   
        }
        
        if( topOrder.size() != G.V()){
            throw new java.lang.IllegalArgumentException("Graph is not connected");  
        }
        
        exist = isHamiltonianPath( G, topOrder); 
    }
    
    private boolean isHamiltonianPath(Digraph G, List<Integer> topOrder){
        
        for(int i=0; i< topOrder.size()-1; ++i){
            boolean found = false;
            int u = topOrder.get(i);
            int v = topOrder.get(i+1);
            for( int w: G.adj(u)){
                if(w == v){
                    found = true; 
                }    
            }
            // if for any consecutive vertices edge doesn't exist then no hamitonian path exist
            if(!found){
                return false;
            }
        }
        return true;
    }
    
    public boolean hamiltonianExist(){
        return exist;
    }
    
    public List<Integer> path(){
        if(exist)
            return topOrder;
        else
            return new ArrayList<Integer>();
    }
    
    public static void main(String args[]){
        
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        HamiltonianPath hp = new HamiltonianPath(G);
        List<Integer> hp_path = hp.path();
        if(hp_path.size()>0){
            StdOut.println("hamiltonianPath");
            for(int i: hp_path)
                StdOut.println(i);
        }
        else{
               StdOut.println("no hamiltonianPath exist");
        }
    }
    
}
