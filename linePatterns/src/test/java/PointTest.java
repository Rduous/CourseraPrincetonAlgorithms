import java.util.Arrays;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {

    //
    // compareTo tests
    //
    @Test
    public void testComparePointToSelf() {
        Point p = new Point(0, 0);
        assertEquals(0, p.compareTo(p));
    }

    @Test
    public void testCompareToWithDifferentYCoords() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 1);
        Point p3 = new Point(0, 2);
        testPointsInOrder(p1, p2, p3);
    }

    @Test
    public void testXCoordTieBreak() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(2, 0);
        testPointsInOrder(p1, p2, p3);
    }

    @Test
    public void testXCoordsIgnoredOutsideOfTieBreak() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(-100, 1);
        Point p3 = new Point(0, 1);
        testPointsInOrder(p1, p2, p3);
    }

    private void testPointsInOrder(Point p1, Point p2, Point p3) {
        assertTrue(p1.compareTo(p2) < 0);
        assertTrue(p1.compareTo(p3) < 0);
        assertTrue(p2.compareTo(p3) < 0);
        assertTrue(p2.compareTo(p1) > 0);
        assertTrue(p3.compareTo(p1) > 0);
        assertTrue(p3.compareTo(p2) > 0);
    }

    //
    // slopeTo tests
    //
    @Test
    public void testSlopeToIteselfReturnsNegativeInfinity() {
        Point p = new Point(0, 0);
        assertEquals(Double.NEGATIVE_INFINITY, p.slopeTo(p), 0.);
    }

    @Test
    public void testSlopeToReturnsInfinityOnVerticalLine() {
        Point p1 = new Point(0, 1);
        Point p2 = new Point(0, 2);
        assertEquals(Double.POSITIVE_INFINITY, p1.slopeTo(p2), 0.);
        assertEquals(Double.POSITIVE_INFINITY, p2.slopeTo(p1), 0.);
    }

    @Test
    public void testSlopeToIsZeroForHorizontalLine() {
        Point p1 = new Point(0, 1);
        Point p2 = new Point(1, 1);
        assertEquals(0., p1.slopeTo(p2), 0.);
        assertEquals(0., p2.slopeTo(p1), 0.);
    }

    @Test
    public void testNonTrivialSlopes() {
        Point p1 = new Point(0, 1);
        Point p2 = new Point(1, 2);
        Point p3 = new Point(1, 0);
        assertEquals(1., p1.slopeTo(p2), 0.);
        assertEquals(1, p2.slopeTo(p1), 0.);
        assertEquals(-1., p1.slopeTo(p3), 0.);
        assertEquals(-1, p3.slopeTo(p1), 0.);
    }

    //
    // slope order compartor tests
    //
    @Test
    public void testDoesNotReorderAlreadyOrderedPoints() {
        Point origin = new Point(0, 0);
        Point[] pointsInOrder = getPointsInOrderOfSlopeToOrigin(origin);
        Point[] sorted = Arrays.copyOf(pointsInOrder, pointsInOrder.length);
        Arrays.sort(sorted, origin.SLOPE_ORDER);
        assertArrayEquals(pointsInOrder, sorted);
    }
    
    @Test
    public void testShuffledArrayGetsPutInOrder() {
        Point origin = new Point(0, 0);
        Point[] pointsInOrder = getPointsInOrderOfSlopeToOrigin(origin);
        Point[] sorted = Arrays.copyOf(pointsInOrder, pointsInOrder.length);
        StdRandom.shuffle(sorted);
        Arrays.sort(sorted, origin.SLOPE_ORDER);
        assertArrayEquals(pointsInOrder, sorted);        
    }

    /*
     * 100  10 9    8      7
     *      |             
     * 50   |              6
     *      |             
     * 0    0--------------5
     *      |             
     * 50   |              4
     *      |             
     * -100 |  1    2      3
     * 
     *      0  1    50     100
     */
    private Point[] getPointsInOrderOfSlopeToOrigin(Point origin) {
        Point[] pointsInOrder = { origin, new Point(1, -100),
                new Point(50, -100), new Point(100, -100), new Point(100, -50),
                new Point(100, 0), new Point(100, 50), new Point(100, 100),
                new Point(50, 100), new Point(1, 100), new Point(0, 100) };
        return pointsInOrder;
    }

}
