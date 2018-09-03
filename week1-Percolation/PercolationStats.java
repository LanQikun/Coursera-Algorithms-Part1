package week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] fractions;
    private int trialCount;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trial) {
        if(n <= 0 || trial <= 0){
            throw new IllegalArgumentException(
                    "parameters should be positive");
        }
        this.trialCount = trial;
        this.fractions = new double[trialCount];
        int siteCount = n * n;

        for (int i = 0; i < trialCount; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                p.open(row, col);
            }
            fractions[i] = ((double) p.numberOfOpenSites()) / siteCount;
        }
    }

    public double mean() {
        if (mean == 0) {
            mean = StdStats.mean(fractions);
        }
        return mean;
    }

    public double stddev() {
        if (stddev == 0) {
            if(trialCount != 1){
                stddev = StdStats.stddev(fractions);
            }else{
                stddev = Double.NaN;
            }
        }
        return stddev;
    }

    public double confidenceLo() {
        mean = mean();
        stddev = stddev();
        return mean - 1.96 * stddev / Math.sqrt(trialCount);
    }

    public double confidenceHi() {
        mean = mean();
        stddev = stddev();
        return mean + 1.96 * stddev / Math.sqrt(trialCount);
    }

//    public static void main(String[] args) {
////        test(200, 100);
////        test(2, 10000);
//        test(2, 100000);
//    }
//    
//    private static void test(int n, int trial){
//        PercolationStats p = new PercolationStats(n, trial);
//        System.out.println(p.mean());
//        System.out.println(p.stddev());
//        System.out.println("["+p.confidenceLo()+", "+p.confidenceHi()+"]");
//    }

}
