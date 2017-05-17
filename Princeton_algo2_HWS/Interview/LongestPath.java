/*
 * 
 * Kshitiz Garg
 */
import java.util.*;

public class LongestPath{
    List<Integer> longestPath; 
    public LongestPath(Graph G ){
        
        // do a depth first search
        Mydfs dfs = new Mydfs(G,0); 
        int maxDepthVertex = findMaxDepthVertex(G,dfs); 
        
        //get the vertex with maximum depth
        dfs = new Mydfs(G,maxDepthVertex);
        maxDepthVertex = findMaxDepthVertex(G,dfs);
        longestPath = dfs.getPath(maxDepthVertex);
    }; 
    
    private int findMaxDepthVertex(Graph G, Mydfs dfs){
        int maxDepth = -1; 
        int deepestVertex = -1; 
        for(int i=0; i<G.V(); ++i){
            if(dfs.depth[i]>maxDepth){
                maxDepth = dfs.depth[i];
                deepestVertex = i; 
            }
        }; 
        return deepestVertex;
    }
    
    public int center(){
        int length = longestPath.size();
        Integer mid = Math.round((length+1)/2);
        return longestPath.get(mid);
    }
    
    public void printPath(){
        StdOut.println("\n");
        for(int i:longestPath)
            StdOut.print(i+" ");
        StdOut.println("\n");
        
    }
    
    
    public static void main(String args[]){
        
        In in = new In(args[0]);   
        Graph G = new Graph(in); 
        
        LongestPath lp = new LongestPath(G);
        lp.printPath();
        
    }
}