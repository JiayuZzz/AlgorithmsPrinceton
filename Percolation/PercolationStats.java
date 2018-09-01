import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    public PercolationStats(int n, int trials){
        if(n<=0||trials<=0){
            throw new java.lang.IllegalArgumentException("grid size and trials should >0!");
        }
        results = new double[trials];
        numTrials = trials;
        for(int i=0;i<trials;i++){
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1,n+1);
                percolation.open(row,col);
            }
            results[i] = (double)percolation.numberOfOpenSites()/(n*n);
        }
    }

    public double mean(){
        return StdStats.mean(results);
    }

    public double stddev(){
        return StdStats.stddev(results);
    }

    public double confidenceLo(){
        return mean()-1.96*stddev()/Math.sqrt((double)numTrials);
    }

    public double confidenceHi(){
        return mean()+1.96*stddev()/Math.sqrt((double)numTrials);
    }

    private double[] results;
    private int numTrials;   //how many trials
    private Percolation percolation;

    public static void main(String[] args){
        if(args.length!=2){
            throw new java.lang.IllegalArgumentException("usage: PercolationStats [gridsize] [numTrials]");
        }
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n,t);
        StdOut.println("mean      ="+percolationStats.mean());
        StdOut.println("stddev    ="+percolationStats.stddev());
        StdOut.println("95% confidence interval = ["+percolationStats.confidenceLo()+","+percolationStats.confidenceHi()+"]");
    }
}
