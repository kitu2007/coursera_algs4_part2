// constructor takes a digraph (not necessarily a DAG)
import java.util.StringTokenizer; 

public class SAP {
    // don't know what to put here
    private Digraph G;
   
    public SAP(Digraph _G){
        G = new Digraph(_G);
    }
    
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        if((v<0 || v>G.V()-1) || (w<0 || w>G.V()-1)){ 
            throw new IndexOutOfBoundsException("vertices out of bounds");
        }
        Bag<Integer> vBag = new Bag<Integer>(), wBag = new Bag<Integer>();  
        vBag.add(v); wBag.add(w);
        
     return length(vBag,wBag); 

    }; 
    
    // private functions
    private boolean checkVertices(Iterable<Integer> vList){
       
        for(int v: vList){ 
            if((v<0 || v>G.V()-1)){ 
                throw new IndexOutOfBoundsException("vertices out of bounds");
            }   
        }
        return true; 
    }
    
    // the same function is called for ancestor as well as the length. 
    private int findAncestor(Iterable<Integer> v, Iterable<Integer> w, int [] minLength){
        checkVertices(v); checkVertices(w);
        // this will take E+V time
        BreadthFirstDirectedPaths bfs_v = new BreadthFirstDirectedPaths(G,v);
        BreadthFirstDirectedPaths bfs_w = new BreadthFirstDirectedPaths(G,w);
 
        // look at all the vertices in the graph to find the minLength path and the closest ancestor
        minLength[0] = Integer.MAX_VALUE;
        int ancestorNode = -1;
        
        // this should have worked but worst case time complexity is V*E
        // go through the vertices and find the path with minimum length and common ancestor
        for( int i=0; i< G.V(); ++i){
            if(bfs_v.hasPathTo(i) && bfs_w.hasPathTo(i)){
               int length = bfs_v.distTo(i)+ bfs_w.distTo(i);
               if(length<minLength[0]){
                   minLength[0] = length;
                   ancestorNode = i; 
               }
            }
        }; 
        // if no ancestor is found then minLenght is set to -1  
        if(ancestorNode == -1){
         minLength[0] = -1;
        }
        
        return ancestorNode; 
    }
    
 
    
    
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        if((v<0 || v>G.V()-1) || (w<0 || w>G.V()-1)){ 
            throw new IndexOutOfBoundsException("vertices out of bounds");
        }
        Bag<Integer> vBag = new Bag<Integer>(), wBag = new Bag<Integer>();  
        vBag.add(v); wBag.add(w);
        return ancestor(vBag,wBag); 
        //BFS or DFS on graph G with v and with w and then finding the intersection make the graph undirected.
    }
    
    

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
 
        int [] minLength  = new int[1];
        int ancestor_val = findAncestor(v,w,minLength);
        return minLength[0]; 
    }
        // a common ancestor that participates in shortest ancestral path; -1 if no such path
       public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        int [] minLength  = new int[1];
        int ancestor_val = findAncestor(v,w,minLength);
        return ancestor_val; 
       }
       
    private Bag<Integer> tokenize(String line){
        String delimiter = " ";
        StringTokenizer tokens = new StringTokenizer(line, delimiter);
        Bag<Integer> bag= new Bag<Integer>();
        while(tokens.hasMoreTokens()){
            bag.add(Integer.parseInt(tokens.nextToken()));
        }
        return bag;
    }
    
    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            //String v_line = StdIn.readInt();
            //String w_line = StdIn.readLine();
            //Bag<Integer> vBag = sap.tokenize(v_line);
            //Bag<Integer> wBag = sap.tokenize(w_line);
            //int length   = sap.length(vBag, wBag);
            //int ancestor = sap.ancestor(vBag,wBag);
            
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}