import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class Board {
    
    private final int highestNum;
    private final int n;
    private final short[][] blocks;
    private int hamming = -1;
    private int manhattan = -1;
    private int zeroRow = -1;
    private int zeroCol = -1;

    public Board(int[][] blocks) {
        n = blocks.length;
        this.blocks = new short[n][n];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < n; j++) {
                int val = blocks[i][j];
                if (val == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
                this.blocks[i][j] = (short) val;
            }
        }
        highestNum = (int) (Math.pow(n, 2) - 1);
        hamming = calcHamming();
        manhattan = calcManhattan();
    }
    
    private Board(short[][] blocks, int hamming, int manhattan, int zeroRow, int zeroCol) {
        n = blocks.length;
        this.blocks = new short[n][n];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
        highestNum = (int) (Math.pow(n, 2) - 1);
        this.hamming = hamming;
        this.manhattan = manhattan;
        this.zeroRow = zeroRow;
        this.zeroCol = zeroCol;
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        return hamming;
        
    }

    private int calcHamming() {
        int result = 0;
        for (int i = 0; i <= highestNum; i++) {
            int row = rowForIndex(i);
            int col = colForIndex(i);
            int expected = i + 1;
            int actual = blocks[row][col];
            if (actual != expected && actual != 0) {
                result++;
            }
        }
        return result;
    }

    public int manhattan() {
        return manhattan;
    }
    
    private int calcManhattan() {
        int result = 0;
        for (int i = 0; i <= highestNum; i++) {
            int row = rowForIndex(i);
            int col = colForIndex(i);
            int expected = i + 1;
            int actual = blocks[row][col];
            if (actual != expected && actual != 0) {
                int goalRow = rowForIndex(actual - 1);
                int goalCol = colForIndex(actual - 1);
                result += Math.abs(goalRow - row) + Math.abs(goalCol - col);
            }
        }
        return result;
    }

    public boolean isGoal() {
        boolean isGoal = true;
        for (int i = 0; i < highestNum && isGoal; i++) {
            isGoal &= blocks[rowForIndex(i)][colForIndex(i)] == i + 1;
        }
        return isGoal;
    }

    public Board twin() {
        Board twin = new Board(blocks, hamming, manhattan, zeroRow, zeroCol);
        for (int i = 0; i < n; i++) {
            if (blocks[i][0] != 0 && blocks[i][1] != 0) {
                twin.swap(i, 0, i, 1);
                twin.hamming = twin.calcHamming();
                twin.manhattan = twin.calcManhattan();
                break;
            }
        }
        return twin;
    }

    private void swap(int row1, int col1, int row2, int col2) {
        short val1 = blocks[row1][col1];
        short val2 = blocks[row2][col2];
        if (val1 != 0) {
            updateDistances(row1, col1, row2, col2, val1);
        } else {
            zeroRow = row2;
            zeroCol = col2;
        }
        if (val2 != 0) {
            updateDistances(row2, col2, row1, col1, val2);
        } else {
            zeroRow = row1;
            zeroCol = col2;
        }
        blocks[row1][col1] = val2;
        blocks[row2][col2] = val1;
    }

    private void updateDistances(int fromRow, int fromCol, int toRow, int toCol,
            short val) {
        int homeRow = rowForIndex(val-1);
        int homeCol = colForIndex(val-1);
        
        //update hamming
        if (fromRow == homeRow && fromCol == homeCol) {
            hamming = hamming() + 1;
        } else if (toRow == homeRow && toCol == homeCol) {
            hamming = hamming() - 1;
        }
        
        //update manhattan
        if (Math.abs(fromRow - homeRow) > Math.abs(toRow - homeRow) || Math.abs(fromCol - homeCol) > Math.abs(toCol - homeCol)) {
            manhattan--;
        } else if (Math.abs(fromRow - homeRow) < Math.abs(toRow - homeRow) || Math.abs(fromCol - homeCol) < Math.abs(toCol - homeCol)) {
            manhattan++;
        }
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

        if (zeroCol > 0) {
            Board b = new Board(blocks, hamming, manhattan, zeroRow, zeroCol);
            b.swap(zeroRow, zeroCol, zeroRow, zeroCol - 1);
            neighbors.add(b);
        }
        if (zeroCol < n - 1) {
            Board b = new Board(blocks, hamming, manhattan, zeroRow, zeroCol);
            b.swap(zeroRow, zeroCol, zeroRow, zeroCol + 1);
            neighbors.add(b);
        }
        if (zeroRow > 0) {
            Board b = new Board(blocks, hamming, manhattan, zeroRow, zeroCol);
            b.swap(zeroRow, zeroCol, zeroRow - 1, zeroCol);
            neighbors.add(b);
        }
        if (zeroRow < n - 1) {
            Board b = new Board(blocks, hamming, manhattan, zeroRow, zeroCol);
            b.swap(zeroRow, zeroCol, zeroRow + 1, zeroCol);
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
                b.append(String.format("%2d", blocks[i][j]));
            }
        }
        return b.toString();
    }

    private int colForIndex(int i) {
        return i % n;
    }

    private int rowForIndex(int i) {
        return i / n;
    }
}
