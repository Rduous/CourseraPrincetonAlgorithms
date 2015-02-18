
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrderComparator();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
    	double rise = that.y - y;
    	double run = that.x - x;
    	if (run == 0) {
    		if (rise == 0) {
    			return Double.NEGATIVE_INFINITY;
    		}
    		return Double.POSITIVE_INFINITY;
    	}
    	//avoid negative zero
    	if (rise == -0) {
    	    return 0;
    	}
    	return rise / run; 
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
    	if (this.y < that.y) {
    		return -1;
    	} else if (this.y > that.y) {
    		return 1;
    	} else {
    		if (this.x < that.x) {
    			return -1;
    		} else if (this.x > that.x) {
    			return 1;
    		}
    	}
    	return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
    }
    
    private class SlopeOrderComparator implements Comparator<Point> {

        public int compare(Point o1, Point o2) {
            return Double.compare(slopeTo(o1), slopeTo(o2));
        }
        
    }
}
