/**
 * 
 * Strongly connected component 
 * 
 * Kshitiz Garg
 */
import java.io.*;
import java.util.*;

public class SCC{

    private boolean [] marked; 
    private int [] edgeTo; 
    private int [] ids; 
    private int count;
    private int numVertices; 
    Stack<Integer> reversePost; 
    
    // used to initialize basic parameters.
    // since we need to run the dfs 2 times. 
    private void initialize(){
    
        marked = new boolean[numVertices]; 
        edgeTo = new int[numVertices];
        ids = new int[numVertices];
        count = 0;
        
        reversePost = new Stack<Integer>();
        
        for(int i=0; i<numVertices; ++i){
            marked[i] = false; 
            edgeTo[i] = -1;
            ids[i] = -1;
        }
    }
    
    // constructor 
    // what if I was using another dfs which didn't support ids. 
    SCC(Digraph G){

        numVertices = G.V();
        initialize();
        
        // run the dfs on G.reverse and get postOrder.
        for(int i=0; i<G.V(); ++i){
            if(!marked[i]){
             dfs(G.reverse(),i,count);
             count +=1; 
            }
        }
       
        // make a deep copy of stack.
        List<Integer> reversePostOrder = new ArrayList<Integer>(); 
        while(reversePost.size()>0){
            reversePostOrder.add(reversePost.pop());
        }
        
        // clear the search for another run. 
        initialize(); 
        for(int v: reversePostOrder){
            if(!marked[v]){
             dfs(G,v,count);
             count +=1;
            }
        } 
    }

    public List<List<Integer>> getCC(){

        List<List<Integer>> cc = new ArrayList<List<Integer>>();
        for(int i=0; i<count; ++i){
            cc.add(new ArrayList<Integer>());     
        }
        
        for(int i=0; i<numVertices; ++i){
            int id = ids[i];
            cc.get(id).add(i);
        }
        
        return cc; 
    }
    
    public int numCC(){
     return count;    
    }
    
    private void dfs(Digraph G, int v, int id){
        //pre.add(v);
        
        marked[v] = true;
        ids[v] = id;     
        for(int w: G.adj(v)){
            // if not marked call dfs..
            if(!marked[w]){
             edgeTo[w] = v;    
             dfs(G,w,id);   
            }
        }
       //post.add(v);
       reversePost.push(v);
    }
    
    public  static void main(String args[]){
     In in = new In(args[0]);   
     Digraph G = new Digraph(in);
     SCC sc = new SCC(G); 
     List<List<Integer>> cc = sc.getCC();
     
     //print the list
     for(int i=0; i<cc.size();++i){
         StdOut.println("CC"+i);
         for(Integer v : cc.get(i))
             StdOut.print(" " + v + " ");
         StdOut.println("done");
     }
     
    }
   
    
    
    
}; 