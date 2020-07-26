package percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean;
    private final double stdDev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) throw new IllegalArgumentException("n must be higher than 0");
        if (trials <= 0) throw new IllegalArgumentException("Number of Trials must be higher than 0");
        final double[] samples = new double[trials];
        double totalSites = (double) n * n;
        for (int i = 0; i < trials; i++) {
            final Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int r = 1 + StdRandom.uniform(n);
                int c = 1 + StdRandom.uniform(n);
                percolation.open(r, c);
            }
            samples[i] = percolation.numberOfOpenSites() / totalSites;
        }
        mean = StdStats.mean(samples);
        stdDev = StdStats.stddev(samples);
        double confidenceFactor = 1.96 * stdDev / Math.sqrt(trials);
        confidenceLo = mean - confidenceFactor;
        confidenceHi = mean + confidenceFactor;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        final PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("%-30s = %.10f%n", "mean", stats.mean());
        StdOut.printf("%-30s = %.10f%n", "stddev", stats.stddev());
        StdOut.printf("%-30s = [%.10f, %.10f]%n", "95% confidence interval", stats.confidenceLo(), stats.confidenceHi());
    }
}
