import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StdDraw.class)
@PowerMockIgnore("javax.swing.*")
public class FastTest {

    @Test
    @PrepareForTest({ StdDraw.class })
    public void testCorrectlyIdentifiesLineSegmentInFourPointSet() {
        Point[] points = { new Point(0, 0), new Point(0, 1), new Point(0, 2),
                new Point(0, 3) };
        mockStatic(StdDraw.class);
        Fast.findAndPrintLineSegments(points);
        PowerMockito.verifyStatic();
        StdDraw.line(0, 0, 0, 3);
    }

    @Test
    @PrepareForTest({ StdDraw.class })
    public void testDoesNotDrawSubsegmentOfLargerLine() {
        Point[] points = { new Point(0, 0), new Point(0, 1), new Point(0, 2),
                new Point(0, 3), new Point(0, 4) };
        mockStatic(StdDraw.class);
        Fast.findAndPrintLineSegments(points);
        PowerMockito.verifyStatic(never());
        StdDraw.line(0, 1, 0, 3);
        PowerMockito.verifyStatic();
        StdDraw.line(0, 0, 0, 4);
    }

    @Test
    @PrepareForTest({ StdDraw.class })
    public void testEndPointFilteringDoesNotCauseASegmentMiss() {
        Point[] points = { new Point(0, 0), new Point(0, 1), new Point(0, 2),
                new Point(0, 3), new Point(-3, 3), new Point(-2, 3),
                new Point(-1, 3), };
//        Arrays.sort(points);
        mockStatic(StdDraw.class);
        Fast.findAndPrintLineSegments(points);
        PowerMockito.verifyStatic();
        StdDraw.line(0, 0, 0, 3);
        PowerMockito.verifyStatic();
        StdDraw.line(-3, 3, 0, 3);
    }
    
    @Test
    @PrepareForTest({ StdDraw.class })
    public void testFourByFourGrid() {
        Point[] points = new Point[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                points[i*4+j] = new Point(i,j);
            }
        }
//        Arrays.sort(points);
        mockStatic(StdDraw.class);
        Fast.findAndPrintLineSegments(points);
        PowerMockito.verifyStatic(times(10));
        StdDraw.line(anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }

}
