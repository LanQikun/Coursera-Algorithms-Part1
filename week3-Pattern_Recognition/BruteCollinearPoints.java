package week3;

import java.util.ArrayList;
import java.util.Arrays;


public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lines;

    public BruteCollinearPoints(final Point[] inputPoints) {
        if (inputPoints == null) {
            throw new java.lang.IllegalArgumentException();
        }
        for (Point p : inputPoints) {
            if (p == null) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        Point[] points = inputPoints.clone();
        lines = new ArrayList<LineSegment>();

        Arrays.sort(points);
        int length = points.length;
        for (int i = 1; i < length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        for (int i = 0; i < length; i++) {
            Point a = points[i];
            for (int j = i + 1; j < length; j++) {
                Point b = points[j];
                double slope1 = a.slopeTo(b);

                for (int k = j + 1; k < length; k++) {
                    Point c = points[k];
                    double slope2 = a.slopeTo(c);
                    if (slope1 == slope2) {

                        for (int l = k + 1; l < length; l++) {
                            Point d = points[l];
                            if (slope1 == a.slopeTo(d)) {
                                lines.add(new LineSegment(a, d));
                            }
                        }
                    }
                }
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
