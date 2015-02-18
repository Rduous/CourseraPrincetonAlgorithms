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
        findAndPrintLineSegments(points);
    }

    static void findAndPrintLineSegments(Point[] input) {
        Arrays.sort(input);
        int n = input.length;
        Point[] pointsSortedBySlope = Arrays.copyOf(input, n);
        for (int i = 0; i < n - 3; i++) {
            Point p = input[i];
            Arrays.sort(pointsSortedBySlope, p.SLOPE_ORDER);
            double prevSlope = Double.NEGATIVE_INFINITY;
            int sequenceStartIndex = 0;
            for (int j = 1; j < pointsSortedBySlope.length; j++) {
                double slope = p.slopeTo(pointsSortedBySlope[j]);
                if (prevSlope != slope) {
                    // slope has changed -- draw the segment, if needed
                    // reset trackers
                    checkAndDrawLineSegment(p, pointsSortedBySlope, sequenceStartIndex,
                            j - sequenceStartIndex);
                    sequenceStartIndex = j;
                    prevSlope = slope;
                }
            }
            checkAndDrawLineSegment(p, pointsSortedBySlope, sequenceStartIndex, n - sequenceStartIndex);
        }
    }

    private static void checkAndDrawLineSegment(Point point,
            Point[] otherPoints, int sequenceStartIndex, int numPoints) {
        if (numPoints >= 3) {
            Point[] toPrint = Arrays.copyOfRange(otherPoints, sequenceStartIndex, sequenceStartIndex + numPoints);

            //is this the first time we've seen this line segment?
            boolean firstEncounter = true;
            for (int i = 0; i < toPrint.length && firstEncounter; i++) {
                firstEncounter &= point.compareTo(toPrint[i]) < 0;
            }
            
            if (firstEncounter) {
                Arrays.sort(toPrint);
                System.out.print(point + " -> ");
                for (int i = 0; i < numPoints-1; i++) {
                    
                    System.out.print(toPrint[i] + " -> ");
                }
                Point endPoint = toPrint[numPoints-1];
                System.out.println(endPoint);
                point.drawTo(endPoint);
            }
        }
    }
}
