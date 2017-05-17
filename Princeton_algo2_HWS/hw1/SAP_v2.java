// constructor takes a digraph (not necessarily a DAG)
import java.util.Iterator;

public class SAP {
    // don't know what to put here
    Digraph G;
    BreadthFirstDirectedPaths bfs;
    int ancestor; 
    
    public SAP(Digraph _G){
        G = _G.reverse();
        bfs = new BreadthFirstDirectedPaths(G,0);   
        ancestor = -1; 
    }
    
    int getlength(Iterable<Integer> iterable){
        int count=0;
        for(int i: iterable){
            ++count; 
        }
        return count; 
    }; 
    
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        int len = 0;
        int ancestor_val = ancestor(v,w);
         if(ancestor_val<0){
                return -1;
            }
         else{
               Iterable<Integer> v_s = bfs.pathTo(v);
               Iterable<Integer> w_s = bfs.pathTo(w);
               Iterable<Integer> a_s = bfs.pathTo(ancestor_val); 
               len = getlength(v_s) + getlength(w_s) - 2*getlength(a_s); 
         }; 
         
    return len; 
    }

    /*
    public int length(int v, int w){
        int source = 0; 
        BreadthFirstDirectedPaths bfs_v = new BreadthFirstDirectedPaths(G,v);
        Iterable<Integer> v_s = bfs_v.pathTo(source);
        BreadthFirstDirectedPaths bfs_w = new BreadthFirstDirectedPaths(G,w);
        Iterable<Integer> w_s = bfs_w.pathTo(source);
        
        //Set<Integer> ancestorPath = new Set<Integer>(); 
        Stack<Integer>  stack_v = new Stack<Integer>() ; 
        Stack<Integer> stack_w = new Stack<Integer>() ; 
        
        for(int x: v_s){
            stack_v.push(x);
        }
        StdOut.println();
        for(int x: w_s){
            stack_w.push(x); 
        }
        
        for(int x: stack_v){
            StdOut.print( v + "->" + x);
            StdOut.println();
        }
        
        StdOut.println();
        StdOut.println();
        
        for(int x: stack_w){
            StdOut.print( w + "->" + x);
            StdOut.println();
        }
        
        if(w_s!=null & v_s!=null){
            int v_tmp = stack_v.peek(), w_tmp = stack_w.peek();
            while(v_tmp == w_tmp){
                ancestor =  stack_v.pop(); 
                stack_w.pop();
                v_tmp = stack_v.peek(); 
                w_tmp = stack_w.peek();
            }; 
            return stack_v.size() + stack_w.size();
        } 
        return -1; 
    }; 
    */
    
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        int ancestor_val = -1;
        Iterable<Integer> v_s = bfs.pathTo(v);
        Iterable<Integer> w_s = bfs.pathTo(w);
        
        //iterate through the list to find the common ancestor
        Iterator<Integer> i = v_s.iterator(); 
        Iterator<Integer> j = w_s.iterator();
        int tmp_i = -1 ;int tmp_j = -1;
        while( i.hasNext() && j.hasNext() ){
            if( tmp_i == tmp_j ){
              ancestor_val = tmp_i; 
              tmp_i = i.next();
              tmp_j = j.next();
             }
        }; 
        return ancestor_val; 
    }; 
    
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        BreadthFirstDirectedPaths bfs_v = new BreadthFirstDirectedPaths(G,v);
        BreadthFirstDirectedPaths bfs_w = new BreadthFirstDirectedPaths(G,w);
        int source = 0; 
        Iterable<Integer> v_s = bfs_v.pathTo(source);
        Iterable<Integer> w_s = bfs_w.pathTo(source);
        
        // do the stack thing. and then compute the length and the ancestor.. 
        
        return 0;        
    }
    
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        
        return 0;
    }
    
    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}