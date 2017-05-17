
public class CircularSuffixArray {
    
    private final int CHAR_SIZE = 256; 
    private String s; 
    private int [] count_arr;
    int [] index; 
    
    // the string won't do anything.. 
    // if I explicitly store the suffix. then 
    public CircularSuffixArray(String _s)   // circular suffix array of s
    {
        count_arr = new int[CHAR_SIZE+1];
        s = _s; 
        index = new int[length()];
        for ( int i = 0; i < length(); ++i){
            index[i] = i;   
        };
        suffixSort();
    } 
    
    // gives the letter at the ith position for suffix with indices suffixId in original
    // suffixes. 
    char getLetter(int i, int suffixInd) {
        // index is used to find the original 
        int ind = (suffixInd + i) % length(); 
        return s.charAt(ind);
    }; 
    
    // now I will have to do a MSD sort. 
    // create a list of ascii to count the frequency of letter. 
    public int length()                   // length of s
    {
        return s.length(); 
    }
    
    
    public int index(int i)               // returns index of ith sorted suffix
    {
        return index[i];
    }
    
    public void suffixSort() {
        // doing a LSD sort.. could have also done a MSD sort.. 
        // doesn't really make too much difference.         
        char []  arr  = new char[length()];
        
        // do lsd sort start from the 
        for ( int iChar = length()-1; iChar >= 0  ; --iChar) {
            // create the array.
            for ( int suffixId = 0; suffixId < length() ; ++suffixId) {
                // get the ith letter of the original suffix given by index[i] 
                arr[suffixId] = getLetter(iChar, index[suffixId]);
            };
            countingSort(arr, index);
        };
    }
    
    // this sorts the characters and return the sorted arr and the index  
    public char[] countingSort( char [] arr, int [] index ){
        
        // create an array of ASCII's character
        int len = arr.length; 
        char [] aux = new char[len];
        int [] new_index = new int[len];
        // set the count to zer0
        for ( int i = 0; i < CHAR_SIZE; ++i) { 
            count_arr[i] = 0;    
        }
        
        // count the character frequency
        for (int i = 0; i < arr.length; ++i) { 
            // get the character 
            char c =  arr[i]; 
            count_arr[c+1] += 1;              
        };
        
        // this creates a cumulative sum
        for ( int i = 0; i < CHAR_SIZE; ++i) { 
            count_arr[i+1] +=count_arr[i]; 
        };
        
        // now read the string and put the character in the right place.
        for (int i = 0; i < arr.length; ++i) { 
            char c = arr[i];
            int insertIndex = count_arr[c];
            aux[insertIndex] = c;
            // we need the reverse map of where the sorted comes from in original order
            // index[0] = ith   not the map of where the original one (index[i] = insertIndex ended in the sorted order
            new_index[insertIndex] = index[i];   
            // increase the count 
            count_arr[c]++; 
        }
        // update the index
        for (int i = 0; i < arr.length; ++i) { 
            index[i] = new_index[i];
        } 
        return aux; 
    }
    
    
    public static void main( String [] argv) {
        String s = "ABRACADABRA!"; 
        CircularSuffixArray cs = new CircularSuffixArray(s);
        StdOut.println("String len:" + s.length() );
        for (int i = 0; i < s.length(); i++){
            StdOut.println("i:" + i + " val:"+ cs.index(i) + " ");
        }; 
        
    }
    
}; 
