/**
 * Code implementing programming assignment #1 for Princeton Coursera
 * Algorithms course. (c) Abby B Bullock 2015
 *
 */
public class Percolation {

	/*
	 * Grid to track open/close status of cells
	 */
	private final boolean[][] isOpenGrid;

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
		isOpenGrid = new boolean[n][n];
		int totalNumElts = n * n + 2;
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
		isOpenGrid[i - 1][j - 1] = true;
		int ufIndex = getUFIndex(i, j);

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
		return isOpenGrid[i - 1][j - 1];
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

