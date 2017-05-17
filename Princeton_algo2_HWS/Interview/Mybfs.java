/**
 * Will use Breath first search
 * 
 * Kshitiz garg.
 */
import java.util.*; 

public class Mybfs{
    private final int MAX_DEGREE; 
    int [] edgeTo;
    boolean [] marked;
    int [][]maxLength;
    int [] depth;
    private final int s; 
    
   /*
    * public LongestPath(G){
        // do a breath first search first
        BreadthFirstPaths Mybfs = new BreadthFirstPaths(G,0);
        
        
    }
    */
    
    public  Mybfs(Graph G, int s){
        MAX_DEGREE = 3; 
        edgeTo = new int[G.V() ];     
        marked  = new boolean[G.V()];
        maxLength = new int[G.V()][MAX_DEGREE];
        depth = new int[G.V()];
        this.s = s; 
        
        for(int i=0; i<G.V(); ++i){
            maxLength[i] = new int[MAX_DEGREE];    
            marked[i] = false; 
            edgeTo[i] = -1; 
        }
        
        Queue<Integer> q = new Queue<Integer>(); 
        q.enqueue(s);
        int count = 0; 
        
        while(!q.isEmpty()){
            int v = q.dequeue(); 
            marked[v] = true;
            for(int w: G.adj(v)){
                if(!marked[w]){
                    if(edgeTo[w]==-1){
                        edgeTo[w] = v;
                        //maxLength[w][] =  1; 
                        depth[w] = depth[v]+1;
                    }
                    q.enqueue(w);
                }
            }
        }//
    }//Mybfs
    
    // get the nodes with maxDepth and maxDepth-1
    /* public void getLongestPath(){
        List<Integer> path = new ArrayList();
        
    }
    */
    
    public List<Integer> getPath(int v){
        if(!hasPath(v)) return null; 
        
        List<Integer> list = new ArrayList<Integer>();
        for(int x = v; x!=s; x = edgeTo[x]){
            list.add(x);
        }
        list.add(s); 
        return list; 
    }//getPath
    
    public boolean hasPath(int v){
        return marked[v];
    }//hasPath
    
    public void printPath(int v){
        List<Integer> list = getPath(v);
        for(int i: list){
            StdOut.println(i+"->");
        }
        StdOut.println("\n");
    };//printPath
    
    
    public static void main(String args[]){
     
        In in = new In(args[0]);
        Graph G = new Graph(in);
        StdOut.println(G);
        Mybfs bfs = new Mybfs(G,0);
        bfs.printPath(3);
        for(int i=0; i < G.V(); ++i){
            StdOut.println(bfs.depth[i]);
        }
        
    }
    
    
    
}