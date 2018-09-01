import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;
    private int numTrials;   //how many trials
    private double mean;
    private double standardDev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("grid size and trials should >0!");
        }
        results = new double[trials];
        numTrials = trials;
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                perc.open(row, col);
            }
            results[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
        mean = StdStats.mean(results);
        standardDev = StdStats.stddev(results);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return standardDev;
    }

    public double confidenceLo() {
        return mean - 1.96 * standardDev / Math.sqrt((double) numTrials);
    }

    public double confidenceHi() {
        return mean + 1.96 * standardDev / Math.sqrt((double) numTrials);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new java.lang.IllegalArgumentException("usage: PercolationStats [gridsize] [numTrials]");
        }
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, t);
        StdOut.println("mean      =" + percolationStats.mean());
        StdOut.println("stddev    =" + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + "," + percolationStats.confidenceHi() + "]");
    }
}
