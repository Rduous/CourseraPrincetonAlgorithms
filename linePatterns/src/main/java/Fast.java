import java.util.Arrays;


public class Fast {
    
    public static void main(String[] args) {
        String fileName = args[0];
        In in = new In(fileName);
        int n = in.readInt();
        Point[] points = new Point[n];
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        
        for (int i=0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point point = new Point(x, y);
            points[i] = point;
            point.draw();
        }
        
        findAndPrintLineSegments(points);
    }
 
    private static void findAndPrintLineSegments(Point[] points) {
        int n = points.length;
        for (int i = 0; i < n-3; i++) {
            Point p = points[i];
            Arrays.sort(points, i+1, n, points[i].SLOPE_ORDER);
            double slope = Double.NEGATIVE_INFINITY;
            int numPoints = 0;
            for (int j = i+1; j < n; j++) {
                double nextSlope = p.slopeTo(points[j]);
                if (slope == nextSlope) {
                    numPoints++;
                } else {
                    //slope has changed
                    //check current line segment length, do stuff if necessary, and reset
                    if (numPoints >= 3) {
                        printAndDrawLineSegment(points, i,numPoints);
                    }
                    numPoints = 1;
                    slope = nextSlope;
                }
            }
            if (numPoints >= 3) {
                printAndDrawLineSegment(points, i,numPoints);
            }
        }
        
    }

    private static void printAndDrawLineSegment(Point[] points, int pointIndex,
            int numPoints) {
        
        for (int i = pointIndex; i < pointIndex + numPoints; i++) {
            System.out.print(points[i] + " -> ");
        }
        System.out.println(points[pointIndex + numPoints]);
        points[pointIndex].drawTo(points[pointIndex + numPoints]);        
    }   

}
