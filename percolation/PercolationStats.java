import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] prob; // 紀錄每一次實驗的 percolation threshold
    private double t; // 實驗次數
    private double c = 1.96d; // 95%信心區間的常數

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trails must bigger than 0");
        }
        else {
            t = trials;
            prob = new double[trials];
            for (int i = 0; i < trials; i++) {
                Percolation percolation = new Percolation(n);
                while (!percolation.percolates()) {
                    int row = StdRandom.uniform(n);
                    int col = StdRandom.uniform(n);
                    percolation.open(row, col);
                    percolation.isOpen(row, col);
                }
                prob[i] = (double) percolation.numberOfOpenSites() / (n * n);
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(prob);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(prob);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - ((c * stddev()) / Math.sqrt(t)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + ((c * stddev()) / Math.sqrt(t)));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        Stopwatch start = new Stopwatch();
        PercolationStats stats = new PercolationStats(n, trials);
        Stopwatch end = new Stopwatch();
        double time = start.elapsedTime() - end.elapsedTime();
        StdOut.println("mean()            = " + stats.mean());
        StdOut.println("stddev()          = " + stats.stddev());
        StdOut.println("confidenceL()     = " + stats.confidenceLo());
        StdOut.println("confidenceHi()    = " + stats.confidenceHi());
        StdOut.println("elapsed time()    = " + time);
    }
}
