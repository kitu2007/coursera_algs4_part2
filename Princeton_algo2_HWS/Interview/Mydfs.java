/** 
 * 
 * Interview work
 */

import java.io.*; 
import java.util.*;

public class Mydfs{
      private final int MAX_DEGREE; 
    boolean [] marked; 
    int [] edgeTo;
    int [] depth;
    int [] [] childDepth;
    
    final int s; 
    int count; 
    Stack<Integer> stack; 
    boolean hasCycle;

    
    public Mydfs(Graph G, int s){
        marked = new boolean[G.V()]; 
        edgeTo = new int[G.V()];
        count = 0;
        depth = new int[G.V()]; 
        hasCycle = false; 
        this.s = s; 
        MAX_DEGREE = 3; 
        childDepth = new int[G.V()][MAX_DEGREE]; 
        
        for(int i=0; i<G.V(); ++i){
            edgeTo[i] = -1;
            depth[i] = 0;
            childDepth[i][0] = 0; 
        }; 

        // initialize stack
       stack = new Stack<Integer>();         
       dfs(G,s);
    }

    public boolean hasCycle(){
     return hasCycle;   
    }
    // v is the current u is the parent
    // recurcisve approach
    private void dfs(Graph G, int v){
        
        marked[v] = true; 
        for(int w: G.adj(v)){
            if(!marked[w]){
                edgeTo[w] = v; 
                depth[w] = depth[v]+1;             
               dfs(G,w);
            }
            else{
                int parent = edgeTo[v];
                childDepth[parent][0] = Math.max(childDepth[parent][0],childDepth[v][0] +1);
                //this is when it is backtracking
                   if((w!=edgeTo[v] )){
                    hasCycle = true;
                    StdOut.println("CycleFound:" + w);
                    printPath(v); 
                }
                   
            }
        } 
    }
    
    
    // non recursive approach
    // v is the current u is the parent
    private void dfs1(Graph G, int v){
        while( !stack.isEmpty() ){
            v = stack.pop();
            if(marked[v]){
                continue;
            }
            
            marked[v] = true;
            for(int w: G.adj(v)){
                edgeTo[w] = v;
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
        for(int x = v ; x!= this.s; x = edgeTo[x]){
         arr.add(x);
        };
        arr.add(this.s); 
        return arr;
    }
    
    public static void main(String [] args ){
        In in = new In(args[0]);
        Graph G = new Graph(in);
        StdOut.println(G);
        Mydfs my1 = new Mydfs(G,0); 
        
        //my1.printPath(5);
        StdOut.println("Depth");
        for(int i=0; i< G.V(); ++i){          
           StdOut.print( " " + my1.depth[i]);
        }
        

        StdOut.println("ChildDepth");
        for(int i=0; i< G.V(); ++i){          
           StdOut.print( " " + my1.childDepth[i][0]);
        }
//StdOut.println("hasCycle" + my1.hasCycle());
        
    }
    
}

