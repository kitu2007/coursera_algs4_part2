import java.awt.Color;
import java.awt.color.ColorSpace;
import java.util.NoSuchElementException;

public class SeamCarver {
    private static final int NUM_LINKS = 3; 
    private Picture pic; 
    private boolean isTranspose; 
    
    public SeamCarver(Picture picture)
    {
        pic = new Picture(picture);
        isTranspose = false; 
    }; 
    
    public Picture picture()                       // current picture
    {
        return pic; 
    }
    
    public int width()                         // width of current picture
    {
        return pic.width();
    }
    
    public int height()                        // height of current picture
    {
        return pic.height();
    }
    
   
    private double getColorEnergy(Color v1, Color v2){
        //get the color space
        int ch = 3; 
        ColorSpace cspace = v1.getColorSpace();
        
        // get the color values are floating point numbers (SRGB) 
        int  x1_val[] = new int[ch]; 
        x1_val[0] = v1.getRed(); x1_val[1]=v1.getGreen(); x1_val[2]=v1.getBlue();
        int  x2_val[] = new int[ch]; 
       x2_val[0] = v2.getRed(); x2_val[1]=v2.getGreen(); x2_val[2]=v2.getBlue();
         
        // todo: use integer maths
        // using getRGB; int rgb = v1.getRGB()
        
        // compute the square of the difference
        double delta_x_sq = 0; 
        for(int i=0; i<ch; ++i){
            double delta = x2_val[i]-x1_val[i];
            delta_x_sq += delta*delta; 
        } 
        return delta_x_sq; 
    };
    
    /*
     * private double getColorEnergy(Color v1, Color v2){
        //get the color space
        int ch = 3; 
        ColorSpace cspace = v1.getColorSpace();
        
        // get the color values are floating point numbers (SRGB) 
        float  x1_val[] = new float[ch]; 
        x1_val = v1.getColorComponents(cspace,x1_val);
        float  x2_val[] = new float[ch]; 
        x2_val = v2.getColorComponents(cspace,x2_val);
        
        // todo: use integer maths
        // using getRGB; int rgb = v1.getRGB()
        
        // compute the square of the difference
        double delta_x_sq = 0; 
        for(int i=0; i<ch; ++i){
            double delta = x2_val[i]-x1_val[i];
            delta_x_sq += delta*delta; 
        } 
        return delta_x_sq; 
    };
    */
    
    private double getMinimumInd(double accum_energy[], int [] ind){
       double minVal = Double.POSITIVE_INFINITY;//.MAX_VALUE; 
        for(int i=0; i<NUM_LINKS; ++i){
            if(accum_energy[i]<minVal){
                minVal = accum_energy[i];
                ind[0] = i;
            }
        };
        return minVal;
    }
    
