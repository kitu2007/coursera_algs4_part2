import java.util.*; 

public class myStack<Item>{
    
    Item [] arr;
    private int count; 
    
    myStack(){

        arr = (Item[]) new Object[10];    
        count = 0; 
    }
    
    void copyAndDouble(){
        Item [] tmp_arr = (Item[]) new Object[2*arr.length]; 
        for(int i=0; i<arr.length; ++i){
            tmp_arr[i] = arr[i];
        }
        arr = tmp_arr; 
    }; 
    
    void reduceArr(){                
        
        if(arr.length<=1)
            return; 

        int newLength = arr.length/2;
        Item [] tmp_arr = (Item[]) new Object[newLength]; 
        for(int i=0; i<newLength; ++i){
            tmp_arr[i] = arr[i];
        }
        arr = tmp_arr; 
    };
    
    void push(Item it){
        if(arr.length<=count){
         copyAndDouble();   
        }
        arr[++count] = it;
    }
    
    Item pop(){
        
        if(arr.length > 2*count){
            reduceArr();        
        }
        return arr[count--];  
        
    }
    
    Item peek(){
        
        return arr[count];
        
    }; 
    
    public static  void main(String args[]){
        
     myStack<Integer> stack = new myStack<Integer>(); 
     stack.push(1); stack.push(10); stack.push(3);  
        for(Integer i:stack.arr){
         StdOut.println("i "+i );
        }
        
    }
    
}
    
 