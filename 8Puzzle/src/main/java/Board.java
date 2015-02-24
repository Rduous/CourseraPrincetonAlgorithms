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
        return n;
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
        Board twin = new Board(blocks);
        for (int i = 0; i < n; i++) {
            if (blocks[i][0] != 0 && blocks[i][1] != 0) {
                twin.swap(i, 0, i, 1);
                break;
            }
        }
        return twin;
    }

    private void swap(int row1, int col1, int row2, int col2) {
        int val = blocks[row1][col1];
        blocks[row1][col1] = blocks[row2][col2];
        blocks[row2][col2] = val;
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
            b.swap(row, col, row, col - 1);
            neighbors.add(b);
        }
        if (col < n - 1) {
            Board b = new Board(blocks);
            b.swap(row, col, row, col + 1);
            neighbors.add(b);
        }
        if (row > 0) {
            Board b = new Board(blocks);
            b.swap(row, col, row - 1, col);
            neighbors.add(b);
        }
        if (row < n - 1) {
            Board b = new Board(blocks);
            b.swap(row, col, row + 1, col);
            neighbors.add(b);
        }
        return neighbors;
    }

    public String toString() {
        int formatNum = Integer.toString(highestNum).length();
        StringBuilder b = new StringBuilder();
        b.append(n);
        for (int i = 0; i < blocks.length; i++) {
            b.append("\n");
            for (int j = 0; j < n; j++) {
                b.append(" ");
                b.append(String.format("%" + formatNum + "d", blocks[i][j]));
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
