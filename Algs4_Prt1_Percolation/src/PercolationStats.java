/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Victor Skurikhin
 */
public class PercolationStats {
    private double dMean, dStddev;
    private double dSigma, dSqrtT;
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("Illegal Argument!"
                      + "N: " + N + " T: " + T);
        }
        double fields       = N*N;
        dMean               = 0.0;
        dStddev             = 0.0;
        double[] adXValues  = new double[T];
        for (int idx = 0; idx < T; ++idx) {
            Percolation p = new Percolation(N);
            int openSites = 0;
            while (!p.percolates()) {
                int row = StdRandom.uniform(0, N) + 1;
                int col = StdRandom.uniform(0, N) + 1;

                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    ++openSites;
                }
            }
            adXValues[idx] = 1.0 * openSites / fields;
        }
        dMean   = StdStats.mean(adXValues);
        dStddev = StdStats.stddev(adXValues);
        dSigma  = Math.sqrt(dStddev);
        dSqrtT  = Math.sqrt(T);
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return this.dMean;
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.dStddev;
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return dMean - (1.96 * dSigma)/dSqrtT;
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return dMean + (1.96 * dSigma)/dSqrtT;
    }
    
    // test client (described below)
    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
                                                   Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + ps.confidenceLo() + ", "
                                                    + ps.confidenceHi());
        //////////////////////////////////////////////////////////////////////
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
// EOF
