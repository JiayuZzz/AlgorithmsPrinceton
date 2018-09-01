import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;

public class Percolation {
    private byte[][] grid;  // 1:opened, 0:blocked
    private final int length;   // length of row
    private WeightedQuickUnionUF ufTop;     // union-find with top virtual site
    private WeightedQuickUnionUF uf; //union-find with two virtual sites
    // union vitualTop with all first row sites, vitualBottom with all last row sites
    // if find(virtualTop)==find(virtualBottom), then percolated.
    private final int virtualTop;
    private final int virtualBottom;
    private int numOpenSites;  // number of open sites

    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("n should larger than 0!");
        }
        grid = new byte[n + 1][n + 1];
        length = n;
        ufTop = new WeightedQuickUnionUF(n * n + 1);
        uf = new WeightedQuickUnionUF(n * n + 2);
        virtualTop = 0;
        virtualBottom = n * n + 1;
        numOpenSites = 0;
    }

    /* open a site, and connect adjacent sites */
    public void open(int row, int col) {
        verify(row, col);
        if (grid[row][col] == 0) {
            grid[row][col] = 1;
            numOpenSites++;
            connect(row, col);
            if (row == 1) {
                bothUnion(toIndex(row, col), virtualTop);
            }
            if (row == length) {
                uf.union(toIndex(row, col), virtualBottom);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        verify(row, col);
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        verify(row, col);
        return ufTop.connected(virtualTop, toIndex(row, col));
    }

    public int numberOfOpenSites() {
        return numOpenSites;
    }

    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }

    /* transfer site position to uf index */
    private int toIndex(int row, int col) {
        return (row - 1) * length + col;
    }

    /* verify validation */
    private void verify(int row, int col) {
        if (row < 1 || row > length || col < 1 || col > length) {
            throw new java.lang.IllegalArgumentException("invalid site position!");
        }
    }

    /* union i and j in uf and ufTop */
    private void bothUnion(int i, int j) {
        ufTop.union(i, j);
        uf.union(i, j);
    }

    /* connect [row][col] with adjacent */
    private void connect(int row, int col) {
        int index = toIndex(row, col);
        if (col > 1) {
            if (isOpen(row, col - 1)) {
                bothUnion(index, toIndex(row, col - 1));
            }
        }
        if (col < length) {
            if (isOpen(row, col + 1)) {
                bothUnion(index, toIndex(row, col + 1));
            }
        }
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                bothUnion(index, toIndex(row - 1, col));
            }
        }
        if (row < length) {
            if (isOpen(row + 1, col)) {
                bothUnion(index, toIndex(row + 1, col));
            }
        }
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation p = new Percolation(n);
        int a, b;
        while (!StdIn.isEmpty()) {
            a = StdIn.readInt();
            b = StdIn.readInt();
            p.open(a, b);
            StdOut.println(p.isFull(a, b));
        }
        StdOut.println(p.percolates());
    }
}
