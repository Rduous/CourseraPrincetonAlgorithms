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
