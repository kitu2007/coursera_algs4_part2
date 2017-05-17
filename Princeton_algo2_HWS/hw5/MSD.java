
// question is how can I improve on this algorithm. 
// what comments I can write 
// 
public class MSD{
    
    private final int R = 256; 
    private String [] aux;  // to store the aux array
    private final int M = 2; 
    MSD( ){
        
    }
    
    int getCharAt(String s, int d){ 
        if ( d < s.length())
            return s.charAt(d);
        return -1; 
    }
    
    public void sort( String [] arr){
        
         aux = new String[arr.length];
        
        sort(arr,  0, arr.length-1, 0);
        
    }
    
    private void insertionSort(String [] s, int lo, int hi, int d) {
        // indices i and j are index into the array s
        for ( int i = lo; i <= hi; ++i) {            
            for ( int j = i; j > lo; --j) {
                // if s[j] is less than s[i] than exchange
                if ( less( s[j], s[j-1], d) ) {
                    exch(s, j, j-1);
                };
            };
        };
    }; 
    
    // using natural order. 
    private boolean less(String s1, String s2, int d) {
         return  ( s1.substring(d).compareTo( s2.substring(d) ) < 0 ); 
        // return  getCharAt(s1,d) < getCharAt(s2,d); ( s1.substring(d).compareTo( s2.substring(d) ) < 0 ); 
    }
    
    // simple exchange 
    private void exch(String [] s, int i, int j) { 
    
        String tmp = s[i]; 
        s[i] = s[j]; 
        s[j] = tmp;
    }
    
    private void sort(String [] arr, int lo, int hi, int d) {
        
        if ( hi <= lo + M )
        {
         // if not many strings within the characters just use 
         // insertion sort. 
          insertionSort(arr, lo, hi, d); 
          return; 
        } 
        
        // for each character 
        int [] count = new int[R+2];
        
        // this gives the frequency count
        for (int i = lo; i <= hi; ++i) {
            int c = getCharAt(arr[i], d); 
            count[c+2] += 1;
        }; 
        
        // get the cum-sum
        for (int i = 0; i < R+1; ++i) {
            count[i+1] += count[i];
        };
        
        // distribute the keys
        // this changes the count array but all entries remain same (just shift one down)
        // count[0] = 0 count['a']=0; count['b'] = frquency(a)/ 
        // after distribute count[0] = 0; count['a'] = fre(a);
        for ( int i = lo; i <= hi; ++i) {
            int c = getCharAt(arr[i], d);
            aux[count[c+1]++] = arr[i];
        } 
        
        for ( int i = lo; i <= hi; ++i) {
            arr[i] = aux[i-lo]; // aux fills up from 0 to number of counts. 
        }
        
        // recursively calls the function for d+1 for every character
        for (int r = 0; r < R; r++){ 
            sort ( arr, lo + count[r], lo + count[r+1] -1, d+1); 
        }; 
                
    }
    
    public static void main ( String [] argv) {
        int num = 5; 
        String [] arr = new String[num]; 
        arr[0] = new String("dbcaf"); 
        arr[1] = new String("dbaaf"); 
        arr[2] = new String("aaf");  
        arr[3] = new String("xecafrttwewe"); 
        arr[4] = new String("beaf"); 
        

        StdOut.println("original arr");
        for(String s:arr)
            StdOut.println(s); 

        MSD msd = new MSD(); 
        msd.sort(arr);

        StdOut.println("sorted string arr");
        for(String s:arr)
            StdOut.println(s); 
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}