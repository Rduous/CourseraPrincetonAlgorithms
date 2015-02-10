import java.util.Arrays;

/**
 * Code implementing programming assignment #1 for Princeton Coursera
 * Algorithms course. (c) Abby B Bullock 2015
 *
 */
public class Percolation {

	private static final int IS_OPEN_INDEX = 0;
	private static final int IS_CONNECTED_TO_TOP_INDEX = 1;
	private static final int IS_CONNECTED_TO_BOTTOM_INDEX = 2;

	private static final int IS_OPEN = 1;
	private static final int IS_CONNECTED_TO_TOP = 2;
	private static final int IS_CONNECTED_TO_BOTTOM = 4;
	
	private static final int PERCOLATES_MASK = 5;
	
	private final int[] openFullArray;
	
	private boolean percolates = false;

	/*
	 * Convenient values
	 */
	private final int n;
	private final int totalNumElts;

	/*
	 * Union-find impl to track whether overall system percolates
	 */
	private final WeightedQuickUnionUF percolatesUf;
	

	public Percolation(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException("N must be greather than 0");
		}
		this.n = N;
		totalNumElts = n * n ;
		openFullArray = new int[totalNumElts];

		percolatesUf = new WeightedQuickUnionUF(totalNumElts);
	}

	/**
	 * Opens this cell in the grid
	 * @param i row number (1-indexed)
	 * @param j col number (1-indexed)
	 */
	public void open(int i, int j) {
		checkBounds(i, j);
		
		int ufIndex = getUFIndex(i, j);
		
		// connect top and bottom elts, if appropriate, and track their statuses
		int[] roots = new int[4];
		Arrays.fill(roots, -1);
		int[][] neighbors = new int[][] {{i-1,j},{i+1,j},{i,j-1},{i,j+1}};
		
		for (int k = 0; k < neighbors.length; k++) {
			int[] coord = neighbors[k];
			int row = coord[0];
			int col = coord[1];int neighbor = getUFIndex(row, col);
			if ( row > 0 && col > 0 && row <= n && col <= n && isBitOpenAt(neighbor, IS_OPEN_INDEX)) {
				roots[k] = percolatesUf.find(neighbor);
				percolatesUf.union(ufIndex, neighbor);
			}
		}
		int newRoot = percolatesUf.find(ufIndex);
		for (int k = 0; k < neighbors.length; k++) {
			int neighborRoot = roots[k];
			if (neighborRoot != -1 ) {
				openFullArray[newRoot] = openFullArray[newRoot] | openFullArray[roots[k]];
			}
		}
		openFullArray[ufIndex] = openFullArray[ufIndex] | IS_OPEN;
		if (i == 1) {
			openFullArray[newRoot] = openFullArray[newRoot] | IS_CONNECTED_TO_TOP;
		}
		if (i == n) { 
			openFullArray[newRoot] = openFullArray[newRoot] | IS_CONNECTED_TO_BOTTOM;
		}
		
		percolates = percolates || (isBitOpenAt(newRoot, IS_CONNECTED_TO_TOP_INDEX) && isBitOpenAt(newRoot, IS_CONNECTED_TO_BOTTOM_INDEX));
	}
	
//	private void openBitAt(int[] bitTrackers, int ufIndex) {
//		int bitContainer = ufIndex / INTEGER_BITS;
//		int bitNumber = ufIndex % INTEGER_BITS;
//		isOpenBits[bitContainer] = isOpenBits[bitContainer] | (int) Math.pow(2, bitNumber);
//				
//	}

	/**
	 * @param i row number (1-indexed)
	 * @param j col number (1-indexed)
	 * @return value indicating whether this coordinate is open
	 */
	public boolean isOpen(int i, int j) {
		checkBounds(i, j);
//		int root = percolatesUf.find(getUFIndex(i, j));
		return isBitOpenAt(getUFIndex(i, j), IS_OPEN_INDEX);
	}
	
	private boolean isBitOpenAt(int ufIndex, int bitIndex) {
		int mask = 1 << bitIndex;
		int masked_n = openFullArray[ufIndex] & mask;
		return masked_n >> bitIndex == 1;
	}

	/**
	 * Returns a boolean representing whether the given coordinate is connected
	 * to the top of the grid.
	 * 
	 * @param i row number (1-indexed)
	 * @param j col number (1-indexed)
	 */
	public boolean isFull(int i, int j) {
		checkBounds(i, j);
		int root = percolatesUf.find(getUFIndex(i, j));
		return isBitOpenAt(root, IS_CONNECTED_TO_TOP_INDEX);
	}

	public boolean percolates() {
		return percolates;
	}

	public static void main(String[] args) {
		// test client (optional)
	}

	/**
	 * Throws an IndexOutOfBoundsException if rows exceed expected dimensions
	 * 
	 * @param i row number (1-indexed)
	 * @param j col number (1-indexed)
	 */
	private void checkBounds(int i, int j) {
		if (i > n || j > n || i <= 0 || j <= 0) {
			throw new IndexOutOfBoundsException("Coordinate (" + i + "," + j
					+ ") is out of bounds.");
		}
	}

	/**
	 * Translates from row/col coordinates to the UF-index
	 */
	private int getUFIndex(int i, int j) {
		return (i - 1) * n + j - 1;
	}

}

