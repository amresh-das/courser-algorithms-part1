package percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private enum State {
        OPEN,
        FULL
    }

    private final WeightedQuickUnionUF unionFind;
    private final State[] sites;
    private final int topIndex;
    private int topRoot;
    private final int bottomIndex;
    private final int n;
    private int openSiteCount;
    private boolean percolates = false;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be > 0");
        this.n = n;
        unionFind = new WeightedQuickUnionUF(n * n + 1);
        sites = new State[n * n + 1];
        openSiteCount = 0;
        topIndex = 0;
        bottomIndex = n * n + 1;
        sites[topIndex] = State.FULL;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int index = computeIndex(row, col);
        if (sites[index] == null) {
            sites[index] = State.OPEN;
            openSiteCount++;
            if (row == 1) {
                union(topIndex, index);
            }
            connectOpenSite(index, index - n);
            connectOpenSite(index, index + n);
            if (col > 1) connectOpenSite(index, index - 1);
            if (col < n) connectOpenSite(index, index + 1);
            topRoot = unionFind.find(topIndex);
        }
    }

    private void connectOpenSite(final int origin, final int target) {
        if (target >= topIndex && target < bottomIndex) {
            if (sites[target] != null) {
                union(origin, target);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int index = computeIndex(row, col);
        return sites[index] != null;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int index = computeIndex(row, col);
        if (sites[index] == State.OPEN) {
            sites[index] = unionFind.find(index) == topRoot ? State.FULL : sites[index];
        }
        return sites[index] == State.FULL;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        if (!percolates) {
            for (int i = 1; i <= n; i++) {
                int index = bottomIndex - i;
                if (sites[index] == State.FULL || (sites[index] == State.OPEN && unionFind.find(index) == topRoot)) {
                    percolates = true;
                    break;
                }
            }
        }
        return percolates;
    }

    private void union(int a, int b) {
        unionFind.union(a, b);
    }

    private int computeIndex(final int row, final int col) {
        return ((row - 1) * n) + col;
    }

    private void validate(final int row, final int col) {
        if (!(row >= 1 && row <= n)) throw new IllegalArgumentException(String.format("Row must be between 1 and %d; was %d", n, row));
        if (!(col >= 1 && col <= n)) throw new IllegalArgumentException(String.format("Row must be between 1 and %d; was %d", n, col));
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        percolation.open(3, 1);
        percolation.open(2, 1);
        StdOut.printf("Expected true, Actual %s", percolation.percolates());
    }
}
