/***************************************************
  Compilation: javac BoggleSolver.java
  Execution: none
  Dependencies: stdlib.jar algs4.jar
  
  *
  * Boggle Solver
  * Author KG  
  **************************************************/
import java.io.*;
import java.util.*; 
import java.lang.Object; 

public class BoggleSolver
{
    private HashSet<String> dict; // this is store valid words
    //private TST<Integer> st; // this is to find if a prefix is valid
    private TrieST<Integer> st; // this is to find if a prefix is valid
    
    // is lookup for a string faster than in HashSet?  
    
    // create boolean array of which dices have been visited 
    private class Move{
        int i; 
        int j; 
        char c; 
        Move(int _i, int _j, char _c){
            i=_i; j=_j; c=_c; 
        }; 
        
        boolean isEqual(Move _move){
            if(_move.c==c && _move.i==i && _move.j==j)
                return true;
            else
                return false;
        }
        
        public boolean equals(Object obj){
            if(obj instanceof Move)
                return isEqual((Move)obj);
            else
                return false;   
        }
        public String toString(){
            StringBuilder sb = new StringBuilder("(" + i + "," + j + "," + c + ")" + "\n");
            return sb.toString().trim();
        }
    }
    
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary)
    {
        // read the dictionary and save it as a hashmap. not sure why as a hash and not TreeMap 
        // and what will it take to search for a match.. N log N to create a RedBlackTree
        // W log N for seach in a tree. where W is avg length of string, since tree uses compareTo function
        // hashMap computes hash for a string.. and on average the lookup will be constant time. obviously computing
        // hash will take W.. so 
        // hash though doesn't support ordered operations like median, range while Tree can do that. 
        // also, we don't compare hash values of 2 strings in Tree.. we could do that.. but hash value isn't computed 
        // to respect the lexical order.. smaller string has lower hash value. may be very high number..
        // does a string has a really high hash value (based on some character order formula) and then that is moded later
        // by a prime number? 
        
        // however it seems in the problem that we need to have dictionary implemented as a trie.. so we can quickly see
        // if a character sequence is a prefix for a valid work in dictionary. if not we cut the search and try other
        // unvisited nodes in the tree. when all nodes are visited we are done. 
        
        
        // assuming dictionary doesn't have duplicates. Set won't be needed much 
        dict = new HashSet<String>(dictionary.length);
        //st = new TST<Integer>(); 
        st = new TrieST<Integer>();
        for(int i=0; i<dictionary.length;++i){
            dict.add(dictionary[i]);
            st.put(dictionary[i],i);
        }
        
    }
    
    // how to ensure that the same word hasn't been used twice or in some ways avoid cycles.. 
    // I would need to mark the position of the letter that are on the prefix and if the new move uses it again then
    // discard the move.
    // a simple way to do would be mark the letter as in dfs..
    private Stack<Move> validMoves( BoggleBoard board, Move currPos, List<Move> movesTaken){
        // validMoves is the 8 level connectness
//        if(currPos.i==3 && currPos.j==1){
//            int ddd = 22;
//        }
//        
        Stack<Move> moves = new Stack<Move>();
        for(int p=-1; p<=1; ++p){
            for(int q=-1; q<=1; ++q){
                int i_new = currPos.i+p;
                int j_new = currPos.j+q;
                // make sure it lies within the board
                if(i_new>=0 && j_new>=0 && i_new<board.rows() && j_new<board.cols() ){
                    char newC =  board.getLetter(i_new,j_new);
                    Move newMove =  new Move(i_new,j_new,newC); 
                    // check if it is not going back to parent move
                    if( !isMoveInPrefix(newMove,movesTaken) ){  //i_new !=i && j_new!=j ){
                        moves.push(newMove);
                    }
                }
            }
        }
        return moves; 
    }
    
    private boolean isMoveInPrefix(Move move, List<Move> moves){
        
        for( Move tmpMove: moves ) {
            if( move.isEqual(tmpMove) ){
                return true;
            }
        }
        return false;  
        
    }; 
    
    // this function is called 
   /*
    * private boolean isValidPrefix2(String prefix){
        // Can possibly use st.
        //Iterable<String> tmp = st.prefixMatch(prefix); 
        
     //   Iterable<String> tmp = st.keysWithPrefix(prefix);        
        //return tmp.iterator().hasNext();   
    }
    */
    
    private boolean isValidPrefix(  TrieST.Node [] currNode, char c, 
                                  String word, Set<String> validWords)
    {
        
        assert(currNode!=null);
        assert(currNode.length==1);
        TrieST.Node tmpNode; 
        if(c=='Q'){
            // st.get(currNode[0],"QU",2); 
            tmpNode = currNode[0].next[(char)('Q'-TrieST.offset)]; 
            if(tmpNode!=null)
                tmpNode = tmpNode.next[(char)('U'-TrieST.offset)];
        }
        else{
            char ind_c = (char)(c-TrieST.offset);
            tmpNode = currNode[0].next[ind_c];
        }; 
        
        if(tmpNode==null){
            //word.enqueue(c.toString());
            return false; 
        }
        else{
            currNode[0] = tmpNode;  
            if(currNode[0].val!=null && word.length()>2){
                //word.enqueue(c.toString()); 
                validWords.add(word);
            }
            
            return true;
        }   
    }
    
    private String getBoardString(char c){
        
        if(c=='Q')
            return "QU";        
        return String.valueOf(c);
        
    }; 
    
    
    
    private void searchBoard(BoggleBoard board, Move currPos, char c, String word, 
                             Set<String> validWords, 
                             List<Move> movesTaken, TrieST.Node [] currNode){ 
        
        // if the prefix is not valid abort the depth first search
        if( !isValidPrefix(currNode ,  c, word, validWords) ){
            return;   
        }
        
        // if prefix is valid explore further
        // I guess I can't allow for the word to use it 2 times and how do I keep a track of that. 
        Stack<Move> newMoves = validMoves(board,currPos,movesTaken);
        while( newMoves.size()>0 ){
            // pop the move and add it to the list of moves.
            Move newMove = newMoves.pop();
            List<Move> newMovesTaken = getAddedList(movesTaken, newMove);
            // movesTaken.add(newMove);
            String newWord = word + getBoardString(newMove.c);
            TrieST.Node [] newCurrNode = new TrieST.Node[1];
            newCurrNode[0] = currNode[0];
            searchBoard( board, newMove, newMove.c, newWord, validWords, 
                        newMovesTaken, newCurrNode); 
        }; 
        //movesTaken.remove(movesTaken.size()-1);
    }
    
    
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        
        //int i=3, j=1;
        // this needs to be a set to avoid duplicate words
        Set<String> validWords = new HashSet<String>();
        TrieST.Node [] currNode = new TrieST.Node[1]; 
        
        for(int i=0; i<board.rows(); ++i){
            for(int j=0; j<board.cols(); ++j){
                currNode[0] = st.root;
                
                // starts the search from this new position 
                char c = board.getLetter(i,j);
                // the move this can still have the 'Q' string.
                Move currPos = new Move(i,j,c);
                // the issue is with the prefix string.. we check that for in the dictionary 
                // as well as 
                String currPrefix = "" + getBoardString(c);
                
                List<Move> movesTaken = new ArrayList<Move>();
                movesTaken.add( new Move(i,j,board.getLetter(i,j) ) );
                searchBoard( board, currPos, c, currPrefix, validWords, movesTaken, currNode);
            }
        }
        
        return validWords; 
    }
    
    private List<Move> getAddedList(List<Move> moves, Move move){
        
        List<Move> newList = new ArrayList<Move>(moves.size()+1);
        for(int i=0; i< moves.size(); ++i){
            newList.add(moves.get(i));    
        }
        newList.add(move);
        return newList;
    }
    
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word)
    {
        if(word.length()<=2)
            return 0;
        else if(word.length()<=4)
            return 1; 
        else if(word.length()==5)
            return 2; 
        else if(word.length()==6)
            return 3; 
        else if(word.length()==7)
            return 5; 
        else if(word.length()>=8)
            return 11; 
        return -1;
    }
    
    // I will do couple of things to improve the timing.. first test if using 26
    // precompute the board moves will help
    // then using the R-way trie and using the redundancy..
    
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        
        if(true){
            System.out.println("Starting the test");
            long startTime = System.currentTimeMillis();
            //TODO:: how to speed this up..
            // dictionary is already in an effecient form hashSet. 
            // they compute hash for a string and then store it in a hashSet. 
            // what can be faster than that... yes every character has only 26 character. 
            // so trie will be a good format.. 
            for(int i=0; i<1000; ++i){
                BoggleBoard board = new BoggleBoard(); //args[1]
                solver.getAllValidWords(board);
            }
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime; 
            System.out.println("TotalTime (in sec):" + (float)totalTime/1000);
        }
        
        if(false){
            int score = 0;
            BoggleBoard board = new BoggleBoard(args[1]);
            for (String word : solver.getAllValidWords(board))
            {
                StdOut.println(word);
                score += solver.scoreOf(word);
            }
            StdOut.println("Score = " + score);
        }
    }
    
}
