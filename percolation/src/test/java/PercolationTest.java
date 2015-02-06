import static org.junit.Assert.*;

import org.junit.Test;

/**
 * These tests are basic but not exhaustive.  The coursera course provides a number of very thorough
 * test cases which are used in their grading suite.
 * 
 */
public class PercolationTest {
	
	//
	// bounds-checking tests
	//
	@Test(expected=IllegalArgumentException.class)
	public void testAZeroGridSizeErrors() {
		new Percolation(0);
	}
	
	@Test
	public void testIsOpenErrorsOnOutOfBoundsCoords() {
		Percolation p = new Percolation(1);
		checkIsOpenCornerCase(p, "Error should be thrown when row number is zero", 0, 1);
		checkIsOpenCornerCase(p, "Error should be thrown when row number is below zero", -1, 1);
		checkIsOpenCornerCase(p, "Error should be thrown when col number is zero", 1, 0);
		checkIsOpenCornerCase(p, "Error should be thrown when col number is below zero", 1, -1);
		checkIsOpenCornerCase(p, "Error should be thrown when row number exceeds dimension", 2, 1);
		checkIsOpenCornerCase(p, "Error should be thrown when row number exceeds dimension", 1, 2);
		p.isOpen(1, 1);		
	}
	
	@Test
	public void testOpenErrorsOnOutOfBoundsCoords() {
		Percolation p = new Percolation(1);
		checkOpenCornerCase(p, "Error should be thrown when row number is zero", 0, 1);
		checkOpenCornerCase(p, "Error should be thrown when row number is below zero", -1, 1);
		checkOpenCornerCase(p, "Error should be thrown when col number is zero", 1, 0);
		checkOpenCornerCase(p, "Error should be thrown when col number is below zero", 1, -1);
		checkOpenCornerCase(p, "Error should be thrown when row number exceeds dimension", 2, 1);
		checkOpenCornerCase(p, "Error should be thrown when row number exceeds dimension", 1, 2);
		p.open(1, 1);		
	}
	
	@Test
	public void testIsFullErrorsOnOutOfBoundsCoords() {
		Percolation p = new Percolation(1);
		checkIsFullCornerCase(p, "Error should be thrown when row number is zero", 0, 1);
		checkIsFullCornerCase(p, "Error should be thrown when row number is below zero", -1, 1);
		checkIsFullCornerCase(p, "Error should be thrown when col number is zero", 1, 0);
		checkIsFullCornerCase(p, "Error should be thrown when col number is below zero", 1, -1);
		checkIsFullCornerCase(p, "Error should be thrown when row number exceeds dimension", 2, 1);
		checkIsFullCornerCase(p, "Error should be thrown when row number exceeds dimension", 1, 2);
		p.isFull(1, 1);	
	}
	
	//
	// isOpen() tests
	//
	@Test
	public void testCellsDefaultsToClosed() {
		Percolation p = new Percolation(1);
		assertFalse(p.isOpen(1, 1));
	}
	
	@Test
	public void testOpenedCellIsOpen() {
		Percolation p = new Percolation(1);
		p.open(1, 1);
		assertTrue(p.isOpen(1, 1));
	}
	
	//
	// isFull() tests
	//
	@Test
	public void testIsFullDefaultsToFalse() {
		Percolation p = new Percolation(1);
		assertFalse(p.isFull(1, 1));
	}
	
	@Test
	public void testOneCellOpenedIsFull() {
		Percolation p = new Percolation(1);
		p.open(1, 1);
		assertTrue(p.isFull(1, 1));
	}
	
	@Test
	public void testACellOnBottomRowIsEmptyWhenOpened() {
		Percolation p = new Percolation(2);
		p.open(2, 2);
		assertFalse(p.isFull(2, 2));
	}
	
	//
	// percolates() tests
	//
	@Test
	public void testOneCellClosedDoesNotPercolate() {
		Percolation p = new Percolation(1);
		assertFalse("A 1x1 grid with no open cells should not percolate",
				p.percolates());
	}
	
	@Test
	public void testOneCellOpenPercolates() {
		Percolation p = new Percolation(1);
		p.open(1, 1);
		assertTrue("A 1x1 grid with an open cell should percolate", p.percolates());
	}
	
	@Test
	public void testGridWithOpenColumnPercolates() {
		Percolation p = new Percolation(2);
		p.open(1, 1);
		p.open(2, 1);
		assertTrue(p.percolates());
	}
	
	@Test
	public void testGridWithOpenDiagonalDoesNotPercolate() {
		Percolation p = new Percolation(2);
		p.open(1, 1);
		p.open(2, 2);
		assertFalse(p.percolates());
	}
	
	
	/*
	 * o x x
	 * o x x
	 * o x o <-- should not be full
	 */
	@Test
	public void testPercolatingSystemDoesNotBackwash() {
		Percolation p = new Percolation(3);
		p.open(1, 1);
		p.open(2, 1);
		p.open(3, 1);
		p.open(3, 3);
		assertTrue(p.percolates());
		assertFalse(p.isFull(3, 3));
	}
	
	/*
	 *
	 *  x  x  x  x  1  x
	 *  x  x  x  x  2  x
	 *  10 9  8  x  3  x
	 *  11 x  7  x  4  x
	 *  12 x  6  5  14 x
	 *  13 x  x  x  x  x
	 * 
	 * Opening in this order, the system should not percolate until the
	 * 14th cell is opened
	 * 
	 */
	@Test
	public void checkNeighborsGetConnectedCorrectly() {
		int[][] coords = new int[][] { {1,5}, {2,5},{3,5},{4,5},{5,4}, {5,3}, {4,3}, {3,3},{3,2},{3,1},
				{4,1},{5,1},{6,1}};
		Percolation p = new Percolation(6);
		for (int i = 0; i < coords.length; i++) {
			int[] coord = coords[i];
			p.open(coord[0], coord[1]);
			assertFalse("System percolates after " + (i + 1) + " cells are opened; it shouldn't.", p.percolates());
		}
		p.open(5, 5);
		assertTrue(p.percolates());
		for (int i = 0; i < coords.length; i++) {
			int[] coord = coords[i];
			assertTrue(p.isFull(coord[0], coord[1]));
		}
	}

	private void checkIsOpenCornerCase(Percolation p, String message, int row,
			int col) {
		try {
			p.isOpen(row,col);
		} catch(IndexOutOfBoundsException e) {
			return;
		}
		fail(message);
	}
	
	private void checkOpenCornerCase(Percolation p, String message, int row,
			int col) {
		try {
			p.open(row,col);
		} catch(IndexOutOfBoundsException e) {
			return;
		}
		fail(message);
	}
	
	private void checkIsFullCornerCase(Percolation p, String message, int row,
			int col) {
		try {
			p.isFull(row,col);
		} catch(IndexOutOfBoundsException e) {
			return;
		}
		fail(message);
	}

}
