import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {

    private static final int[][] ALMOST_FINAL = new int[][] { { 1, 2, 3 },
            { 4, 5, 6 }, { 7, 0, 8 } };
    private static final int[][] ZERO_ON_LEFT_EDGE = new int[][] { { 1, 2, 3 },
            { 0, 4, 5 }, { 7, 8, 6 } };
    private static final int[][] ZERO_IN_MIDDLE = new int[][] { { 1, 2, 3 },
            { 4, 0, 5 }, { 7, 8, 6 } };
    private static final int[][] FINAL = new int[][] { { 1, 2, 3 },
            { 4, 5, 6 }, { 7, 8, 0 } };
    private static final int[][] HAMMING_MANHATTAN_EXAMPLE = new int[][] {
            { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };

    private Board finalBoard;
    private Board almostFinalBoard;
    private Board zeroInMiddleBoard;
    private Board zeroOnLeftEdge;
    private Board hammingManhattanExample;

    @Before
    public void setup() {
        finalBoard = new Board(FINAL);
        almostFinalBoard = new Board(ALMOST_FINAL);
        zeroInMiddleBoard = new Board(ZERO_IN_MIDDLE);
        zeroOnLeftEdge = new Board(ZERO_ON_LEFT_EDGE);
        hammingManhattanExample = new Board(HAMMING_MANHATTAN_EXAMPLE);
    }

    @Test
    public void testIsGoalFalseWhenNumbersNotInFinalState() {
        assertFalse(almostFinalBoard.isGoal());
    }

    @Test
    public void testIsGoalTrueWhenNumbersInFinalState() {
        assertTrue(finalBoard.isGoal());
    }

    @Test
    public void testIsGoalFalseWhenNumbersNotInFinalState2() {
        assertFalse(zeroInMiddleBoard.isGoal());
    }

    @Test
    public void testIsGoalFalseWhenNumbersNotInFinalState3() {
        assertFalse(zeroOnLeftEdge.isGoal());
    }

    @Test
    public void testABoardEqualsItself() {
        assertEquals(finalBoard, finalBoard);
    }

    @Test
    public void testABoardDoesNotEqualNull() {
        assertNotEquals(finalBoard, null);
    }

    @Test
    public void testABoardDoesNotEqualANonBoard() {
        assertNotEquals(finalBoard, "Not a board");
    }

    @Test
    public void testABoardDoesNotEqualADifferentBoard() {
        assertNotEquals(finalBoard, almostFinalBoard);
    }

    @Test
    public void testTwinReturnsNonNullBoard() {
        Board twin = finalBoard.twin();
        assertNotNull(twin);
    }

    @Test
    public void testTwinReturnsADifferentBoard() {
        Board twin = finalBoard.twin();
        assertNotEquals(finalBoard, twin);
    }

    @Test
    public void testTwinReturnsABoardThatIsCreatedWithOneMove() {
        Board twinOfFinal = finalBoard.twin();
        assertEquals(twinOfFinal, almostFinalBoard);
        Board twin2 = zeroOnLeftEdge.twin();
        assertEquals(zeroInMiddleBoard, twin2);
        Board twin3 = zeroInMiddleBoard.twin();
        assertEquals(zeroOnLeftEdge, twin3);
    }

    @Test
    public void testManhattanNumberZeroOnFinalBoard() {
        assertEquals(0, finalBoard.manhattan());
    }

    @Test
    public void testManhattanNumberOfNearFinalBoard() {
        assertEquals(1, almostFinalBoard.manhattan());
    }

    @Test
    public void testManhattanNumberOfZeroInMiddleBoard() {
        assertEquals(2, zeroInMiddleBoard.manhattan());
    }

    @Test
    public void testManhattanNumberOfZeroAtTwoOne() {
        assertEquals(3, zeroOnLeftEdge.manhattan());
    }

    @Test
    public void testManhattanOnExampleBoard() {
        assertEquals(10, hammingManhattanExample.manhattan());
    }

    @Test
    public void testHammingNumberZeroOnFinalBoard() {
        assertEquals(0, finalBoard.hamming());
    }

    @Test
    public void testHammingNumberOnNearFinalBoard() {
        assertEquals(1, almostFinalBoard.hamming());
    }

    @Test
    public void testHammingNumberOfZeroInMiddleBoard() {
        assertEquals(2, zeroInMiddleBoard.hamming());
    }

    @Test
    public void testHammingNumberOfZeroAtTwoOne() {
        assertEquals(3, zeroOnLeftEdge.hamming());
    }

    @Test
    public void testHammingOnExampleBoard() {
        assertEquals(5, hammingManhattanExample.hamming());
    }

    @Test
    public void testNeighborsNonNull() {
        assertNotNull(finalBoard.neighbors());
    }

    @Test
    public void testIteratorHasFourBoardsWhenZeroInTheMiddle() {
        Iterable<Board> neighbors = zeroInMiddleBoard.neighbors();
        int num = 0;
        for (Board n : neighbors) {
            num++;
        }
        assertEquals(4, num);
    }

    @Test
    public void testIteratorHasTwoBoardsWhenZeroInTheCorner() {
        Iterable<Board> neighbors = finalBoard.neighbors();
        int num = 0;
        for (Board n : neighbors) {
            num++;
        }
        assertEquals(2, num);
    }

    @Test
    public void testIteratorHasTwoBoardsWhenZeroOnAnEdge() {
        Iterable<Board> neighbors = zeroOnLeftEdge.neighbors();
        int num = 0;
        for (Board n : neighbors) {
            num++;
        }
        assertEquals(3, num);
    }

    /*
     * Check that all the neighbors are unique
     */
    @Test
    public void testNeighbors() {
        List<Board> boards = new ArrayList<Board>();
        Iterable<Board> neighbors = zeroInMiddleBoard.neighbors();
        for (Board n : neighbors) {
            assertNotEquals(zeroInMiddleBoard, n);
            assertFalse(boards.contains(n));
            boards.add(n);
            assertTrue(boards.contains(n));
        }
        assertTrue(boards.contains(zeroOnLeftEdge));
    }

}
