public class Board {

    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
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
        // is this board the goal board?
        return false;
    }

    public Board twin() {
        // a boadr that is obtained by exchanging two adjacent blocks in the
        // same row
        return null;
    }

    public boolean equals(Object y) {
        // does this board equal y?
        return false;
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
