public class Percolation {

	private final boolean[][] grid;

	private final int[] unionFindArray;
	private final int[] sizes;

	private final int topIndex;
	private final int bottomIndex;
	private final int n;
	
	public Percolation(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException("N must be greather than 0");
		}
		grid = new boolean[N][N];
		unionFindArray = new int[N * N + 2];
		for (int i = 0; i < unionFindArray.length; i++) {
			unionFindArray[i] = i;
		}
		sizes = new int[N * N + 2];
		topIndex = N * N;
		bottomIndex = N * N + 1;
		this.n = N;
		
		//connect top and bottom elements
//		int row = 1;
//		for(int col=1; col<=n; col++) {
//			union(topIndex,getUFIndex(row, col));
//		}
//		row = n;
//		for(int col=1; col<=n; col++) {
//			union(bottomIndex,getUFIndex(row, col));
//		}
	}

	public void open(int i, int j) {
		// open site (row i, column j) if it is not open already
		checkBounds(i, j);
		grid[i - 1][j - 1] = true;
		

		// check neighbors
		int ufIndex = getUFIndex(i, j);
		if (i == 1) {
			union(ufIndex, topIndex);
		}
		if (i == n) {
			union(ufIndex, bottomIndex);
		}
		for (int rowMod = -1; rowMod < 2; rowMod += 2) {
			if (rowMod != 0) {
				try {
					if (isOpen(i + rowMod, j)) {
						union(getUFIndex(i + rowMod, j), ufIndex);
					}
				} catch (IndexOutOfBoundsException e) {
					// skip
				}
			}
		}
		for (int colMod = -1; colMod < 2; colMod += 2) {
			if (colMod != 0) {
				try {
					if (isOpen(i, j + colMod)) {
						union(getUFIndex(i, j + colMod), ufIndex);
					}
				} catch (IndexOutOfBoundsException e) {
					// skip
				}
			}
		}

	}

	public boolean isOpen(int i, int j) {
		// is site (row i, column j) open?
		checkBounds(i, j);
		return grid[i - 1][j - 1];
	}

	public boolean isFull(int i, int j) {
		// is site (row i, column j) full?
		checkBounds(i, j);
		return isOpen(i,j) && find(getUFIndex(i, j), topIndex);
	}

	public boolean percolates() {
		return find(topIndex, bottomIndex);
	}

	public static void main(String[] args) {
		// test client (optional)
	}

	private void checkBounds(int i, int j) {
		if (i > n || j > n || i <= 0 || j <= 0) {
			throw new IndexOutOfBoundsException("Coordinate (" + i + "," + j
					+ ") is out of bounds.");
		}
	}

	private int getUFIndex(int i, int j) {
		return (i - 1) * n + j - 1;
	}

	private boolean find(int p, int q) {
		int pid = getRoot(p);
		int qid = getRoot(q);
		return pid == qid;
	}

	private void union(int p, int q) {
		int i = getRoot(p);
		int j = getRoot(q);
		if (sizes[i] >= sizes[j]) {
			unionFindArray[j] = i;
			sizes[i] += sizes[j];
		} else {
			unionFindArray[i] = j;
			sizes[j] += sizes[i];
		}
	}

	private int getRoot(int i) {
		while (i != unionFindArray[i]) {
			unionFindArray[i] = unionFindArray[unionFindArray[i]];
			i = unionFindArray[i];
		}
		return i;
	}

}

