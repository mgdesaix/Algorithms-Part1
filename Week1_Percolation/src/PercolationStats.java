import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdStats;


/************************************************************************
 * PercolationStats
 * Name: Matt DeSaix
 * 
 * Description: Runs a Percolation simulation
 * on a n x n array, t times. Requires Percolation class to create array and methods.
 * PercolationStats returns the mean, std, and 95% confidence intervals of the simulation
 * estimating the phase transition
 */

public class PercolationStats {

	private final static double ci95 = 1.96;
	private final int t;
	private final double results[];
	
	public PercolationStats(int n, int trials) {
		if (n <= 0) throw new IllegalArgumentException("n out of bounds");
		if (trials <= 0) throw new IllegalArgumentException("n out of bounds");
		t = trials;
		
		results = new double[trials];
		for(int i = 0; i < trials; i++) {
			Percolation perc = new Percolation(n); // create array values based on n
	        
	        while( !perc.percolates()) {
	        	int row = StdRandom.uniform(n)+1; // row is >= 1 and <= 
	        	int col = StdRandom.uniform(n)+1;
    
	            while(perc.isOpen(row, col)) { // keep getting random numbers if they've already been opened
	            	row = StdRandom.uniform(n) + 1;
		        	col = StdRandom.uniform(n) + 1;
	            }
	            perc.open(row, col); 
	        }
	        double count = perc.numberOfOpenSites();
	        results[i] = (count/(n*n));
		}
		
	}
	
	public double mean() {
		double x = StdStats.mean(results);
		return x;
	}
	
	public double stddev() {
		double x = StdStats.stddev(results);
		return x;
	}
	
	public double confidenceLo() {
		return (mean() - (ci95 * stddev()/Math.sqrt(t)));
	}
	
	public double confidenceHi() {
		return (mean() + (ci95 * stddev()/Math.sqrt(t)));
	}
	
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);	// read in n-value for array
		int t = Integer.parseInt(args[1]); // read in number of iterations
		
		PercolationStats ps = new PercolationStats(n, t);
		System.out.println("Percolation with a " + n + " by " + n + " array and " + t + " trials");
	

		System.out.println("mean                     = " + ps.mean());
		System.out.println("stddev                   = " + ps.stddev());
		System.out.println("95% confidence interval  = [" + ps.confidenceLo() +
				" , " + ps.confidenceHi() + "]");
	}

}
