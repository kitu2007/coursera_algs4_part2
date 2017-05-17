
public MyStackList<Item>{
    Node top; 
    
    Item pop(){
        if(top==null)
            throw new java.lang.RunTimeException();

        Node prevTop = top; 
        top = top.next;
        return prevTop.val;   
    }
    
    Item push(Item i){
        Node newNode = new Node();
        newNode.val = i; 
        newNode.next = top;
        top = newNode;
    }
    
    public static void main(String args[]){
        
        
        
    }
}


public MyQueue<Item>{
    
    
    
    
    
    
    
    
}