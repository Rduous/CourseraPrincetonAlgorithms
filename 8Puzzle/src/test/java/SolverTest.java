import org.junit.Test;
import static org.junit.Assert.*;

public class SolverTest {

    @Test
    public void testSolvable2x2Board() {
        Board board = new Board(new int[][] {{1,2},{0,3}});
        Solver solver = new Solver(board);
        assertTrue(solver.isSolvable());
        assertEquals(1, solver.moves());
        int num = 0;
        Iterable<Board> solution = solver.solution();
        for (Board b : solution) {
            num++;
        }
        assertEquals(2, num);
    }

    @Test
    public void testSolvable2x2Board2() {
        Board board = new Board(new int[][] {{1,0},{3,2}});
        Solver solver = new Solver(board);
        assertTrue(solver.isSolvable());
        assertEquals(1, solver.moves());
        int num = 0;
        Iterable<Board> solution = solver.solution();
        for (Board b : solution) {
            num++;
        }
        assertEquals(2, num);
    }

    @Test
    public void testSolvable2x2Board3() {
        Board board = new Board(new int[][] {{0, 1},{3,2}});
        Solver solver = new Solver(board);
        assertTrue(solver.isSolvable());
        assertEquals(2, solver.moves());
        int num = 0;
        Iterable<Board> solution = solver.solution();
        for (Board b : solution) {
            num++;
        }
        assertEquals(3, num);
    }

    @Test
    public void testSolvable2x2Board4() {
        Board board = new Board(new int[][] {{0, 2},{1, 3}});
        Solver solver = new Solver(board);
        assertTrue(solver.isSolvable());
        assertEquals(2, solver.moves());
        int num = 0;
        Iterable<Board> solution = solver.solution();
        for (Board b : solution) {
            num++;
        }
        assertEquals(3, num);
    }
    
    @Test
    public void testUnsolvable2x2Board() {
        Board board = new Board(new int[][] {{1,2},{0,3}});
        Board twin = board.twin();
        Solver solver = new Solver(twin);
        assertEquals(-1, solver.moves());
        assertNull(solver.solution());
        assertFalse(solver.isSolvable());
    }
    
    @Test (expected = NullPointerException.class)
    public void testThrowsErrorOnNullArgument() {
        new Solver(null);
    }
    
    @Test
    public void test2x2ExampleFromGrader() {
        Board board = new Board(new int[][] {{3,2},{1,0}});
        Solver solver = new Solver(board);
        assertEquals(-1, solver.moves());
        assertFalse(solver.isSolvable());
        assertNull(solver.solution());
    }
}
