import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {
    private static final Comparator<SearchNode> HAMMING_PRIORITY = (a, b) -> (a.board.hamming() + a.moves) - (b.board.hamming() + b.moves);
    private static final Comparator<SearchNode> MANHATTAN_PRIORITY = (a, b) -> (a.board.manhattan() + a.moves) - (b.board.manhattan() + b.moves);

    private Iterable<Board> solution = null;
    private boolean isSolved = false;
    private int moves = 0;

    static class SearchNode {
        final Board board;
        final int moves;
        final SearchNode previous;

        public SearchNode(final Board board, final int moves, final SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        private Iterable<Board> getPath() {
            final List<Board> result = new ArrayList<>();
            SearchNode search = this;
            do {
                result.add(0, search.board);
                search = search.previous;
            } while (search != null);
            return result;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Provided board must not be null");
        final List<Board> visited = new ArrayList<>();
        final MinPQ<SearchNode> priorityQueue = new MinPQ<>(MANHATTAN_PRIORITY);

        priorityQueue.insert(new SearchNode(initial, 0, null));

        while (!priorityQueue.isEmpty()) {
            final SearchNode search = priorityQueue.delMin();
            if (search.board.isGoal()) {
                isSolved = true;
                moves = search.moves;
                solution = search.getPath();
                break;
            } else {
                visited.add(search.board);
                for (Board neighbour : search.board.neighbors()) {
                    if (visited.contains(neighbour)) continue;
                    priorityQueue.insert(new SearchNode(neighbour, search.moves + 1, search));
                }
            }
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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

}
