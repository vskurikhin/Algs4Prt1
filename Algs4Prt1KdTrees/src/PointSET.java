/* PointSET.java */

import java.util.Iterator;

public class PointSET {
    // construct an empty set of points
    public PointSET() { }

    // is the set empty?
    public boolean isEmpty() {
        return true;
    }

    // number of points in the set
    public int size() {
        return 0;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return false;
    }


    // draw all points to standard draw
    public void draw() {
    }

    private class Point2DItrtr implements Iterator<Point2D> {
        // private Queue<Point2D> q;

        public Point2DItrtr() {
            // q = new Queue<Point2D>();
        }

        public boolean hasNext() {
            // return (!q.isEmpty());
            return false;
        }

        public Point2D next() { return null; }

        public void remove() { }
    }

    // all neighboring boards
    private class Point2DItrbl implements Iterable<Point2D> {
        public Iterator<Point2D> iterator() {
            Point2DItrtr iter = new Point2DItrtr();
            return iter;
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Iterable<Point2D> iter = new Point2DItrbl();
        return iter;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        return;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
/*
    private final SET<Point2D> set = new SET<Point2D>();
    public PointSET() {
    public boolean isEmpty() {
    public int size() {
    public void insert(Point2D p) {
    public boolean contains(Point2D p) {
    public void draw() {
    public Iterable<Point2D> range(RectHV rect) {
    public Point2D nearest(Point2D p) {
*/
