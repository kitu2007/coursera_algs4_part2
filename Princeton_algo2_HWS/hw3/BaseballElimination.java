import java.util.*; 
import java.io.*; 

// create a baseball division from given filename in format specified below
public class BaseballElimination {
  
    
    // this should be a symbol table
    private class Team{
     String name;
     Integer wins;
     Integer losses;
     Integer remainingGames;
     List<Integer> gameList; 
    
     // constructor
     public Team(String _name, Integer _wins, Integer _losses, 
                 Integer _remainingGames, List<Integer> _gameList){
      name = _name; 
      wins = _wins;
      losses = _losses; 
      remainingGames = _remainingGames;
      gameList = _gameList; 
     }
    }
    
    List<Team> teams; 
    Integer numberOfTeams;
    
public BaseballElimination(String filename){
    // read the number of teams
    // read the names of the team followed by wins losses remaining games. 
    In in = new In(filename); 
    numberOfTeams = in.readInt();
    teams = new ArrayList<Team>(numberOfTeams);

    for(int i=0; i<numberOfTeams; ++i){
        String name  = in.readString();
        Integer wins = in.readInt();
        Integer losses = in.readInt();
        Integer remainingGames = in.readInt();
        List<Integer> gameList = new ArrayList<Integer>(numberOfTeams);
        // read the table of left games with other teams
        for(int j=0; j< numberOfTeams;++j){
            Integer tmp = in.readInt();
            gameList.add(tmp);
        }
        Team tmpTeam = new Team(name,wins,losses,remainingGames,gameList);
        teams.add(tmpTeam);
    }
}

// number of teams
public  int numberOfTeams(){
    return -1;
}

// all teams
public Iterable<String> teams(){
    Stack<String> tmp = new Stack<String>(); 
return tmp; 
}
    
// number of wins for given team
public int wins(String team)                   
{ return 0; }

  // number of losses for given team
public int losses(String team)                  
{ return 0; }

   // number of remaining games for given team
public  int remaining(String team)              
{return 0;}

 // number of remaining games between team1 and team2
public int against(String team1, String team2)   
{return 0;}

 // is given team eliminated?
public boolean isEliminated(String team)             
{return true; }

// subset R of teams that eliminates given team; null if not eliminated
public Iterable<String> certificateOfElimination(String team)  
{
    return new Stack<String>();
}



public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination(args[0]);
    for (String team : division.teams()) {
        if (division.isEliminated(team)) {
            StdOut.print(team + " is eliminated by the subset R = { ");
            for (String t : division.certificateOfElimination(team))
                StdOut.print(t + " ");
            StdOut.println("}");
        }
        else {
            StdOut.println(team + " is not eliminated");
        }
    }
}

}
