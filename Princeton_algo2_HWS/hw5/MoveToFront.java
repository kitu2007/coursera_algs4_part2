/****
  * java MoveToFront
  * 
  * 
  * KG
  */
import java.lang.Object; 
import java.io.*;

public class MoveToFront{
    private static final int CHAR_SIZE = 256; 
    //private static char ascii_index[] = new char[CHAR_SIZE];
    //private HashSet<char,int> ascii_list = new HashSet<char,int>();
    
  
    
    private static char[] initializeArray(){
        char ascii_index[] = new char[CHAR_SIZE];
        for(int i=0; i<256; ++i){
            //ascii_ind.add(Character.toChars(i),i);//how do I get these characters?
            ascii_index[i] = (char)i;
          //  StdOut.println("pos:" + i + " char:" + ascii_index[i] + " ");   
        }
        return ascii_index;
    }; 
    
    private static int find(char ascii_index[], char c) {
        
        for( int i = 0; i < CHAR_SIZE; ++i){
            if (ascii_index[i] == c)
                return i; 
        }
        
        throw new IllegalArgumentException("not valid character"); 
    }
    
    // move the item at ind to the front
    private static void moveToFront(char ascii_index[], int ind ) { 
        char c = ascii_index[ind];
        // move the index i-1 to i and continue 
        while ( ind > 0 ) {
            ascii_index[ind] = ascii_index[ind-1];    
            ind--; 
        }
        ascii_index[0] = c;
    }
    
    
    // create a symbol table which has index of 'c' to its location and an inverted map.. 
    
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode (){
        // output the 8-bit index in the sequence where c appears and move c to front.
         String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();
        
        char[] ascii_index = initializeArray();
        
        // this reads in from the stdin -- so no file
        for (int i = 0; i < input.length-1; i++) {
            char c = input[i]; 
            // find the character 
            int pos = find(ascii_index, c);
            
            // move the character to the front and adjust the array
            moveToFront(ascii_index, pos);
            
            // where does this lie in the ascii list     
            BinaryStdOut.write((char)pos);
            
        }
        BinaryStdOut.flush();        
    }
    
    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode(){
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();  
        char[] ascii_index = initializeArray();
 
        for (int i = 0; i < input.length; i++) {
            char c = input[i];
            int pos = (int) c; 
            BinaryStdOut.write(ascii_index[pos]);
            moveToFront(ascii_index, pos);
        }

        BinaryStdOut.flush();
    }
    
    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String [] args){
        // initialize the 256 ascii characters
         //String s = BinaryStdIn.readString();
        //StdOut.println("string:"+s);
        if (true) {
        // read the characters (8-bit) 
        //MoveToFront mf = new MoveToFront();
        if ( args[0].equals("-") ) {
            MoveToFront.encode();
        }
        else if (args[0].equals("+")) {
            MoveToFront.decode();
        }
        else{
            throw new IllegalArgumentException("invalid input");
        }
        }
        if (false) {
            // this reads in from the stdin -- so no file
            while (!BinaryStdIn.isEmpty()) {
                char c = BinaryStdIn.readChar();
                BinaryStdOut.write(c);
            }
            BinaryStdOut.flush();
        }
    }
    
    
}