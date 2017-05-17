import java.util.*; 
import java.io.*;

public  class Outcast{
    private WordNet wnet;
// constructor takes a WordNet object
    public Outcast(WordNet wordnet){
        wnet = wordnet; 
    }
    
// given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns){
        int distMax = -1;
        String outcast_noun="";
        int size = nouns.length; 
        // for each noun check the distance
        for(int i=0; i<size; ++i){
            int dist = 0;  
            for(int j=0; j<size; ++j){
                if(i!=j){
                    dist += wnet.distance(nouns[i], nouns[j]);
                };
            }
            
            if(dist>distMax){
                distMax = dist; 
             outcast_noun = nouns[i];    
            }
        }
        return outcast_noun;
    }
    
// for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            String[] nouns = In.readStrings(args[t]);
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
    
}