/***
  * 
  * 
  * 
  * Kshitiz Garg
  */

import java.io.*;
import java.util.*; 

public class Reach{
    
    private boolean exist; 
    private int reachableVertex; 
    
    Reach(Digraph G){

        // init variables
        exist = false;
        reachableVertex = -1; 

        // do a topological sort
        List<Integer> topSort = getTopSort(G);
        if(topSort.size()<=0)
            throw new java.lang.IllegalArgumentException("no topological order exist");
        
        for(int w:topSort)
            StdOut.println("w:"+ w);
        
        // if vertex is reachable from all other vertex and doesn't contain cycle
        // it will be the last vertex in the topological sort.
        int vertex = topSort.get(topSort.size()-1);
        
        // check if this vertex connect to all the vertices by doing dfs on reversed graph
        List<Integer> sources = new ArrayList<Integer>();
        sources.add(vertex);
        
        DirectedDfs dfs = new DirectedDfs(G.reverse(),sources);
        
        // check if all vertices are reachable for vertex 
        boolean isReachable = true;
        for(int i=0; i<G.V(); ++i){
            if(!dfs.isReachable(i)){
                isReachable = false;
                StdOut.println("not reachable from:"+ vertex + "is:" + i);
                break;
            }
        }

        if (isReachable) {
         exist = true;  
         reachableVertex = vertex;
        }
    }
    
    public boolean vertexExist(){
     return exist;   
    }
    
    public int reachableVertex(){
        return reachableVertex;   
    }
    
    private List<Integer> getTopSort(Digraph G){
     
        // do a topological sort. 
        List<Integer> sources = new ArrayList<Integer>();
        for(int i=0; i<G.V(); ++i){
            sources.add(i);
        }
        DirectedDfs dfs = new DirectedDfs(G,sources); 
        return dfs.getTopologicalOrder();
        
    }
    
    public static void main(String args[]){
     In in = new In(args[0]);
     Digraph G = new Digraph(in);
     Reach reach = new Reach(G); 
     if(reach.vertexExist()){         
      StdOut.println("vertex reachable by all other vertex" + reach.reachableVertex());   
         
         
     }
     else{
      StdOut.println("no vertex exist that is reachable by all other vertex");   
     }
        
        
        
    }
    
    
    
    
}