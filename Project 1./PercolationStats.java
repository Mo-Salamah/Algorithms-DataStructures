/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {


    //array that stores the fraction of open sites before a system percolates, for all trials
    private double[] fractions;
    private int trialsNum;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Values not appropriate");
        }
        trialsNum = trials;
        fractions = new double[trials];

        for (int i = 0; i < trials; i++) {
            boolean percolates = false;
            Percolation trial = new Percolation(n);
            while (!percolates) {
                //at random open a site with range: 1-n
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                trial.open(randomRow, randomCol);
                if (trial.percolates()) {
                    percolates = true;
                    double openSites = trial.numberOfOpenSites();
                    double totalNumberSites = n * n;
                    double openSitesFraction = openSites / totalNumberSites;
                    fractions[i] = openSitesFraction;
                }


            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);

    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        //given formula
        return this.mean() - ((1.96 * this.stddev()) / Math.sqrt(trialsNum));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        //given formula
        return this.mean() + ((1.96 * this.stddev()) / Math.sqrt(trialsNum));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats experiment = new PercolationStats(n, T);
        System.out.println("mean                    = " + experiment.mean());
        System.out.println("stddev                  = " + experiment.stddev());
        String confidenceInterval = "[" + String.valueOf(experiment.confidenceLo()) + ", " + String
                .valueOf(experiment.confidenceHi()) + "]";
        System.out.println("95% confidence interval = " + confidenceInterval);


    }

}









