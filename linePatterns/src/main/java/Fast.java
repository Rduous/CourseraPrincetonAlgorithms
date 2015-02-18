import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
        Arrays.sort(points);
        findAndPrintLineSegments(points);
    }
 
    static void findAndPrintLineSegments(Point[] naturalOrderPoints) {
        int n = naturalOrderPoints.length;
       
        List<Point> endPoints = new ArrayList<Point>();
        for (int i = 0; i < n-3; i++) {
            Point p = naturalOrderPoints[i];
            Point[] points = Arrays.copyOfRange(naturalOrderPoints, i+1,n);
            Arrays.sort(points, 0, points.length, p.SLOPE_ORDER);
            double slope = Double.NEGATIVE_INFINITY;
            int numPoints = 0;
            int sequenceStartIndex = 0;
            for (int j = 0; j < points.length; j++) {
                double nextSlope = p.slopeTo(points[j]);
                if (slope == nextSlope) {
                    numPoints++;
                } else {
                    //slope has changed
                    //check current line segment length, do stuff if necessary, and reset
                    if (numPoints >= 3) {
                        printAndDrawLineSegment(p, points, sequenceStartIndex ,numPoints, endPoints);
                    }
                    sequenceStartIndex = j;
                    numPoints = 1;
                    slope = nextSlope;
                }
            }
            if (numPoints >= 3) {
                printAndDrawLineSegment(p, points, sequenceStartIndex,numPoints, endPoints);
            }
        }        
    }

    private static void printAndDrawLineSegment(Point startPoint, Point[] otherPoints, int sequenceStartIndex,
            int numPoints, List<Point> endPoints) {

        //check that this isn't a smaller subsegment
        Point[] toPrint = Arrays.copyOfRange(otherPoints, sequenceStartIndex, sequenceStartIndex+numPoints);
        Arrays.sort(toPrint);
        Point endPoint = toPrint[numPoints-1];
        if (! endPoints.contains(endPoint)) {
            System.out.print(startPoint + " -> ");
            for (int i = 0; i < numPoints-1; i++) {
                System.out.print(toPrint[i] + " -> ");
            }
            System.out.println(endPoint);
            startPoint.drawTo(endPoint);
            endPoints.add(endPoint);
        }
    }   

}