    private void printMatrix(double [][] mat, int rows, int cols, String str){
        System.out.println(str);
        for (int y = 0; y < rows; y++)
        {
            for (int x = 0; x <cols; x++)            
            {
                char lMarker = ' ';
                char rMarker = ' ';
                System.out.printf("%c%6.4f%c ", lMarker, mat[y][x], rMarker);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    private void printPic(Picture showpic, String str){
        System.out.println(str);
        for (int y = 0; y < showpic.height(); y++)
        {
            for (int x = 0; x <showpic.width(); x++)            
            {
                char lMarker = ' ';
                char rMarker = ' ';
                Color c1 = showpic.get(x,y);
                System.out.printf("%c%d,%d,%d%c ", lMarker, c1.getRed(),c1.getGreen(),c1.getBlue(), rMarker);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    private void printIntegerMatrix(int [][] mat, int rows, int cols, String str){
        System.out.println(str);
        for (int y = 0; y < rows; y++)
        {
            for (int x = 0; x <cols; x++)            
            {
                char lMarker = ' ';
                char rMarker = ' ';
                System.out.printf("%c%d%c ", lMarker, mat[y][x], rMarker);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public double energy(int x, int y)            // energy of pixel at column x and row y
    {
        double delta_x_sq = 0, delta_y_sq =0;  
        
       //swap the x and y values so x means row instead of col 
        if(isTranspose){
            int tmp=x; 
            x=y; 
            y=tmp;
        }
        
        if(x<0 || x>pic.width()-1 || y<0 || y>pic.height()-1 ) 
            throw new java.lang.IndexOutOfBoundsException();
       
        // this is for the border pixels
        if( (x==0) || (x==(pic.width()-1)) ) {
            delta_x_sq = 3*255*255;
            delta_y_sq = 0;
        }
        else if( (y==0) || (y==(pic.height()-1)) ){
             delta_x_sq = 0;
            delta_y_sq = 3*255*255;
        } 
        else{
            Color v_x1 = pic.get(x-1,y);   
            Color v_x2 = pic.get(x+1,y);
            delta_x_sq =  getColorEnergy(v_x1,v_x2);
            
            Color v_y1 = pic.get(x,y-1);
            Color v_y2 = pic.get(x,y+1);
            delta_y_sq =  getColorEnergy(v_y1,v_y2);
        } 
        
        return delta_x_sq + delta_y_sq;
    };
    
    
    public int[] findHorizontalSeam()            // sequence of indices for horizontal seam
    {
        // transponse 
        isTranspose = true; 
        int [] tmp = findVerticalSeam();
        isTranspose = false;
        return tmp;
        
    }
    
    
    
    private double getShortestEnergy(double [][] shortestEnergy, int x, int y, int rows, int cols)// i is row, j is col
    {
        if(x<0 || x> cols-1 || y<0 || y>rows-1) 
            return Double.POSITIVE_INFINITY; 
        return shortestEnergy[y][x];
    }
    
    
    public int[] findVerticalSeam()              // sequence of indices for vertical seam
    {
        //this is the main function I want to handle..
        // there is energy function.. this is topological in the sense that for 
        // row 2 you can only come from row1 and only from pixels left, top and right
        // so we only need to check for those and return the one with the smallest. 
        int rows = height(); int cols = width();
        // values contain relative path to parent node (-1,0,1)
        if(isTranspose){
            rows = width(); cols = height();
        }; 
        
        int [][] parentNode = new int[rows][cols];
        // the shortest Energy at a pixel going top to down
        double[][] shortestEnergy = new double[rows][cols];
        // tmp array for keeping energy of left, above or right parent node
        double [] accum_energy = new double[NUM_LINKS]; 
        
        for(int x=0; x<cols; ++x){
            shortestEnergy[0][x] =energy(x,0);
        }
        
        // percolate down updating the shortestEnergy at every pixel
        for(int y=1; y<rows; ++y){ 
            for(int x=0; x<cols; ++x){
                accum_energy[0] = getShortestEnergy(shortestEnergy, x-1, y-1,rows,cols) + energy(x,y); 
                accum_energy[1] = getShortestEnergy(shortestEnergy, x  , y-1,rows,cols) + energy(x,y);
                accum_energy[2] = getShortestEnergy(shortestEnergy, x+1, y-1,rows,cols) + energy(x,y);
                int [] ind = new int[1];
                ind[0] = -1;
                double minEnergy = getMinimumInd(accum_energy,ind);
                assert(ind[0] >= 0 && ind[0]<=2);
                parentNode[y][x]= ind[0]-1 ; 
                shortestEnergy[y][x] = minEnergy;
            }
        };
        
        //print shortestEnergy. 
        //printMatrix(shortestEnergy, rows,cols,"shortestEnergy"); 
        
        //printIntegerMatrix(parentNode, rows,cols, "parentNode");
        
        // once this is done find the shortest Energy column in the end row
        double minEnergyLastRow = Double.POSITIVE_INFINITY;
        int minColLastRow = 0; 
        for(int x=0; x<cols; ++x){
            if(shortestEnergy[rows-1][x] < minEnergyLastRow){
                minEnergyLastRow = shortestEnergy[rows-1][x];
                minColLastRow = x; 
            }
        }
        
        // the shortest path can be computed by looking backward from the 
        // column. 
        int seamPath[] = new int[rows];
        seamPath[rows-1] = minColLastRow;
        for(int y=rows-2; y>=0; --y){
            // who is the parent of the minCol in terms of left up or right
            int minCol = seamPath[y+1]; 
            int parentOffset = parentNode[y+1][minCol];
            // add the value to the minCol to get the minimum in above row 
            seamPath[y] = minCol + parentOffset;
        }
        
        return seamPath; 
    }; 
    
    
    
    public    void removeHorizontalSeam(int[] a)   // remove horizontal seam from picture
    {
        // given the seam remove it.. 
        // should be easy 
        isTranspose = true; 
        removeVerticalSeam(a);
        isTranspose = false; 
        
        
    }
    
    public void removeVerticalSeam(int[] a)     // remove vertical seam from picture
    {
        
        int rows = pic.height(); 
        int cols =  pic.width(); 
        
        // TODO:: check if a is valid.
        for(int i=0; i<a.length-1; ++i){
           int diff = Math.abs(a[i]-a[i+1]);
           if(diff>1){
                throw new java.lang.IllegalArgumentException("invalid seam values: not adjacent"); 
           }
        }
        
        
        
        if(isTranspose){
            // ensure seam lenght is correct
            if(a.length != cols) 
                throw new java.lang.IllegalArgumentException("length of seam:" + a.length + 
                                                             " not equal to the image width:"+ cols); 
            for(int i=0; i<a.length; ++i){
                   if( a[i]<0 && a[i]>rows-1 ){
                       throw new java.lang.IllegalArgumentException("invalid seam values: out of bounds"); 
                   }
               }
            
            // reduce row to row-1 (horizontal seam is reduced)
            rows = rows -1;
        }
        else{
            
            // ensure seam length is equal to height
           if(a.length != rows) 
                throw new java.lang.IllegalArgumentException("length of seam:" + a.length + 
                                                             " not equal to the image height:"+rows); 

               for(int i=0; i<a.length; ++i){
                   if( a[i]<0 && a[i]>cols-1 ){
                       throw new java.lang.IllegalArgumentException("invalid seam values:invalid seam values: out of bounds"); 
                   }
               }
            
            // reduce col to cols-1 (horizontal seam is reduced)
            cols = cols -1;
        }
        
        // the new pic is 1 col or 1 row reduced 
        Picture newPic = new Picture(cols,rows);
        Color cl; 
        for(int y=0; y<rows; ++y){ 
            for(int x=0; x<cols; ++x){
                
                if(isTranspose)
                {
                    // horizontal seam -- transpose (row removal that is y axis)
                    if(y>=a[x]){ // a is equal to number of colums
                        cl = pic.get(x,y+1);
                    }
                    else{
                        cl = pic.get(x,y);
                    }                   
                }
                else{
                   // vertical seam ( column removal that is x axis)
                    if(x>=a[y]){
                        // skip a column if x>=a[y]
                        cl = pic.get(x+1,y);
                    }
                    else{
                        cl = pic.get(x,y);
                    } 
                }
                //set the color 
                newPic.set(x,y,cl);
            }
        }
        pic = newPic;
    }
    
    
    /*
     public void removeVerticalSeam(int[] a)     // remove vertical seam from picture
     {
     // this got a little too complicated a logic it works but it is really esoteric
     // given the seam remove it.. 
     // should be easy
     
     // a has to be size of the image height
     
     int rows = pic.height(); int cols =  pic.width(); 
     if(isTranspose){
     rows = rows -1;
     assert(a.length == pic.width()); 
     
     }
     else{
     cols = cols -1;   
     assert(a.length == pic.height()); 
     }
     
     // this is slightly complicated.. but you can traverse in cols order by
     // swapping (xn,yn)=(y,x); Also need to swap rows and cols otherwise you 
     // will get index out of bound issue since pic maintain the (rows,cols)
     
     
     Picture newPic = new Picture(cols,rows);
     // the pic structure remains the same just the way we traverse is different
     if(isTranspose){
     int tmp = rows;
     rows = cols; 
     cols = tmp; 
     }
     
     Color cl; 
     int px,py;
     for(int y=0; y<rows; ++y){ 
     for(int x=0; x<cols; ++x){
     System.out.printf("row:%d col:%d\n",y,x);
     // too complicated a logic .. there is a simple one here. 
     // this got a little complicated. 
     // this transpose is to ensure that it is traversing vertically.. 
     // is it really needed. 
     // does the same thing but for jumps y
     if(isTranspose){
     px=y; 
     py=x; 
     } 
     else{
     px=x;
     py=y;
     }
     
     if(isTranspose){
     if(py>=a[px])
     cl = pic.get(px,py+1) ;
     else
     cl = pic.get(px,py);    
     }
     else{
     if(px>=a[py])
     cl = pic.get(px+1,py) ;
     else
     cl = pic.get(px,py);
     }
     newPic.set(px,py, cl );   
     
     
     }
     }
     pic = newPic; 
     }
     */
    
}