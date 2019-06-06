import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// PREV: Refactored and organised code
// TODO: Read sites from file to generate the grid and test it
// TODO: Use visualizer client 

public class Percolation {
    public int[][] grid;
    int i, j;
    int size;
    int wu[];
    WeightedQuickUnionUF wqu;
    int openSites[];

    private void create1D() {
        int k = 1;
        wu = new int[size*size + 2];
        wu[0] = 0;
        wu[wu.length-1] = wu.length-1;

        for (i=0; i<grid.length; i++){
            for (j=0; j<grid[i].length; j++){        
                wu[k] = grid[i][j];
                k+=1;
            }
        }
    }

    private void setVirtualSites(){
        System.out.println(wu[0] + " - " + wu[wu.length-1]);
        wqu = new WeightedQuickUnionUF(wu.length);

        for (i=1; i<grid.length+1; i++){
            wqu.union(wu[0], wu[i]);
        }

        for (i=wu.length-2; i>((wu.length-2) - grid.length); i--){
            wqu.union(wu[wu.length-1], wu[i]);
        }
    }

    private void setOpenSites() {
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
        int index = find1dIndex(row, col);
        switch (adj){
            case "up":
                if ( validateIndex(row-1) && isOpen(row-1, col)) {
                    int up = find1dIndex(row-1, col);
                    wqu.union(index+1, up+1);
                }
                break;
            case "down":
                if ( validateIndex(row+1) && isOpen(row+1, col)) {
                    int down = find1dIndex(row+1, col);
                    wqu.union(index+1, down+1);
                } 
                break;
            case "left":
                if ( validateIndex(col-1) && isOpen(row, col-1)) {
                    int left = find1dIndex(row, col-1);
                    wqu.union(index+1, left+1);
                }
                break;
            case "right":
                if ( validateIndex(col+1) && isOpen(row, col+1)) {
                    int right = find1dIndex(row, col+1);
                    wqu.union(index+1, right+1);
                }
                break;
        }
    }

    public Percolation(int n) {
        size = n;
        int k = 0;
        // create n-by-n grid, with all sites blocked
        grid = new int[n][n];
        for (i=0; i<grid.length; i++){
            for (j=0; j<grid[i].length; j++){
                grid[i][j] = ++k;
            }
        }
        create1D();
        setVirtualSites(); 
        setOpenSites(); 
    }
    
    public void open(int row, int col)    {
        // open site (row, col) if it is not open already
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
    }

    public boolean isOpen(int row, int col)  {
        int index = find1dIndex(row, col);
        return openSites[index] == 1 ? true : false;
    }
    public boolean isFull(int row, int col)  {
        // is the site connected to the virtual top site?
        // TODO: mark site as full by marking it light blue
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
        System.out.println("does system percolate? " + percolation.percolates());
        percolation.open(1, 0);
        System.out.println("is wqu connected?  " + percolation.wqu.connected(3, 0));
        percolation.open(0,0); 
        System.out.println("does system percolate? " + percolation.percolates());
        percolation.open(1,0); 
        percolation.open(2,0); 
        System.out.println("is site open?  " + percolation.openSites[0]);
        System.out.println("does system percolate? " + percolation.percolates());
    }
 }