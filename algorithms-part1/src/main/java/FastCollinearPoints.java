import java.util.Arrays;

public class FastCollinearPoints {
    private final Point[] points;
    private int segmentCount;
    private LineSegment[] result;
    private Line[] visited;
    private int visitedCount;

    // finds all line segments containing 4 argPoints
    public FastCollinearPoints(Point[] argPoints) {
        if (argPoints == null) throw new IllegalArgumentException("Points should not be null");
        this.result = new LineSegment[argPoints.length / 2];
        this.visited = new Line[argPoints.length / 2];
        for (Point point : argPoints) {
            if (point == null) throw new IllegalArgumentException("Any of the Point provided should not be null");
        }
        this.points = argPoints;
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].slopeTo(points[i + 1]) == Double.NEGATIVE_INFINITY)
                throw new IllegalArgumentException("Provided points must be distinct");
        }
        compute();
    }

    private void compute() {
        for (int i = 0; i < points.length; i++) {
            final Point base = points[i];
            Arrays.sort(points, base.slopeOrder());
            int streak = 0;
            double prevSlope = base.slopeTo(base);
            Point streakStart = points[0];
            for (int j = 1; j < points.length; j++) {
                final Point point = points[j];
                double currentSlope = base.slopeTo(point);
                if (!isVisited(point, currentSlope)) {
                    if (currentSlope == prevSlope) {
                        streak++;
                    } else {
                        if (streak >= 2) {
                            addUnique(base, streakStart, points[j - 1], currentSlope);
                        }
                        streak = 0;
                        streakStart = point;
                    }
                    addVisited(point, currentSlope);
                }
                prevSlope = currentSlope;
            }
            if (streak >= 2) {
                addUnique(base, streakStart, points[points.length - 1], prevSlope);
            }
            Arrays.sort(points);
        }
    }

    private void addUnique(final Point current, final Point start, final Point end, final double slope) {
        final Point p0 = (current.compareTo(start) < 0) ? current : start;
        final Point p1 = (current.compareTo(end) > 0) ? current : end;
        resizeSegmentIfNeeded();
        result[segmentCount++] = new LineSegment(p0, p1);
        visited[segmentCount] = new Line(start, slope);
    }

    private void resizeSegmentIfNeeded() {
        if (segmentCount == result.length - 1) {
            final LineSegment[] newSegment = new LineSegment[result.length + result.length / 2];
            final Line[] newPlanesVisited = new Line[result.length + result.length / 2];
            System.arraycopy(result, 0, newSegment, 0, segmentCount);
            System.arraycopy(visited, 0, newPlanesVisited, 0, segmentCount);
            result = newSegment;
            visited = newPlanesVisited;
        }
    }

    private boolean isVisited(final Point point, final double slope) {
        for (int i = 0; i < visitedCount; i++) {
            if (visited[i].p == point && visited[i].angle == slope) return true;
        }
        return false;
    }

    private void addVisited(final Point point, final double slope) {
        if (visitedCount == visited.length - 1) {
            final Line[] newVisited = new Line[visited.length + visited.length / 2];
            System.arraycopy(visited, 0, newVisited, 0, visitedCount);
            visited = newVisited;
        }
        visited[visitedCount++] = new Line(point, slope);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentCount;
    }

    // the line segments
    public LineSegment[] segments() {
        return result;
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
        FastCollinearPoints unit = new FastCollinearPoints(points);
        assert unit.numberOfSegments() == 2 : "Should indicate 2 segments";
    }

    private class Line {
        Point p;
        double angle;

        public Line(final Point p, final double angle) {
            this.p = p;
            this.angle = angle;
        }
    }

}
