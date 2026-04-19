import java.util.ArrayList;

public class MyProgram { //// the gamegrid below, unless its like a one in a million chance, 
                        ///will never generate. as it's used to generate new grids
                        //// i figured out on april 11 what "shifting" and "reordering" in the rules meant
                        ///// didnt apply it until now because my original program geninuely sucked (1-3 seconds per gen)
                        ///// and i wanted to look cool with my "bombing board" strategy of generation
                        ///// yet somehow my program right now is still slower than the other students
                        //// it is currently april 18, i give up trying to make a program
                        /// faster than the other students and will just submitt so i can hopefully get my points
    public static int[][] gameGrid = new int[][] {{1, 2, 3, 4, 5, 6, 7, 8, 9},
                                                  {4, 5, 6, 7, 8, 9, 1, 2, 3},
                                                  {7, 8, 9, 1, 2, 3, 4, 5, 6},
                                                  {2, 3, 4, 5, 6, 7, 8, 9, 1},
                                                  {5, 6, 7, 8, 9, 1, 2, 3, 4},
                                                  {8, 9, 1, 2, 3, 4, 5, 6, 7},
                                                  {3, 4, 5, 6, 7, 8, 9, 1, 2},
                                                  {6, 7, 8, 9, 1, 2, 3, 4, 5},
                                                  {9, 1, 2, 3, 4, 5, 6, 7, 8}
                                                                                };
    
    public static int gambles = 0; //// once this hits 100 ill make it stop randomizing
    /// i cant make the total number of gambles (100) too low or its not that random
    public static void main(String[] args) {
        long time = System.nanoTime();
        backToTheFuture();
        System.out.println((System.nanoTime() - time)/1000000); // display time in miliseconds
    }
    
    public static boolean backToTheFuture() { /// my backtracking method
        int a = (int)(Math.random()*9);  /// called it back to the future because
        int b = (int)(Math.random()*9); //// "back" for backtracking and rest is a movie reference
        
        int choice = (int)(Math.random()*2);
        
        if (choice == 1) swapCols(a, b);
        else swapRows(a, b);
        
        if (checkValids(a, b)) gambles++;
        else {if (choice == 1) swapCols(a, b); else swapRows(a, b);}
        if (gambles == 100) {display(); return true;}
        return backToTheFuture();
    }
    
    public static void swapCols(int a, int b) {
        for (int i = 0; i < 9; i++) {
            int temp = gameGrid[i][a];
            gameGrid[i][a] = gameGrid[i][b];
            gameGrid[i][b] = temp;
        }
    }
    
    public static void swapRows(int a, int b) {
        int[] temp = gameGrid[b];
        gameGrid[b] = gameGrid[a];
        gameGrid[a] = temp;
    }
    
    public static int[] miniMidPoint(int i, int j) {
        // zone table below
        //   1   |   2   | 3
        // --------------------
        //   4   |   5   | 6
        // --------------------
        //   7   |   8   | 9
        
        int x = i%3;
        int y = j%3;
        
        if (x > 0) {i--; x--;} if (y > 0) {j--; y--;}
        if (x == 0) i++; if (y == 0) j++;
        return new int[] {i, j}; 
    }
    
    public static boolean checkValids(int x, int y) {
        return validsInCol(x) && validsInRow(y) && validsInMiniZone(miniMidPoint(x, y));
    }
    
    public static boolean validsInCol(int x) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) nums.add(i+1);
        for (int j = 0; j < 9; j++) {
            if (nums.contains(gameGrid[x][j])) nums.remove(nums.indexOf(gameGrid[x][j]));
            else return false;
        }
        return true;
    }
    
    public static boolean validsInRow(int y) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) nums.add(i+1);
        for (int i = 0; i < 9; i++) {
            if (nums.contains(gameGrid[i][y])) nums.remove(nums.indexOf(gameGrid[i][y]));
            else return false;
        }
        return true;
    }
    
    public static boolean validsInMiniZone(int[] loc) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) nums.add(i+1);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (nums.contains(gameGrid[loc[0]-1+i][loc[1]-1+j])) nums.remove(nums.indexOf(gameGrid[loc[0]-1+i][loc[1]-1+j]));
                else return false;
            }
        }
        return true;
    }
    
    public static void kill() {System.exit(0);}
    
    public static void display() {
        for (int i = 0; i < 9; i++) {
            if ((i+3)%3==0)System.out.println("+-------+-------+-------+");
            System.out.print("|");
            for (int j = 1; j < 10; j++) {
                System.out.print(" " + gameGrid[i][j-1]);
                if (j%3==0 && j != 0) System.out.print(" |");
            }
            System.out.println();
        }
        System.out.println("+-------+-------+-------+");
    }
}