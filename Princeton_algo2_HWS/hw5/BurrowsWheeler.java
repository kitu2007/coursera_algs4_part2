public class BurrowsWheeler { 
    
    public static void encode(){
        // get the string from the binary
        String s = BinaryStdIn.readString();
       // StdOut.println("string:" + s); 
       // StdOut.println("string len" + s.length() );
        
        CircularSuffixArray cs = new CircularSuffixArray(s.substring(0,s.length()-1));
//        StdOut.println("cs len" + cs.length() );
        
        for (int i = 0; i < cs.length(); ++i) {
            if (cs.index[i] == 0) {
                StdOut.println(i);
                break;
            };
        }
        BinaryStdOut.flush();
                
        for (int i = 0; i < cs.length(); ++i) {
            int orig_ind = cs.index(i) -1;
            if (orig_ind < 0 )
                orig_ind = orig_ind + cs.length(); 
            BinaryStdOut.write(s.charAt(orig_ind));
        }
        BinaryStdOut.flush();
       // MoveToFront.encode();
        
        
    }; 
    
    // does the sort on the character array and also get the next array
    private static void sort(char [] arr, int []next) {
        
        int R = 256; 
        int  [] count = new int[R+1];
        char [] aux = new char[arr.length];
        
        // initialze count to 0 
        for( int i = 0; i < R; ++i) { 
            count[i] = 0; 
        } 
        
        // count the frequency
        for (int i = 0; i< arr.length; ++i){
            int c = arr[i];
            count[c+1] +=1;
        }

        // sum
        for (int i = 0; i < R; ++i){
            count[i+1] += count[i];
        }; 
        
        // distribute
        for (int i = 0; i < arr.length; ++i){
            char c = arr[i]; 
            aux[count[c]] = c;
            next[count[c]] = i;
            count[c]++;
        }; 

        // copy back
        for (int i = 0; i < arr.length; ++i){
            arr[i] = aux[i];
        }; 
        
    }
    
    public static void decode() {
        
        // now in the decode the main job is to get the next() array. 
        // say you got the two input 
        int startInd = 3; 
        String s = "ARD!RCAAAABB"; 
        
        // will have to sort the arr and using counting sort;
        int [] next = new int[s.length()];
        char [] arr = new char[s.length()];
        StdOut.println("array before sorting");
        for ( int i = 0; i < s.length(); ++i){
            arr[i] = s.charAt(i);
            StdOut.print( " " + arr[i] + " ");
        };
        
        // next gives the indices of the subsequent letter
        sort(arr,next); 
        StdOut.println("\n array after sorting\n");
        for ( int i = 0; i < arr.length; ++i){
            StdOut.println( " " + arr[i] + " " + next[i]);
        };

        int ind = startInd;
        for ( int i = 0; i < s.length(); ++i) {
            BinaryStdOut.write(s.charAt(next[ind]));
            ind = next[ind];
        }
        BinaryStdOut.flush(); 
        
    };
    
    
    public static void main(String[] args) {
            if ( args[0].equals("-") ) {
                BurrowsWheeler.encode();
            }
            else if (args[0].equals("+")) {
                BurrowsWheeler.decode();
            }
            else{
                throw new IllegalArgumentException("invalid input");
            }
        
    };
    
    
};