/** 
 * 
 * Interview work
 */

import java.io.*; 
import java.util.*;

public class Mydfs{
    boolean [] marked; 
    int [] edgeTo; 
    int count; 
    Stack<Integer> stack; 
    boolean hasCycle;

    
    public Mydfs(Graph G){
        marked = new boolean[G.V()]; 
        edgeTo = new int[G.V()];
        count = 0; 
        hasCycle = false; 

        
        for(int i=0; i<G.V(); ++i){
            edgeTo[i] = -1; 
        }; 

        // initialize stack
        stack = new Stack<Integer>(); 
        
        //for(int i=0; i<G.V(); ++i){
          //  stack.push(i);
            dfs(G,9,-1);
            //count++;
        //}
    }

    public boolean hasCycle(){
     return hasCycle;   
    }
    // v is the current u is the parent
    // recurcisve approach
    private void dfs(Graph G, int v, int u){
        
        //if(v==null) return; 
        if(marked[v]) {
            return;
        }
        marked[v] = true;
        edgeTo[v] = u; 
 
        for(int w: G.adj(v)){
            if(marked[w] && (w!=u && u!=-1)){
                hasCycle = true;
                StdOut.println("CycleFound:" + w);
                printPath(v); 
            }
            dfs(G,w,v);      
        } 
    }
    
    
    // non recursive approach
    // v is the current u is the parent
    private void dfs1(Graph G, int v, int u){
        while( !stack.isEmpty() ){
            v = stack.pop();

            if(marked[v]){
                continue;
            }
            
            marked[v] = true;
            edgeTo[v] = u;
            u = v; // now v is the parent;
            for(int w: G.adj(u)){
                stack.push(w);
            }        
        }
    }
    
    public void printPath(int v){
          List<Integer> list = getPath(v);
          for(Integer item:list)
            StdOut.print(item + " ");
          StdOut.println("\n");
    }
    
    public List<Integer> getPath(int v){
        List<Integer> arr = new ArrayList<Integer>();
        arr.add(v);
        int parent = edgeTo[v];
        while(parent!=-1){
         arr.add(parent);
         parent = edgeTo[parent];
        };
        
        return arr;
    }
    
    public static void main(String [] args ){
        In in = new In(args[0]);
        Graph G = new Graph(in);
        StdOut.println(G);
        Mydfs my1 = new Mydfs(G); 
        
        //my1.printPath(12);
        
        
        //StdOut.println("hasCycle" + my1.hasCycle());
        
    }
    
}

