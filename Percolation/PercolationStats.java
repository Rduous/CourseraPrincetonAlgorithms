import java.util.Random;


public class PercolationStats {
	
	private final int n;
	private final int t;
	private final double[] percentOpenResults;
	
	private double percolationThresh;
	private double standardDev;
	
	public PercolationStats(int n, int t) {
		// perform T independent experiments on an N-by-N grid
		if (n <= 0 || t <= 0) {
			throw new IllegalArgumentException();
		}
		this.n = n;
		this.t = t;
		percentOpenResults = new double[t];
		performExperiments();
	}
	
	private void performExperiments() {
		
		
		Random r = new Random(System.currentTimeMillis());
		
		for (int i = 0; i < t; i++) {
			Percolation perc = new Percolation(n);
			int numOpen = 0;
			while (! perc.percolates()) {
				int row = r.nextInt(n) + 1;
				int col = r.nextInt(n) + 1;
				if (! perc.isOpen(row, col)) {
					numOpen ++;
				}
				perc.open(row, col);
			}
			percentOpenResults[i] = ( ( double ) numOpen) / (n*n);
		}
		percolationThresh = mean();
		standardDev = stddev();
	}

	public double mean() {
		return StdStats.mean(percentOpenResults);
	}

	public double stddev() {
		
		// sample standard deviation of percolation threshold
		return StdStats.stddev(percentOpenResults);
	}

	public double confidenceLo() {
		
		return percolationThresh - ( 1.96 * standardDev)/ Math.sqrt(t) ;
	}

	public double confidenceHi() {

		return percolationThresh + ( 1.96 * standardDev)/ Math.sqrt(t) ;
	}

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);
		
		PercolationStats stats = new PercolationStats(n, t);
		System.out.println("mean                    = " + stats.mean());
		System.out.println("stddev                  = " + stats.stddev());
		System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
		
	}
}
