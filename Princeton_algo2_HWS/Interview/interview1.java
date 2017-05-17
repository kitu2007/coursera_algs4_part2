import java.lang.*; 
import java.util.*; 

public class interview1{
    
    
    static void removeDuplicates(StringBuilder s){
        
        int len = s.length();
        // no need to run the function
        if(len<=1)
            return; 
        
        // str
        for(int i=0; i<s.length() ; ++i){
            char c = s.charAt(i);
            int ind = i+1;
            for(int j=i+1; j<s.length(); ++j){
                // if c is equal to jth element insert it in string  
                // otherwise skip to next element
                if( c!=s.charAt(j) ){
                    s.setCharAt(ind, s.charAt(j));
                    ++ind;     
                }
            }
            // set the last entry of string at null
            s.setLength(ind);
            // not sure if s.len is updated 
        }
        
    }
    
    static void rotate(int [][] matrix, int n) {
        for (int layer = 0; layer < n / 2; ++layer) {
            int first = layer;
            int last = n - 1 - layer;
            for(int i = first; i < last; ++i) {
                int offset = i - first;
                int top = matrix[first][i]; // save top                                                                                                
                // left -> top                                                                                                                         
                matrix[first][i] = matrix[last-offset][first];
                
                // bottom -> left                                                                                                                      
                matrix[last-offset][first] = matrix[last][last - offset];
                
                // right -> bottom                                                                                                                     
                matrix[last][last - offset] = matrix[i][last];
                
                // top -> right                                                                                                                        
                matrix[i][last] = top; // right <- saved top                                                                                           
            }
        }
    }
    
    static void printMatrix(int[][]M, int N){
        
        for(int i=0; i<N; ++i){
            for(int j=0; j<N; ++j){
                StdOut.print(M[i][j] + " ");
            }
            StdOut.print("\n");
        }
    }
    
    static void zeroset(int[][]mat, int M, int N){
//       if(mat == null) 
//           throw new java.lang.IOException();
       
        boolean [] markColumn = new boolean[N];
        for(int j=0; j<N; ++j){
            markColumn[j] = false;   
        }; 
        
        for(int i=0; i<M; ++i){
            boolean isZeroRow = false; 
            for(int j=0; j<N; ++j){
                if(mat[i][j]==0){
                   isZeroRow = true;
                   markColumn[j] = true;   
                }
            };
            if(isZeroRow){
             // make the row zero, we won't see this row again   
                for(int j=0; j<N; ++j){
                    mat[i][j] = 0;
                }
            }
        }
        
        // now set columns to zero.
        for(int j=0; j<N; ++j){
            if(markColumn[j]){
                for(int i=0; i<N; ++i){
                    mat[i][j] = 0;
                }
            }
        }
    }
    
    /* test cases:
     * 
     - bad input null string
     (a) no duplicates. avcd
     (b) simple duplicates. abcdaf
     (c) all duplicates aaaa
     (d) repeating character abcdaaadac, abcaaa
     */ 
    public static void main(String args[]){
        
        final int N = 5;
        int [][]M = new int[5][5];
        
        int id = 1;
        for(int i=0; i<N; ++i){
            for(int j=0; j<N; ++j){
                M[i][j]= id++;
            }
        };
        
        M[1][2] = 0; 
        M[1][3] = 0;
        M[3][1] = 0; 
        M[3][3] = 0; 
        
        StdOut.println("Before rotation");
        printMatrix(M,N);
        
        StdOut.println("After rotation");
        zeroset(M,N,N);
        //rotate(M,N);
        printMatrix(M,N);
        
        //In in = new In(args[0]);
        //String s = in.readString();
        StdOut.println("String before:" + args[0]);
        StringBuilder s = new StringBuilder(args[0]);        
        StdOut.println("String before:" + s);
        removeDuplicates(s);
        StdOut.println("String after:" + s);
        
    }
 
}