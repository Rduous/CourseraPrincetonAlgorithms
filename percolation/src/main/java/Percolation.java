/**
 * Code implementing programming assignment #1 for Princeton Coursera
 * Algorithms course. (c) Abby B Bullock 2015
 *
 */
public class Percolation {

	private static final int INTEGER_BITS = Integer.BYTES * 8 - 1;

	/*
	 * Array of ints used (via bitwise operators) to track open/closed status of cells
	 */
	private int[] isOpenBits;

	/*
	 * Convenient values
	 */
	private final int topIndex;
	private final int bottomIndex;
	private final int n;

	/*
	 * Union-find impl to track whether overall system percolates
	 */
	private final WeightedQuickUnionUF percolatesUf;
	/*
	 * Union-find impl to track whether any given cell should be "full"
	 */
	private final WeightedQuickUnionUF isFullUf;

	public Percolation(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException("N must be greather than 0");
		}
		this.n = N;
		int totalNumElts = n * n + 2;
		isOpenBits = new int[(totalNumElts - 2) / INTEGER_BITS  + 2];
		topIndex = totalNumElts - 2;
		bottomIndex = totalNumElts - 1;

		percolatesUf = new WeightedQuickUnionUF(totalNumElts);
		isFullUf = new WeightedQuickUnionUF(totalNumElts);
	}

	/**
	 * Opens this cell in the grid
	 * @param i row number (1-indexed)
	 * @param j col number (1-indexed)
	 */
	public void open(int i, int j) {
		checkBounds(i, j);
		
		int ufIndex = getUFIndex(i, j);
		openBitAt(isOpenBits, ufIndex);
		// connect top and bottom elts, if appropriate
		if (i == 1) { // is top row
			percolatesUf.union(ufIndex, topIndex);
			isFullUf.union(ufIndex, topIndex);
		}
		if (i == n) { // is bottom row
			percolatesUf.union(ufIndex, bottomIndex);
		}

		// check and connect neighbors
		connectToNeighbor(ufIndex, i - 1, j);
		connectToNeighbor(ufIndex, i + 1, j);
		connectToNeighbor(ufIndex, i, j - 1);
		connectToNeighbor(ufIndex, i, j + 1);
	}
	
	private void openBitAt(int[] bitTrackers, int ufIndex) {
		int bitContainer = ufIndex / INTEGER_BITS;
		int bitNumber = ufIndex % INTEGER_BITS;
		isOpenBits[bitContainer] = isOpenBits[bitContainer] | (int) Math.pow(2, bitNumber);
				
	}

	private void connectToNeighbor(int cellUfIndex, int neighborRow,
			int neighborCol) {
		try {
			if (isOpen(neighborRow, neighborCol)) {
				percolatesUf.union(getUFIndex(neighborRow, neighborCol), cellUfIndex);
				isFullUf.union(getUFIndex(neighborRow, neighborCol), cellUfIndex);
			}
		} catch (IndexOutOfBoundsException e) {
			// skip
		}
	}

	/**
	 * @param i row number (1-indexed)
	 * @param j col number (1-indexed)
	 * @return value indicating whether this coordinate is open
	 */
	public boolean isOpen(int i, int j) {
		checkBounds(i, j);
		return isOpen(getUFIndex(i, j));
	}
	
	private boolean isOpen(int ufIndex) {
		int bitContainer = ufIndex / 31;
		int bitNumber = ufIndex % 31;
		int mask = 1 << bitNumber;
		int masked_n = isOpenBits[bitContainer] & mask;
		int thebit = masked_n >> bitNumber;
				
		return thebit == 1;
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
		return isOpen(i, j) && isFullUf.connected(getUFIndex(i, j), topIndex);
	}

	public boolean percolates() {
		return percolatesUf.connected(topIndex, bottomIndex);
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

