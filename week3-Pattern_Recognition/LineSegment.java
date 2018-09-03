package week3;


public class LineSegment {
    private final Point p; // one endpoint of this line segment
    private final Point q; // the other endpoint of this line segment

    public LineSegment(Point a, Point d) {
        if (a == null || d == null) {
            throw new NullPointerException("argument is null");
        }
        this.p = a;
        this.q = d;
    }

    public void draw() {
        p.drawTo(q);
    }

    public String toString() {
        return p + " -> " + q;
    }

    /**
     * @throws UnsupportedOperationException
     *             if called
     */
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

}
