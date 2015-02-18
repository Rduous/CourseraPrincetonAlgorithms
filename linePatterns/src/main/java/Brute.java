
public class Brute {
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
        
        findAndDrawLineSegments(points);
    }

    private static void findAndDrawLineSegments(Point[] points) {
        int n = points.length;
        for (int i = 0; i < n-3; i++) {
            for (int j = i+1; j < n-2; j++) {
                for (int k = 0; k < n-1; k++) {
                    Point p1 = points[i];
                    Point p2 = points[j];
                    Point p3 = points[k];
                    if (colinear(p1, p2, p3)) {
                        for (int m = 0; m < n; m++) {
                            Point p4 = points[m];
                            if (colinear(p1, p2, p4)) {
                                System.out.println(p1 + " -> " + p2 + " -> " + p3 + " -> " + p4);
                                p1.drawTo(p4);
                            }
                        }
                    }
                }
            }
        }
        
    }

    private static boolean colinear(Point... points) {
        double slope = points[0].slopeTo(points[1]);
        for (int i = 1; i < points.length-1; i++) {
            if ( Math.abs(points[i].slopeTo(points[i+1] ) - slope) > 0.00001) {
                return false;
            }
        }
        return true;
    }
}
