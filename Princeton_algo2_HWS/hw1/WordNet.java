import java.util.*; 
import java.io.*;

public class WordNet{
    
    // the hashmap is used to search for the noun.
    private HashMap<String,Bag<Integer> > noun_idMap; 
    private HashMap<Integer, Node > idToSynset; // create hashmap associating id with each synset 
    private Digraph G;
    private SAP sap; 
    
    private class Node{
        int id;
        Stack<String> nouns;
        String explanation;
        public Node(int _id, Stack<String> _nouns, String _explanation){
            id = _id;
            nouns = _nouns;
            explanation = _explanation;
        }
    }

    private FileInputStream safeOpen(String filename){
        FileInputStream fs = null;
         try{
            fs =  new FileInputStream(filename);
        }
        catch(Exception e){
            System.out.println(e.getClass());            
        }
        return fs;
    }

    private int getInteger(String str){        
        try{
            int id = Integer.parseInt(str);
            return id;
        } catch(NumberFormatException e){
            throw e;
        }
        
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        
        // read the synset file and create the nodes.
        noun_idMap = new HashMap<String, Bag<Integer> >(); 
        idToSynset = new HashMap<Integer, Node >();

        FileInputStream fs= safeOpen(synsets);
        Scanner  scan = new Scanner(fs);        
        String delimiter = ",";
        int maxId = -1; 
    
        // scans each line 
        while( scan.hasNextLine() ){
            String sentence = scan.nextLine();
            StringTokenizer tokens = new StringTokenizer(sentence, delimiter);
            
            //parses tokens into id, nouns and explanation
            int id = Integer.parseInt(tokens.nextToken());
            if(maxId<id){
                maxId = id;
            }
            //StdOut.printf("%d \n",id);
            String nouns_str = tokens.nextToken();
            Stack<String> nouns = getNouns(nouns_str);
            String explanation = tokens.nextToken();
            // create a hashMap linking nouns to the ids
            for(String noun : nouns){
                Bag<Integer> tmp = noun_idMap.get(noun);
                // if no entry create new one else update
                if(tmp!=null){
                    tmp.add(id);  
                }else{
                    //create a new bag of integer
                    Bag<Integer> ids = new Bag<Integer>();
                    ids.add(id);
                    noun_idMap.put(noun,ids);
                }
            }
            // add the node to a vector indexed
            Node newNode = new Node(id,nouns,explanation);
            idToSynset.put(id, newNode);
        };
        
        // create directed graph
        G = new Digraph(maxId+1);
        try{
            fs =  new FileInputStream(hypernyms);
        }
        catch(Exception e){
            System.out.println(e.getClass());
        }
        
        //TODO:: check if DAG has cycles
        // and it not rooted (one vertex that is ansector
        // of every other vertex
        
        scan = new Scanner(fs);
        while(scan.hasNextLine()){
            String sentence = scan.nextLine();
            StringTokenizer tokens = new StringTokenizer(sentence, delimiter);
            // directed edge from vertex to hypernym.
            int vertex = getInteger(tokens.nextToken());
            while(tokens.hasMoreTokens()){
                int hypernym = getInteger(tokens.nextToken());
                G.addEdge(vertex,hypernym);
            }
        }
        sap = new SAP(G);
    }
    
    private Stack<String> getNouns(String nouns_str){
        Stack<String> nouns = new Stack<String>();
        StringTokenizer tokens = new StringTokenizer(nouns_str, " ");
        while(tokens.hasMoreTokens()){
            nouns.push(tokens.nextToken());
        }
        return nouns;
    };
    
    
    // the set of nouns (no duplicates), returned as an Iterable
    public Iterable<String> nouns(){
        return noun_idMap.keySet();
    }
    
    // is the word a WordNet noun?
    public boolean isNoun(String word){
        return noun_idMap.containsKey(word);
    }
    
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        Bag<Integer> nounA_ids = noun_idMap.get(nounA);
          if(nounA_ids==null || nounA_ids.isEmpty()){
            throw new IllegalArgumentException("Could not find noun in the wordNet\n");
        };
          
      
      
        Bag<Integer> nounB_ids = noun_idMap.get(nounB);

        if(nounB_ids==null || nounB_ids.isEmpty()){
            //StdOut.printf("Could not find noun:%s in the wordNet\n",nounB);
             throw new IllegalArgumentException( "Could not find noun in the wordNet\n");
            //return fullStr; 
        }

        return sap.length(nounA_ids,nounB_ids); 
    }
    
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        String fullStr= ""; 
        Bag<Integer> nounA_ids = noun_idMap.get(nounA);
        //check if an entry exist for the noun
        if(nounA_ids==null || nounA_ids.isEmpty()){
            throw new IllegalArgumentException("Could not find noun in the wordNet\n");
        }; 
        
        Bag<Integer> nounB_ids = noun_idMap.get(nounB);
        //check if an entry exist for the noun
        if(nounB_ids==null || nounB_ids.isEmpty()){
            //StdOut.printf("Could not find noun:%s in the wordNet\n",nounB);
             throw new IllegalArgumentException( "Could not find noun in the wordNet\n");
            //return fullStr; 
        }
        
        int ancestor = sap.ancestor(nounA_ids,nounB_ids); 
        Stack<String> ancestor_noun = idToSynset.get(ancestor).nouns;
        for (String str: ancestor_noun)
            fullStr = fullStr + str + " ";
        return fullStr;
    }
    
    // for unit testing of this class
    public static void main(String[] args){
        //read the shit here. 
//        In in = new In(args[0]);
//        In hypernym = new In(args[1]);
        String synset = "synsets.txt"; //"synsets6.txt";//
        String hypernym =  "hypernyms.txt";//"hypernyms6TwoAncestors.txt"; //
        WordNet wnet = new WordNet(synset,hypernym);
        
        while (!StdIn.isEmpty()) {
            String v_line = StdIn.readLine();
           Stack<String> nouns = wnet.getNouns(v_line);

            /*for(String str: nouns){
                StdOut.printf("%s - %s\n", str, (wnet.isNoun(str)) ?"yes":"no");
            }
            */ 
           String nounA = nouns.pop(), nounB = nouns.pop();
           int length = 0;
           String ancestor = wnet.sap(nounA, nounB); 
           StdOut.printf(" ancestor of %s & %s is %s\n", nounA, nounB, ancestor);
           StdOut.printf(" distance between %s & %s is %d\n", nounA, nounB, length);

        }
    };
    
}

