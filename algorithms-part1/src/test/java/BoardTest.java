import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardTest {
    Board b1 = new Board(new int[][] {
            {8,1,3},
            {4,0,2},
            {7,6,5}
    });
    Board b2 = new Board(new int[][] {
            {1,2,3},
            {4,5,6},
            {7,8,0}
    });

    @Test
    void verifyHamming() {
        Assertions.assertThat(b1.hamming()).isEqualTo(5);
    }

    @Test
    void verifyManhattan() {
        Assertions.assertThat(b1.manhattan()).isEqualTo(10);
    }

    @Test
    void verifyManhattan2() {
        final Board board = new Board(new int[][]{
                {4, 3, 1},
                {0, 2, 6},
                {7, 8, 5}
        });
        Assertions.assertThat(b1.manhattan()).isEqualTo(10);
    }

    @Test
    void verifyIsGoal() {
        Assertions.assertThat(b2.isGoal()).isEqualTo(true);
    }

    @Test
    void verifyEquals() {
        final Board b3 = new Board(new int[][] {
                {8,1,3},
                {4,0,2},
                {7,6,5}
        });
        Assertions.assertThat(b1.equals(b3)).isEqualTo(true);
    }

    @Test
    public void verifyNeighbours() {
        final Board board = new Board(new int[][] {
                {1,0,3},
                {4,2,5},
                {7,8,6}
        });
        final Board n1 = new Board(new int[][] {
                {0,1,3},
                {4,2,5},
                {7,8,6}
        });
        final Board n2 = new Board(new int[][] {
                {1,2,3},
                {4,0,5},
                {7,8,6}
        });
        final Board n3 = new Board(new int[][] {
                {1,3,0},
                {4,2,5},
                {7,8,6}
        });
        Assertions.assertThat(board.neighbors()).containsOnly(n1, n2, n3);
    }

}