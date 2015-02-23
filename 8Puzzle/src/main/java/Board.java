import java.util.Objects;

public class Board {
    
    private final int highestNum;
    private final int n;
    private final int[][] blocks;

    public Board(int[][] blocks) {
        this.blocks = blocks;
        n = blocks.length;
        highestNum = (int) (Math.pow(n, 2) - 1); 
    }

    public int dimension() {
        return 0;
        // board dimension N
    }

    public int hamming() {
        // number of blocks out of place
        return 0;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        return 0;
    }

    public boolean isGoal() {
        boolean isGoal = true;
        for (int i = 0; i < highestNum && isGoal; i++) {
            isGoal &= blocks[i/n][i%n] == i + 1;
        }
        return isGoal;
    }

    public Board twin() {
        int[][] blocks2 = new int[n][n];
        for (int i = 0; i < highestNum + 1; i++) {
            int row = i/n;
            int col = i%n;
            int val = blocks[row][col];
            if (val == 0) {
                if (col == 0) {
                    //swap right
                    blocks2[row][col+1] = 0;
                    blocks2[row][col] = blocks[row][col+1];
                    i++;
                } else {
                    //swap left
                    blocks2[row][col] = blocks2[row][col-1];
                    blocks2[row][col-1] = val;
                }
            } else {
                blocks2[row][col] = val;
            }
        }
        return new Board(blocks2);
    }

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if ( ! (y instanceof Board )) {
            return false;
        }
        Board other = (Board) y;
        return Objects.deepEquals(blocks, other.blocks);
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        return null;
    }

    public String toString() {
        // string representation of this board (in the output format specified
        // below)
        return "";
    }

    public static void main(String[] args) {// unit tests (not graded)

    }
}
