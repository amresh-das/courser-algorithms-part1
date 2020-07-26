import java.util.Arrays;

public class BruteCollinearPoints {
    private final Point[] points;
    private int segmentCount;
    private LineSegment[] segments;

    // finds all line segments containing 4 argPoints
    public BruteCollinearPoints(Point[] argPoints) {
        if (argPoints == null) throw new IllegalArgumentException("Points should not be null");
        int count = 0;
        this.points = new Point[argPoints.length];
        this.segments = new LineSegment[argPoints.length + 1];
        for (Point point : argPoints) {
            if (point == null) throw new IllegalArgumentException("Any of the Point provided should not be null");
            points[count++] = point;
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].slopeTo(points[i + 1]) == Double.NEGATIVE_INFINITY)
                throw new IllegalArgumentException("Provided points must be distinct");
        }
        compute();
    }

    private void compute() {
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                double p01 = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length - 1; k++) {
                    double p02 = points[i].slopeTo(points[k]);
                    if (p01 == p02) {
                        for (int l = k + 1; l < points.length; l++) {
                            double p03 = points[i].slopeTo(points[l]);
                            if (p02 == p03) {
                                segments[segmentCount++] = new LineSegment(points[i], points[l]);
                            }
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentCount;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segmentCount);
    }

    public static void main(final String... args) {
        Point[] points = {
                new Point(12, 10),
                new Point(12, 20),
                new Point(12, 30),
                new Point(12, 40),
                new Point(100, 10),
                new Point(100, 20),
                new Point(100, 30),
                new Point(100, 40)
        };
        BruteCollinearPoints unit = new BruteCollinearPoints(points);
        assert unit.numberOfSegments() == 2 : "Should indicate 2 segments";
    }

}
