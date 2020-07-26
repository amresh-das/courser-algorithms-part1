/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        if (this.y == that.y) return 0.0;
        return (double) (that.y - this.y) / (double) (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        final int yCompare = Integer.compare(this.y, that.y);
        return yCompare == 0 ? Integer.compare(this.x, that.x) : yCompare;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return (p1, p2) -> {
            double p1Slope = slopeTo(p1);
            double p2Slope = slopeTo(p2);
            if (p1Slope == Double.NEGATIVE_INFINITY) return p2Slope == Double.NEGATIVE_INFINITY ? 0 : -1;
            if (p1Slope == Double.POSITIVE_INFINITY) return p2Slope == Double.POSITIVE_INFINITY ? 0 : 1;
            if (p2Slope == Double.POSITIVE_INFINITY) return -1;
            if (p2Slope == Double.NEGATIVE_INFINITY) return 1;
            return Double.compare(p1Slope, p2Slope);
        };
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p1;
        Point p2;
        Point p0;

        p1 = new Point(1, 1);
        p2 = new Point(1, 1);
        assert p1.compareTo(p2) == 0 : "Equal Points should campareTo zero";
        assert p1.slopeTo(p2) == Double.NEGATIVE_INFINITY : "Equal Points should have slope of Negative Infinity";

        p1 = new Point(1, 5);
        p2 = new Point(1, -5);
        assert p1.compareTo(p2) > 0 : "Only y coordinate should determine comparison when unequal";
        assert p1.slopeTo(p2) == Double.POSITIVE_INFINITY : "Vertical line must have Positive infinity slow";

        p1 = new Point(-1, 5);
        p2 = new Point(1, 5);
        assert p1.compareTo(p2) < 0 : "Only x coordinate should determine comparison when y is equal";
        assert p1.slopeTo(p2) == 0.0 : "Horizontal line must have 0 slope";

        p1 = new Point(1, 3);
        p2 = new Point(4, 7);
        assert p1.slopeTo(p2) == (double) (7 - 3) / (double) (4 - 1) : "Should compute slope";

        p0 = new Point(0, 0);
        p1 = new Point(1, -1);
        p2 = new Point(-1, 1);
        Comparator<Point> comparator = p0.slopeOrder();
        assert comparator.compare(p1, p2) == 0 : "Should have zero slope";
        assert comparator.compare(p2, p1) == 0 : "Should have zero slope";

        p1 = new Point(5, 5);
        p2 = new Point(2, 3);
        assert comparator.compare(p1, p2) < 0 : "P1 Slope should be less than P2";
        assert comparator.compare(p2, p1) > 0 : "P1 Slope should be greater than P2";
    }

}