


public class LL<Item>{
    
    private int N; 
    private Node root;
    
    private class Node{        
     private Item val;    
     private Node next;

     /*
     public Node(){
         val = 0;
         next = null; 
     }

     public Node(int _val){
         val = _val;
         next = null; 
     }
     */
    }
    
    // constructor
    public LL(){
        root = null; 
        N = 0; 
    }

    Node getnext(Node x){
        return x.next;    
    }
    
    public void append(Item val){
        Node end = new Node();
        end.val = val; 
        end.next = null;
        
        if(root==null){
            root = end;
            return;
        }
        
        Node curr = root;
        while( curr.next!=null ){
             curr = curr.next;
        }
        curr.next = end;
        N +=1;
    }
 
    // accessor method
    public Node getRoot(){
     return root;   
    }
    
    public String toString(){
     StringBuilder s = new StringBuilder();
        for(Item item: this){
         s.append(item + " ");
        }
        return s.toString();
    }
    
    public void print(){

        Node x = root;
        while(x!=null){
            StdOut.println(x.val);
            x = x.next;
        }
    }
    
    private boolean check(){
     if(N=0) (return root == null);
     if(N=1) (return (root!=null && root.next == null) );
    // if(N>1) // check if N agrees with elements. check for cycle. 
        
    }
    
    public static void main(String args[]){
        LL<Integer> linklist = new LL<Integer>(); 
        for(Integer i=0;i<5; ++i){
            linklist.append(i);            
        }
        
       linklist.print();
        
    }
    
}