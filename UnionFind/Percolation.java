import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// PREV: Used virtual sites to test if system percolates. It works 
// TODO: isFull actually checks the current site with the virtual top site. Implement this.

public class Percolation {
    public int[][] grid;
    int i, j;
    int size;
    int wu[];
    WeightedQuickUnionUF wqu;
    int openSites[];

    private void xyTo1D() {
        int k = 1;
        wu= new int[size*size + 2];
        wu[0] = 0;
        wu[wu.length-1] = wu.length-1;
        System.out.println("WU " + (size*size +2) );
        for (i=0; i<grid.length; i++){
            for (j=0; j<grid[i].length; j++){        
                wu[k] = grid[i][j];
                System.out.print("k: " + k + " " + wu[k] + "| "  );
                k+=1;
            }
        }
        System.out.println(wu[0] + " - " + wu[wu.length-1]);
        wqu = new WeightedQuickUnionUF(wu.length);
        for (i=1; i<grid.length+1; i++){
            wqu.union(wu[0], wu[i]);
        }

        for (i=wu.length-2; i>((wu.length-2) - grid.length); i--){
            wqu.union(wu[wu.length-1], wu[i]);
        }
        openSites = wu;
        for (i=0; i<openSites.length-1; i++){
            openSites[i] = 0;
        }
    }

    private int find1dIndex(int row, int col) {
        return (row * grid.length + col);
    }

    private boolean validateIndex(int index) {
        if (0 <= index && index <= wu.length) {
            return true;
        };
        return false;
    }

    public Percolation(int n) {
        size = n;
        int k=0;
        // create n-by-n grid, with all sites blocked
        grid = new int[n][n];
        for (i=0; i<grid.length; i++){
            for (j=0; j<grid[i].length; j++){
                grid[i][j] = ++k;
            }
        }
        // create private method to go from 2d array to 1d array
        xyTo1D();
    }

    private void printGrid() {
        System.out.println("\n");
        for (i=0; i<grid.length; i++){
            for (j=0; j<grid[i].length; j++){
                System.out.print(grid[i][j] + "\t");
            }
            System.out.println();
        }
    }

    private void findAdjacentAndOpenSite(String adj, int row, int col){
        // TODO: convert the coords here to 1d and then return those
        //       they can be used to determine if sites are open
        int index = find1dIndex(row, col);
        // TODO: refactor to merge if statements 
        switch (adj){
            case "up":
                if ( validateIndex(row-1)) {
                    int up = find1dIndex(row-1, col);
                    if (isOpen(row-1, col)) {
                        System.out.println("isAdjacent - up ");
                        wqu.union(index+1, up+1);
                    }
                }
                break;
            case "down":
                if ( validateIndex(row+1)) {
                    //down
                    int down = find1dIndex(row+1, col);
                    if (isOpen(row+1, col)) {
                        wqu.union(index+1, down+1);
                    }
                } 
                break;
            case "left":
                if ( validateIndex(col-1)) {
                    //left
                    int left = find1dIndex(row, col-1);
                    if (isOpen(row, col-1)) {
                        wqu.union(index+1, left+1);
                    }
                }
                break;
            case "right":
                if ( validateIndex(col+1)) {
                    //right
                    int right = find1dIndex(row, col+1);
                    if (isOpen(row, col+1)) {
                        wqu.union(index+1, right+1);
                    }
                }
                break;
        }
    }

    public void open(int row, int col)    {
        // open site (row, col) if it is not open already
        // weighted union - union function
        // 3 things: 
            // -1-. Validate indices
            // 2. Mark the site as open
            // 3. Open site in question to it's open neighbours
        
        if (!validateIndex(row)) {
            System.out.println(row + " : row index is out of range!");
        };

        if (!validateIndex(col)) {
            System.out.println(col + " : col index is out of range!");
        };
        int index = find1dIndex(row, col);
        openSites[index] = 1;

        findAdjacentAndOpenSite("up", row, col);
        findAdjacentAndOpenSite("down", row, col);
        findAdjacentAndOpenSite("left", row, col);
        findAdjacentAndOpenSite("right", row, col);
        System.out.println("\n");
    }

    public boolean isOpen(int row, int col)  {
        int index = find1dIndex(row, col);
        return openSites[index] == 1 ? true : false;
    }
    public boolean isFull(int row, int col)  {
        // is the site connected to the virtual top site?
        return wqu.connected(row, col);
    }
    public int numberOfOpenSites()  { 
        return wqu.count();
    }
    public boolean percolates()  {
        // does the system percolate?
        if (this.isFull(wu[0], wu[wu.length-1])) {
            return true;
        }
        return false;
    }
 
    public static void main(String[] args) {
        // test client (optional)
        Percolation percolation = new Percolation(3);
        percolation.open(1, 0);
        System.out.println("is wqu connected?  " + percolation.wqu.connected(3, 0));
        percolation.open(0,0); 
        percolation.open(1,0); 
        percolation.open(2,0); 
        System.out.println("is site open?  " + percolation.openSites[0]);
        System.out.println("does system percolate? " + percolation.percolates());
    }
 }