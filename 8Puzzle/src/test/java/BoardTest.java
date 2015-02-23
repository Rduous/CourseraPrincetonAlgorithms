import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    
    private static final int[][] ALMOST_FINAL = new int[][] { { 1, 2, 3 },
            { 4, 5, 6 }, { 7, 0, 8 } };
    private static final int[][] ZERO_AT_2_1 = new int[][] { { 1, 2, 3 },
        { 0, 4, 5 }, { 7, 8, 6 } };
    private static final int[][] ZERO_IN_MIDDLE = new int[][] { { 1, 2, 3 },
        { 4, 0, 5 }, { 7, 8, 6 } };
    private static final int[][] FINAL = new int[][] { { 1, 2, 3 },
            { 4, 5, 6 }, { 7, 8, 0 } };
    
    private Board finalBoard;
    private Board almostFinalBoard;
    private Board zeroInMiddleBoard;
    private Board zeroAtTwoOneBoard;
    
    @Before
    public void setup() {
        finalBoard = new Board(FINAL);
        almostFinalBoard = new Board(ALMOST_FINAL);
        zeroInMiddleBoard = new Board(ZERO_IN_MIDDLE);
        zeroAtTwoOneBoard = new Board(ZERO_AT_2_1);
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
        assertFalse(zeroAtTwoOneBoard.isGoal());
    }
    
    @Test
    public void testABoardEqualsItself() {
        assertEquals(finalBoard,finalBoard);
    }
    
    @Test
    public void testABoardDoesNotEqualNull() {
        assertNotEquals(finalBoard,null);
    }
    
    @Test
    public void testABoardDoesNotEqualANonBoard() {
        assertNotEquals(finalBoard,"Not a board");
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
        Board twin2 = zeroAtTwoOneBoard.twin();
        assertEquals(zeroInMiddleBoard, twin2);
        Board twin3 = zeroInMiddleBoard.twin();
        assertEquals(zeroAtTwoOneBoard, twin3);
    }

}
