package week3;

import java.util.ArrayList;
import java.util.Arrays;


// undo: subline
public class FastCollinearPoints {
    private final ArrayList<LineSegment> lines;

    public FastCollinearPoints(final Point[] inputPoints) {
        // check input
        if (inputPoints == null) {
            throw new java.lang.IllegalArgumentException();
        }
        for (Point p : inputPoints) {
            if (p == null) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        Point[] points = Arrays.copyOf(inputPoints, inputPoints.length);
        lines = new ArrayList<LineSegment>();

        Arrays.sort(points);
        int length = points.length;
        for (int i = 1; i < length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        if (length < 4) {
            return;
        }

        for (int i = 0; i < length; i++) {
            Point p = inputPoints[i];
            Arrays.sort(points);
            Arrays.sort(points, p.slopeOrder());

            // first slope
            double preSlope = p.slopeTo(points[1]);
            Point max = points[1];
            Point min = points[1];
            int count = 1;

            for (int j = 2; j < length; j++) {
                Point cur = points[j];
                double curSlope = p.slopeTo(cur);
                if (preSlope == curSlope) {
                    count++;
                    max = cur.compareTo(max) > 0 ? cur : max;
                    min = cur.compareTo(min) < 0 ? cur : min;
                } else {
                    if (count >= 3 && p.compareTo(min) < 0) {
                        lines.add(new LineSegment(p, max));
                    }
                    count = 1;
                    preSlope = curSlope;
                    max = cur;
                    min = cur;
                }
            }
            // last points
            if (count >= 3 && p.compareTo(min) < 0) {
                lines.add(new LineSegment(p, max));
            }
        }

    }

    public int numberOfSegments() {
        return lines.size();
    }

    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[lines.size()]);
    }
}