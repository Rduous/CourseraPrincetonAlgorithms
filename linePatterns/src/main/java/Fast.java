import java.util.Arrays;

public class Fast {

    public static void main(String[] args) {
        String fileName = args[0];
        In in = new In(fileName);
        int n = in.readInt();
        Point[] points = new Point[n];

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point point = new Point(x, y);
            points[i] = point;
            point.draw();
        }
        Arrays.sort(points);
        findAndPrintLineSegments(points);
    }

    static void findAndPrintLineSegments(Point[] naturalOrderPoints) {
        int n = naturalOrderPoints.length;
        Point[] points = Arrays.copyOf(naturalOrderPoints, n);
        for (int i = 0; i < n - 3; i++) {
            Point p = naturalOrderPoints[i];
            Arrays.sort(points, p.SLOPE_ORDER);
            double slope = Double.NEGATIVE_INFINITY;
            int numPoints = 0;
            int sequenceStartIndex = 0;
            for (int j = 0; j < points.length; j++) {
                double nextSlope = p.slopeTo(points[j]);
                if (slope == nextSlope) {
                    numPoints++;
                } else {
                    // slope has changed
                    // check current line segment length, do stuff if necessary,
                    // and reset
                    if (numPoints >= 3) {
                        printAndDrawLineSegment(p, points, sequenceStartIndex,
                                numPoints);
                    }
                    sequenceStartIndex = j;
                    numPoints = 1;
                    slope = nextSlope;
                }
            }
            if (numPoints >= 3) {
                printAndDrawLineSegment(p, points, sequenceStartIndex,
                        numPoints);
            }
        }
    }

    private static void printAndDrawLineSegment(Point point,
            Point[] otherPoints, int sequenceStartIndex, int numPoints) {

        // check that this isn't a smaller subsegment
        Point[] toPrint = new Point[numPoints + 1];
        toPrint[0] = point;
        System.arraycopy(otherPoints, sequenceStartIndex, toPrint, 1, numPoints);
        Arrays.sort(toPrint);
        Point endPoint = toPrint[numPoints];
        Point startPoint = toPrint[0];
        if (point == startPoint) {
            for (int i = 0; i < numPoints; i++) {
                System.out.print(toPrint[i] + " -> ");
            }
            System.out.println(endPoint);
            point.drawTo(endPoint);
        }
    }
}
