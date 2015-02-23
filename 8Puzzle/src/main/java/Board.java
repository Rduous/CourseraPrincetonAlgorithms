import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Board {

    private final int highestNum;
    private final int n;
    private final int[][] blocks;

    public Board(int[][] blocks) {
        n = blocks.length;
        this.blocks = new int[n][];
        for (int i = 0; i < blocks.length; i++) {
            this.blocks[i] = Arrays.copyOf(blocks[i], n);
        }
        highestNum = (int) (Math.pow(n, 2) - 1);
    }

    public int dimension() {
        return 0;
        // board dimension N
    }

    public int hamming() {
        int result = 0;
        for (int i = 0; i <= highestNum; i++) {
            int row = rowFor(i);
            int col = colFor(i);
            int expected = i + 1;
            int actual = blocks[row][col];
            if (actual != expected && actual != 0) {
                result++;
            }
        }
        return result;
    }

    public int manhattan() {
        int result = 0;
        for (int i = 0; i <= highestNum; i++) {
            int row = rowFor(i);
            int col = colFor(i);
            int expected = i + 1;
            int actual = blocks[row][col];
            if (actual != expected && actual != 0) {
                int goalRow = rowFor(actual - 1);
                int goalCol = colFor(actual - 1);
                result += Math.abs(goalRow - row) + Math.abs(goalCol - col);
            }
        }
        return result;
    }

    public boolean isGoal() {
        boolean isGoal = true;
        for (int i = 0; i < highestNum && isGoal; i++) {
            isGoal &= blocks[rowFor(i)][colFor(i)] == i + 1;
        }
        return isGoal;
    }

    public Board twin() {
        int[][] blocks2 = new int[n][n];
        for (int i = 0; i < highestNum + 1; i++) {
            int row = rowFor(i);
            int col = colFor(i);
            int val = blocks[row][col];
            if (val == 0) {
                if (col == 0) {
                    swapEmptySpaceRight(blocks2, row, col);
                    i++;
                } else {
                    swapEmptySpaceLeft(blocks2, row, col);
                }
            } else {
                blocks2[row][col] = val;
            }
        }
        return new Board(blocks2);
    }

    private void swapEmptySpaceLeft(int[][] blocks2, int row, int col) {
        blocks2[row][col] = blocks2[row][col - 1];
        blocks2[row][col - 1] = 0;
    }

    private void swapEmptySpaceRight(int[][] blocks2, int row, int col) {
        blocks2[row][col + 1] = 0;
        blocks2[row][col] = blocks[row][col + 1];
    }

    private void swapEmptySpaceUp(int[][] blocks2, int row, int col) {
        blocks2[row][col] = blocks2[row-1][col];
        blocks2[row-1][col] = 0;
    }

    private void swapEmptySpaceDown(int[][] blocks2, int row, int col) {
        blocks2[row+1][col] = 0;
        blocks2[row][col] = blocks[row+1][col];
    }

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (!(y instanceof Board)) {
            return false;
        }
        Board other = (Board) y;
        return Objects.deepEquals(blocks, other.blocks);
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();

        int i = 0;
        int row = 0;
        int col = 0;
        for (; i < highestNum + 1; i++) {
            row = rowFor(i);
            col = colFor(i);
            if (blocks[row][col] == 0) {
                break;
            }
        }
        if (col > 0) {
            Board b = new Board(blocks);
            swapEmptySpaceLeft(b.blocks, row, col);
            neighbors.add(b);
        }
        if (col < n - 1) {
            Board b = new Board(blocks);
            swapEmptySpaceRight(b.blocks, row, col);
            neighbors.add(b);
        }
        if (row > 0) {
            Board b = new Board(blocks);
            swapEmptySpaceUp(b.blocks, row, col);
            neighbors.add(b);
        }
        if (row < n -1) {
            Board b = new Board(blocks);
            swapEmptySpaceDown(b.blocks, row, col);
            neighbors.add(b);
        }
        return neighbors;
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(n);
        for (int i = 0; i < blocks.length; i++) {
            b.append("\n");
            for (int j = 0; j < n; j++) {
                b.append(" ");
                int val = blocks[i][j];
                b.append(val == 0 ? " " : val);
            }
        }
        return b.toString();
    }

    private int colFor(int i) {
        return i % n;
    }

    private int rowFor(int i) {
        return i / n;
    }
}
