import java.util.Arrays;

/**
 * Code implementing programming assignment #1 for Princeton Coursera
 * Algorithms course. (c) Abby B Bullock 2015
 *
 */
public class Percolation {
	
	private static final int INTEGER_BITS = 31;
	private static final int NUMBER_OF_PROPERTIES_TRACKED = 3;
	private static final int ELTS_IN_SINGLE_INT = INTEGER_BITS / NUMBER_OF_PROPERTIES_TRACKED;

	private static final int IS_OPEN = 1;
	private static final int IS_CONNECTED_TO_TOP = 2;
	private static final int IS_CONNECTED_TO_BOTTOM = 4;
	private static final int PERCOLATES = 6;
	private static final int ALL_TRUE = 7;
	
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
	
	/*
	 * An array of ints used w/bitmasks to track:
	 * byte 3n:     open/closed status
	 * byte 3n + 1: connected-to-top status
	 * byte 3n + 2: connected-to-bottom status
	 */
	private final int[] openFullArray;
	

	public Percolation(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException("N must be greather than 0");
		}
		this.n = N;
		totalNumElts = n * n ;
		openFullArray = new int[(totalNumElts) / ELTS_IN_SINGLE_INT  + 1];

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
		unionWithNeighborIfOpen(ufIndex, roots, 0, i-1, j);
		unionWithNeighborIfOpen(ufIndex, roots, 1, i+1, j);
		unionWithNeighborIfOpen(ufIndex, roots, 2, i, j-1);
		unionWithNeighborIfOpen(ufIndex, roots, 3, i, j+1);

		int newRoot = percolatesUf.find(ufIndex);
		for (int k = 0; k < roots.length; k++) {
			int neighborRoot = roots[k];
			if (neighborRoot != -1 ) {
				unionOtherIntoRoot(newRoot, roots[k]);
			}
		}
		union(ufIndex, IS_OPEN);
		if (i == 1) {
			union(newRoot, IS_CONNECTED_TO_TOP);
		}
		if (i == n) { 
			union(newRoot, IS_CONNECTED_TO_BOTTOM);
		}
		percolates = percolates || intersect(newRoot, PERCOLATES);
	}

	/**
	 * @param i row number (1-indexed)
	 * @param j col number (1-indexed)
	 * @return value indicating whether this coordinate is open
	 */
	public boolean isOpen(int i, int j) {
		checkBounds(i, j);
		return intersect(getUFIndex(i, j), IS_OPEN);
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
		return intersect(root, IS_CONNECTED_TO_TOP);
	}

	public boolean percolates() {
		return percolates;
	}
	
	private int getIntContainerIndex(int ufIndex) {
		return ufIndex / ELTS_IN_SINGLE_INT;
	}
	
	private int getIndexOfEltInContainer(int ufIndex) {
		return ( ufIndex % ELTS_IN_SINGLE_INT ) * NUMBER_OF_PROPERTIES_TRACKED; 
	}
	
	private void unionOtherIntoRoot(int rootIndex, int otherIndex) {
		int container = getIntContainerIndex(otherIndex);
		int bitNum = getIndexOfEltInContainer(otherIndex);
		int masked_n = openFullArray[container] & (ALL_TRUE << bitNum);
		int rootMask = masked_n >> bitNum;
		union(rootIndex, rootMask);
	}
	
	private void union(int ufIndex, int mask) {
		int container = getIntContainerIndex(ufIndex);
		int bitNum = getIndexOfEltInContainer(ufIndex);
		int shiftedMask = mask << bitNum;
		openFullArray[container] = openFullArray[container] | shiftedMask;
	}
	
	private boolean intersect(int ufIndex, int mask) {
		int container = getIntContainerIndex(ufIndex);
		int bitIndex = getIndexOfEltInContainer(ufIndex) ;
		int shifted_mask = mask << bitIndex;
		int masked = openFullArray[container] & shifted_mask;
		return masked >> bitIndex == mask;
	}

	/**
	 * Checks the cell at given row and column.  If it is open, union that cell with the elt,
	 * and record the cell's root in the roots array.
	 * @param elt
	 * @param roots the roots of open neighbors of elt
	 * @param k the index at which to store the root
	 * @param neighborRow
	 * @param neighborCol
	 */
	private void unionWithNeighborIfOpen(int elt, int[] roots, int k, int neighborRow,
			int neighborCol) {
		int neighbor = getUFIndex(neighborRow, neighborCol);
		if ( neighborRow > 0 && neighborCol > 0 && neighborRow <= n && neighborCol <= n && intersect(neighbor, IS_OPEN)) {
			int root = percolatesUf.find(neighbor);
			roots[k] = root;
			percolatesUf.union(elt, root);
		}
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

