import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Solver {
  
    private static final int TWIN_PENALTY_AMOUNT = 10;
    private int moves;
    private List<Board> solution;

    public Solver(Board initial) {
        Node previous = null;
        
        MinPQ<Node> queue = new MinPQ<Node>(getComparator());
        
        queue.insert(new Node(initial, null, 0));
        queue.insert(new Node(initial.twin(), null, 0, TWIN_PENALTY_AMOUNT));
        
        boolean solved = false;
        int count = 0;
        while (!solved && !queue.isEmpty()) {
            if (count % 100000 == 1) {
                System.out.println("Steps: " + count + "; queue size: " + queue.size() + "; current priority: " + previous.priority + "; current moves: " + previous.moves);
            }
            previous = checkQueue( queue);
            
            solved = previous.board.isGoal();
            if (solved) {
                break;
            }
            count++;
        }
        
        if (solved == false) {
            solution = null;
            moves = -1;
        } else {
            solution = new ArrayList<Board>(); 
            List<Board> path = new ArrayList<Board>();
            while (previous != null) {
                path.add(previous.board);
                previous = previous.cameFrom;
            }
            moves = path.size() - 1;
            for (int i=moves; i >= 0; i--) {
                solution.add(path.get(i));
            }
            if ( ! solution.contains(initial)) {
                solution = null;
                moves = -1;
            }
        }
    }

    private Node checkQueue(MinPQ<Node> queue) {
        Node searchNode = queue.delMin();
        if (searchNode.board.isGoal()) {
            return searchNode;
        }
        int moves = searchNode.moves + 1;
        Iterable<Board> neighbors = searchNode.board.neighbors();
        for (Board board : neighbors) {
            Node newNode = new Node(board, searchNode, moves);
            if (searchNode.cameFrom == null || ! newNode.board.equals(searchNode.cameFrom.board)) {
                queue.insert(newNode);
            } 
        }
        return searchNode;
    }

    public boolean isSolvable() {
        return solution != null;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private Comparator<Node> getComparator() {
        return new Comparator<Node>() {

            public int compare(Node o1, Node o2) {
                if (o1.priority == o2.priority) {
                    return Integer.compare(o1.rating, o2.rating);
                }
                return Integer.compare(o1.priority, o2.priority);
            }
        };
    }
    
    private static class Node {
        
        Board board;
        Node cameFrom;
        int moves;
        int rating;
        int priority;
        
        public Node(Board board, Node cameFrom, int moves) {
            this.board = board;
            this.cameFrom = cameFrom;
            this.moves = moves;
            this.rating = board.manhattan();
            this.priority = moves + rating ;
        }
        public Node(Board board, Node cameFrom, int moves, int penalty) {
            this.board = board;
            this.cameFrom = cameFrom;
            this.moves = moves;
            this.rating = board.manhattan();
            this.priority = moves + rating + penalty;
        }
        
        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            b.append(board.manhattan()).append(" + ").append( moves).append(" moves = ").append( priority ).append("\n");
            b.append(board.toString());
            return b.toString();
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (! (obj instanceof Node)) return false;
            Node other = (Node) obj;
            return Objects.equals(board, other.board);
        }
        @Override
        public int hashCode() {
            return Objects.hash(board);
        }
    }
}
