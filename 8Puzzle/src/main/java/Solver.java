import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Solver {
    
    private static Map<Node, Integer> priority = new HashMap<Solver.Node, Integer>();
  
    private final int moves;
    private final List<Board> solution;

    public Solver(Board initial) {
        Node previous = null;
        Node previousTwin = null;
        
        MinPQ<Node> queue = new MinPQ<Node>(getComparator());
        MinPQ<Node> twinQueue = new MinPQ<Node>(getComparator());
        
        queue.insert(new Node(initial, null, 0));
        twinQueue.insert(new Node(initial.twin(), null, 0));
        
        boolean solved = false;
        while (!solved && !queue.isEmpty()) {
            previous = checkQueue(previous, queue);//, alreadySeen);
            
            solved = previous.board.isGoal();
            if (solved) {
                break;
            }
            previousTwin = checkQueue(previousTwin, twinQueue);//, alreadySeenTwin);
            if (previousTwin.board.isGoal()) {
                break;
            }
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
        }
    }

    private Node checkQueue(Node previous, MinPQ<Node> queue) {//, Set<Node> alreadySeen) {
        Node searchNode = queue.delMin();
        // add this node to tree
        if (searchNode.board.isGoal()) {
            return searchNode;
        }
        int moves = searchNode.moves + 1;
        Iterable<Board> neighbors = searchNode.board.neighbors();
        for (Board board : neighbors) {
            if (!board.equals(previous)) {
                Node newNode = new Node(board, searchNode, moves);
//                if ( alreadySeen.add(newNode)) {
                    queue.insert(newNode);
//                }
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
                return Integer.compare(o1.board.manhattan() + o1.moves, o2.board.manhattan() + o2.moves);
            }
        };
    }
    
    private static class Node {
        
        Board board;
        Node cameFrom;
        int moves;
        public Node(Board board, Node cameFrom, int moves) {
            this.board = board;
            this.cameFrom = cameFrom;
            this.moves = moves;
        }
        
        @Override
        public String toString() {
            return board.toString();
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
