import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;

public class Percolation {
    public Percolation(int n){
        if(n<=0){
            throw new java.lang.IllegalArgumentException("n should larger than 0!");
        }
        grid = new byte[n+1][n+1];
        length = n;
        uf = new WeightedQuickUnionUF(n*n+3);
        virtualTop = n*n+1;
        virtualBottom = n*n+2;
        numOpenSites = 0;
        //union vitualTop with all first row sites, vitualBottom with all last row sites
        for(int i=1;i<=length;i++){
            uf.union(toIndex(1,i),virtualTop);
            uf.union(toIndex(length,i),virtualBottom);
        }
    }

    public void open(int row, int col){
        verify(row,col);
        if(grid[row][col]==0){
            grid[row][col] = 1;
            numOpenSites++;
            connect(row,col);
        }
    }

    public boolean isOpen(int row, int col){
        verify(row,col);
        return grid[row][col]==1;
    }

    public boolean isFull(int row, int col){
        verify(row,col);
        return uf.find(toIndex(row,col))==uf.find(virtualTop);
    }

    public int numberOfOpenSites(){
        return numOpenSites;
    }

    public boolean percolates(){
        return uf.find(virtualTop)==uf.find(virtualBottom);
    }

    private int toIndex(int row, int col){
        return (row-1)*length+col;
    }

    private void verify(int row, int col){
        if(row<1||row>length||col<1||col>length){
            throw new java.lang.IllegalArgumentException("invalid site position!");
        }
    }

    //connect [row][col] with adjacent
    private void connect(int row, int col){
        int index = toIndex(row,col);
        if(col>1){
            if(isOpen(row,col-1)) {
                uf.union(index, toIndex(row, col - 1));
            }
        }
        if(col<length){
            if(isOpen(row,col+1)) {
                uf.union(index, toIndex(row, col + 1));
            }
        }
        if(row>1){
            if(isOpen(row-1,col)) {
                uf.union(index, toIndex(row - 1, col));
            }
        }
        if(row<length){
            if(isOpen(row+1,col)) {
                uf.union(index, toIndex(row + 1, col));
            }
        }
    }

    private byte[][] grid;  //1:opened, 0:blocked
    private int length;   // length of row
    private WeightedQuickUnionUF uf;
    ////union vitualTop with all first row sites, vitualBottom with all last row sites
    // if find(virtualTop)==find(virtualBottom), then percolated.
    private int virtualTop;
    private int virtualBottom;
    int numOpenSites;  //number of open sites

    public static void main(String[] args){
        int n = StdIn.readInt();
        Percolation p = new Percolation(n);
        int a,b;
        while(!StdIn.isEmpty()){
            a=StdIn.readInt();
            b=StdIn.readInt();
            p.open(a,b);
            StdOut.println(p.isFull(a,b));
        }
        StdOut.println(p.percolates());
    }
}
