#include <cstdio>
#include <iostream>

void removeDuplicates( char *s)
{
        
  if(s==NULL) return; 
        
  // str
  for(int i=0; s[i]!=0 ; ++i){
    char c = s[i];
    int ind = i+1;
    for(int j=i+1; s[j]!=0; ++j){
      // if c is equal to jth element insert it in string  
      // otherwise skip to next element
      if( c!=s[j] ){
	s[ind] = s[j];
	++ind;     
      }
    }
    // set the last entry of string at null
    s[ind] = 0;
    // not sure if s.len is updated 
  }

}

static void rotate(int** matrix, int n) {
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

void printMatrix(int**M, int N){

 for(int i=0; i<N; ++i){
   for(int j=0; j<N; ++j){
     std::cout<< M[i][j] << " "; 
   }
   std::cout<< "\n"; 
 }
}

int  main(int argc, char *argv[] )
{

  int N = 5;
  int **M = new int*[5];
  for(int i=0; i<N; ++i){
    M[i] = new int[5];
  }; 

  int id = 1;
  for(int i=0; i<N; ++i){
    for(int j=0; j<N; ++j){
      M[i][j]= id++; 
    }
  }
  
  std::cout<< "before rotation:"<< std::endl;
  printMatrix(M,N);

 
  rotate(M,N);
  std::cout<< "after rotation:"<< std::endl;
  printMatrix(M,N);
 
  /* 
  std::cout<< "number of arguments:"<< argc << std::endl;

  std::cout<< "String before: " << argv[1]<< std::endl; 
  char * s = argv[1];
  removeDuplicates(s);
  std::cout<< "String after: " << s << std::endl; 
  */

  return 1;

}

