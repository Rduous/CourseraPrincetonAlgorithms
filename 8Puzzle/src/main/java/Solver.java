import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solver {
    

    private final int moves;
    private final List<Board> solution = new ArrayList<Board>();

    public Solver(Board initial) {
        Board previous = null;
        Board previousTwin = null;
                
        Map<Board, Board> cameFrom = new HashMap<Board, Board>();
        
        MinPQ<Board> queue = new MinPQ<Board>(getComparator());
        MinPQ<Board> twinQueue = new MinPQ<Board>(getComparator());
        
        queue.insert(initial);
        twinQueue.insert(initial.twin());
        
        boolean solved = false;
        while (!solved && !queue.isEmpty()) {
            
            Board next = checkQueue(previous, queue);
            cameFrom.put(next, previous);
            previous = next;
            
            solved = previous.isGoal();
            if (solved) {
                break;
            }
            previousTwin = checkQueue(previousTwin, twinQueue);
            if (previousTwin.isGoal()) {
                break;
            }
        }
        
        if (solved == false) {
            moves = -1;
        } else {
            List<Board> path = new ArrayList<Board>();
            while (previous != null) {
                path.add(previous);
                previous = cameFrom.get(previous);
            }
            moves = path.size() - 1;
            for (int i=moves -1; i >= 0; i--) {
                solution.add(path.get(i));
            }
        }
    }

    private Board checkQueue(Board previous, MinPQ<Board> queue) {
        Board searchNode = queue.delMin();
        // add this node to tree
        if (searchNode.isGoal()) {
            return searchNode;
        }
        Iterable<Board> neighbors = searchNode.neighbors();
        for (Board board : neighbors) {
            if (!board.equals(previous)) {
                queue.insert(board);
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

    private Comparator<Board> getComparator() {
        return new Comparator<Board>() {

            public int compare(Board o1, Board o2) {
                return Integer.compare(o1.manhattan(), o2.manhattan());
            }
        };
    }
}
