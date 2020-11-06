import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int[][] tiles;
    private int hamming = 0;
    private int manhattan = 0;
    private int blankI;
    private int blankJ;
    private Iterable<Board> neighbours;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
        int gridSize = tiles.length * tiles[0].length;

        for (int cnt = 0; cnt < gridSize; cnt++) {
            int i = cnt / tiles.length;
            int j = cnt % tiles.length;
            int goal = cnt + 1;
            int actual = tiles[i][j];
            if (actual == 0) {
                blankI = i;
                blankJ = j;
            } else {
                if (actual != goal) {
                    hamming++;

                    int goalI = (actual - 1) / tiles.length;
                    int goalJ = (actual - 1) % tiles.length;
                    manhattan += Math.abs(i - goalI) + Math.abs(j - goalJ);
                }
            }
        }

    }

    // string representation of this board
    public String toString() {
        String result = "" + tiles.length;
        for (int[] row : tiles) {
            result += "\n";
            for (int c : row) {
                result += " " + c;
            }
        }
        result += "\n";
        return result;
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y instanceof Board) {
            Board other = (Board) y;
            return this.dimension() == other.dimension()
                    && this.hamming() == other.hamming()
                    && this.manhattan() == other.manhattan();
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (neighbours == null) {
            neighbours = generateNeighbours();
        }
        return neighbours;
    }

    private Iterable<Board> generateNeighbours() {
        final List<Board> neighbours = new ArrayList<>(4);
        if (blankJ > 0) {
            int[][] other = copyBoardArray();
            other[blankI][blankJ] = other[blankI][blankJ - 1];
            other[blankI][blankJ - 1] = 0;
            neighbours.add(new Board(other));
        }
        if (blankJ < tiles[0].length - 1) {
            int[][] other = copyBoardArray();
            other[blankI][blankJ] = other[blankI][blankJ + 1];
            other[blankI][blankJ + 1] = 0;
            neighbours.add(new Board(other));
        }
        if (blankI > 0) {
            int[][] other = copyBoardArray();
            other[blankI][blankJ] = other[blankI - 1][blankJ];
            other[blankI - 1][blankJ] = 0;
            neighbours.add(new Board(other));
        }
        if (blankI < tiles[0].length - 1) {
            int[][] other = copyBoardArray();
            other[blankI][blankJ] = other[blankI + 1][blankJ];
            other[blankI + 1][blankJ] = 0;
            neighbours.add(new Board(other));
        }
        return neighbours;
    }

    private int[][] copyBoardArray() {
        int[][] other = new int[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, other[i], 0, tiles[i].length);
        }
        return other;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        final Board board = new Board(new int[][] {
                {8,1,3},
                {4,0,2},
                {7,6,5}
        });
        verifyHamming(board, 5);
        verifyManhattan(board, 10);
        verifyIsGoal(board, false);
        verifyIsGoal(new Board(new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,0}
        }), true);
        verifyEquals(board,  new Board(new int[][] {
                {8,1,3},
                {4,0,2},
                {7,6,5}
        }), true);
        verifyEquals(board,  new Board(new int[][] {
                {4,1,3},
                {8,0,2},
                {7,6,5}
        }), false);
        verifyNeighbours(new Board(new int[][] {
                {1,0,3},
                {4,2,5},
                {7,8,6}
        }), new Board(new int[][] {
                {0,1,3},
                {4,2,5},
                {7,8,6}
        }), new Board(new int[][] {
                {1,2,3},
                {4,0,5},
                {7,8,6}
        }), new Board(new int[][] {
                {1,3,0},
                {4,2,5},
                {7,8,6}
        }));
    }

    private static void verifyHamming(final Board board, int expectedHamming) {
        assert board.hamming() == expectedHamming : String.format("Actual: %d, Expected: %d", board.hamming(), expectedHamming);
    }

    private static void verifyManhattan(final Board board, int expectedManhattan) {
        assert board.manhattan() == expectedManhattan : String.format("Actual: %d, Expected: %d", board.manhattan(), expectedManhattan);
    }

    private static void verifyIsGoal(final Board board, boolean isGoalResult) {
        assert board.isGoal() == isGoalResult;
    }

    private static void verifyEquals(final Board b1, final Board b2, final boolean expectedResult) {
        assert b1.equals(b2) == expectedResult;
    }

    private static void verifyNeighbours(final Board board, final Board... neighbours) {
        final List<String> expected = new ArrayList<>();
        for (Board neighbour : neighbours) {
            expected.add(neighbour.toString());
        }
        for (Board b : board.neighbors()) {
            assert expected.contains(b.toString());
        }
        assert expected.size() == neighbours.length;
    }


}
